import React from 'react';
import {Label} from 'react-bootstrap';

const PokemonStat = React.createClass({

    getDefaultProps() {
        return {
            name : "",
            value : 0,
            max : 0
        }
    },

    render() {
        return (
            <span><Label>{this.props.name}</Label> {this.props.value}/{this.props.max}</span>
        );
    }
});

export default PokemonStat;