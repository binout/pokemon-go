import React from 'react';
import {Panel} from 'react-bootstrap';

import PokemonStat from './PokemonStat.jsx';
import PokemonSnap from './PokemonSnap.jsx';
import PokemonIV from './PokemonIV.jsx';

var moment = require('moment');

const PokemonRate = React.createClass({

    getDefaultProps() {
        return {
            rate : {}
        }
    },
    
    render() {
        var rate = this.props.rate;
        if (rate.id) {
            let header = rate.name + ' (' + rate.trainer + ')' + ' - ' + moment(rate.date).format('YYYY/MM/DD');
            return (
                <Panel header={header}>
                    <h4>
                        <PokemonSnap id={rate.id}/>&nbsp;
                        <PokemonStat name="CP" value={rate.cp} max={rate.maxCp}/>&nbsp;
                        <PokemonStat name="HP" value={rate.hp} max={rate.maxHp}/>
                    </h4>

                    <PokemonIV ivs={rate.ivs}/>
                </Panel>
            );
        } else {
            return <div></div>
        }
    }
});

export default PokemonRate;
