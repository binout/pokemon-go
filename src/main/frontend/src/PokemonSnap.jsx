import React from 'react';

const PokemonSnap = React.createClass({

    getDefaultProps() {
        return {
            id : 0
        }
    },

    render() {
        let snap = 'pokemon-snaps/';
        if (this.props.id < 10) {
            snap = snap + '00' + this.props.id + '.png'
        } else  if (this.props.id < 100) {
            snap = snap + '0' + this.props.id + '.png'
        } else {
            snap = snap + this.props.id + '.png'
        }
        return (
            <img src={snap} width="80px" height="80px"/>
        );
    }
});

export default PokemonSnap;