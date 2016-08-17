import $ from 'jquery';
import React from 'react';
import {PageHeader,Button} from 'react-bootstrap';

import PokemonRate from './PokemonRate.jsx';

const Application = React.createClass({

    getInitialState() {
        return {
            pokedex : [],
            dusts : [],
            pokemon : 1,
            dust : 200,
            rate : {}
        }
    },

    componentDidMount() {
        $.ajax({
            url: '/pokedex',
            type: 'GET',
            contentType : 'application/json'
        }).done(data => this.setState({pokedex : data}));
        $.ajax({
            url: '/dusts',
            type: 'GET',
            contentType : 'application/json'
        }).done(data => this.setState({dusts : data}));
    },

    handleSubmit(e){
        e.preventDefault();
        var pokemon = {
            id : this.state.pokemon,
            cp : this.refs.inputCp.value,
            hp : this.refs.inputHp.value,
            dust : this.state.dust,
        };
        $.ajax({
            url: '/pokemon-rates',
            type: 'POST',
            contentType : 'application/json',
            data : JSON.stringify(pokemon)
        }).done(data => this.setState({rate : data}));
    },

    renderSelectPokemon() {
        return (
          <select onChange={event => this.setState({pokemon: event.target.value})}>
              {this.state.pokedex.map(p => <option key={p.id} value={p.id}>{p.id} - {p.name}</option>)}
          </select>
        );
    },

    renderSelectDust() {
        return (
            <select onChange={event => this.setState({dust: event.target.value})}>
                {this.state.dusts.map(p => <option key={p} value={p}>{p}</option>)}
            </select>
        );
    },

    render() {
        return (
            <div className="App container">
                <PageHeader>Pokemon GO<small> IV Calculator</small></PageHeader>

                <form ref="form" onSubmit={this.handleSubmit}>
                    {this.renderSelectPokemon()}
                    &nbsp;CP <input type="number" min="10" max="5000" required="true" ref="inputCp" />
                    &nbsp;HP <input type="number" min="1" max="500" required="true" ref="inputHp" />
                    &nbsp;Dust {this.renderSelectDust()}
                    &nbsp;<Button bsStyle="primary" bsSize="small" onClick={this.handleSubmit}>Rate Pokemon !</Button>
                </form>
                
                <hr/>
                
                <PokemonRate rate={this.state.rate}/>
            </div>
        );
    }

});

export default Application;

