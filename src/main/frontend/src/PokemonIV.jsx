import React from 'react';
import {Label,Table} from 'react-bootstrap';

const PokemonIV = React.createClass({

    getDefaultProps() {
        return {
            ivs : []
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
        return (
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
                {this.props.ivs.map(iv => this.renderLine(iv))}
                </tbody>
            </Table>
        );
    }
});

export default PokemonIV;