import $ from 'jquery';
import React from 'react';
import {PageHeader,Button} from 'react-bootstrap';

import PokemonRate from './PokemonRate.jsx';

const Application = React.createClass({

    getInitialState() {
        return {
            pokedex : [],
            pokemon : 1,
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
                    &nbsp;CP <input type="number" min="10" max="5000" required="true" ref="inputCp" />
                    &nbsp;HP <input type="number" min="1" max="500" required="true" ref="inputHp" />
                    &nbsp;Dust <input type="number" min="200" max="10000" step="100" required="true" ref="inputDust" />
                    &nbsp;<Button bsStyle="primary" bsSize="small" onClick={this.handleSubmit}>Rate Pokemon !</Button>
                </form>
                
                <hr/>
                
                <PokemonRate rate={this.state.rate}/>
            </div>
        );
    }

});

export default Application;

