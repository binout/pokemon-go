import React from 'react';
import {Label,Table,Popover,OverlayTrigger} from 'react-bootstrap';

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
        var popover = (
            <Popover><span>{iv.message}</span></Popover>
        );
        return (
            <tr key={iv.level}>
                <td>{iv.level}</td>
                <td>{iv.stamina}</td>
                <td>{iv.attack}</td>
                <td>{iv.defense}</td>
                <td>
                    <OverlayTrigger trigger="click" placement="left" overlay={popover}>
                        <Label bsStyle={bsStyle}>{iv.rate}</Label>
                    </OverlayTrigger>
                </td>
            </tr>
        );
    },
    
    render() {
        return (
            <Table striped bordered condensed responsive>
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
        );
    }
});

export default PokemonIV;