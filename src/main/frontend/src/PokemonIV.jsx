import React from 'react';
import {Label,Table,Col} from 'react-bootstrap';

const PokemonIV = React.createClass({

    getDefaultProps() {
        return {
            ivs : []
        }
    },

    renderLine(iv) {
        var bsStyle = 'default';
        switch (iv.grade) {
            case 'A' :
                bsStyle = 'success'
                break;
            case 'B' :
                bsStyle = 'primary'
                break;
            case 'C' :
                bsStyle = 'warning'
                break;
            case 'D' :
                bsStyle = 'danger'
                break;
        }
        return (
            <tr key={iv.level}>
                <td>{iv.level}</td>
                <td>{iv.stamina}</td>
                <td>{iv.attack}</td>
                <td>{iv.defense}</td>
                <td><Label bsStyle={bsStyle}>{iv.rate}</Label></td>
            </tr>
        );
    },
    
    render() {
        return (
            <Col sm={10}>
                <Table striped bordered condensed>
                    <thead>
                    <tr>
                        <th>Level</th>
                        <th>Stamina</th>
                        <th>Attack</th>
                        <th>Defense</th>
                        <th>Rate</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.props.ivs.map(iv => this.renderLine(iv))}
                    </tbody>
                </Table>
            </Col>
        );
    }
});

export default PokemonIV;