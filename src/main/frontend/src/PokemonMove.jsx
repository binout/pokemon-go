import React from 'react';
import {Table} from 'react-bootstrap';

const PokemonStat = React.createClass({

    getDefaultProps() {
        return {
            moves : []
        }
    },

    renderLine(move) {
        return (
            <tr key={move.name}>
                <td>{move.name}</td>
                <td>{move.attack}</td>
                <td>{move.dps}</td>
            </tr>
        );
    },
    
    render() {
        return (
            <Table striped bordered condensed responsive>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Attack</th>
                    <th>DPS</th>
                </tr>
                </thead>
                <tbody>
                {this.props.moves.map(m => this.renderLine(m))}
                </tbody>
            </Table>
        );
    }
});

export default PokemonStat;