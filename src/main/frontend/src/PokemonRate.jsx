import React from 'react';
import {Panel,Label,Table} from 'react-bootstrap';

import PokemonSnap from './PokemonSnap.jsx';

var moment = require('moment');

const PokemonRate = React.createClass({

    getDefaultProps() {
        return {
            rate : {}
        }
    },

    renderLine(iv) {
        var bsStyle = 'default';
        if (iv.perfect > 90) {
            bsStyle = 'success'
        } else if (iv.perfect > 75) {
            bsStyle = 'info';
        } else if (iv.perfect > 50) {
            bsStyle = 'primary'
        } else  if (iv.perfect > 25) {
            bsStyle = 'warning'
        } else {
            bsStyle = 'danger';
        }
        return (
            <tr key={iv.level}>
                <td>{iv.level}</td>
                <td>{iv.stamina}</td>
                <td>{iv.attack}</td>
                <td>{iv.defense}</td>
                <td><Label bsStyle={bsStyle}>{iv.perfect}</Label></td>
            </tr>
        );
    },
    
    render() {
        var rate = this.props.rate;
        if (rate.id) {
            let header = rate.name + ' (' + rate.trainer + ')' + ' - ' + moment(rate.date).format('YYYY/MM/DD');
            return (
                <Panel header={header}>
                    <h4>
                        <PokemonSnap id={rate.id}/>
                        <p><Label>CP</Label> {rate.cp}/{rate.maxCp}</p>
                        <p><Label>HP</Label> {rate.hp}/{rate.maxHp}</p>
                    </h4>

                    <Table striped bordered condensed>
                        <thead>
                        <tr>
                            <th>Level</th>
                            <th>Stamina</th>
                            <th>Attack</th>
                            <th>Defense</th>
                            <th>Perfection</th>
                        </tr>
                        </thead>
                        <tbody>
                        {rate.ivs.map(iv => this.renderLine(iv))}
                        </tbody>
                    </Table>
                </Panel>
            );
        } else {
            return <div></div>
        }
    }
});

export default PokemonRate;
