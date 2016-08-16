import $ from 'jquery';
import React from 'react';
import {PageHeader,Button,Panel,Label,Table} from 'react-bootstrap';

const Application = React.createClass({

    getInitialState() {
        return {
            pokedex : [],
            pokemon : -1,
            rate : {}
        }
    },

    componentDidMount() {
        $.ajax({
            url: '/pokedex',
            type: 'GET',
            contentType : 'application/json'
        }).done(data => this.setState({pokedex : data}));
    },

    handleSubmit(e){
        e.preventDefault();
        var pokemon = {
            id : this.state.pokemon,
            cp : this.refs.inputCp.value,
            hp : this.refs.inputHp.value,
            dust : this.refs.inputDust.value,
        };
        $.ajax({
            url: '/pokemon-rates',
            type: 'POST',
            contentType : 'application/json',
            data : JSON.stringify(pokemon)
        }).done(data => this.setState({rate : data}));
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

    renderSnap() {
        let snap;
        if (this.state.rate.id < 10) {
            snap = 'pokemon-snaps/00' + this.state.rate.id + '.png'
        } else  if (this.state.rate.id < 100) {
            snap = 'pokemon-snaps/0' + this.state.rate.id + '.png'
        } else {
            snap = 'pokemon-snaps/' + this.state.rate.id + '.png'
        }
        return (
            <img src={snap} width="80px" height="80px"/>
        );
    },

    renderRate() {
        if (this.state.rate.id) {
            return (
                <Panel header={this.state.rate.name}>
                    <h4>
                        {this.renderSnap()}
                        <Label>CP</Label> {this.state.rate.cp}/{this.state.rate.maxCp}&nbsp;
                        <Label>HP</Label> {this.state.rate.hp}/{this.state.rate.maxHp}
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
                        {this.state.rate.ivs.map(iv => this.renderLine(iv))}
                        </tbody>
                    </Table>
                </Panel>
            );
        }
    },

    pokemonSelected(event) {
        this.setState({pokemon: event.target.value});
    },

    renderSelect() {
        return (
          <select onChange={this.pokemonSelected}>
              {this.state.pokedex.map(p => <option key={p.id} value={p.id}>{p.id} - {p.name}</option>)}
          </select>
        );
    },

    render() {
        return (
            <div className="App container">
                <PageHeader>Pokemon GO<small> IV Calculator</small></PageHeader>

                <form ref="form" onSubmit={this.handleSubmit}>
                    {this.renderSelect()}
                    &nbsp;CP <input type="text" ref="inputCp" />
                    &nbsp;HP <input type="text" ref="inputHp" />
                    &nbsp;Dust <input type="text" ref="inputDust" />
                    &nbsp;<Button bsStyle="primary" bsSize="small" onClick={this.handleSubmit}>Rate Pokemon !</Button>
                </form>
                <hr/>
                {this.renderRate()}
            </div>
        );
    }

});

export default Application;

