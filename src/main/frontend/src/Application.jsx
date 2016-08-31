import $ from 'jquery';
import React from 'react';
import ReactDOM from 'react-dom';
import {PageHeader,Form,Col,FormGroup,ControlLabel,FormControl,Button} from 'react-bootstrap';

import PokemonRate from './PokemonRate.jsx';
import PokemonSnap from './PokemonSnap.jsx';

const Application = React.createClass({

    getInitialState() {
        return {
            pokedex : [],
            dusts : [],
            pokemon : 1,
            team : 'RED',
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
            team : this.state.team,
            trainer : ReactDOM.findDOMNode(this.refs.inputTrainer).value,
            cp : ReactDOM.findDOMNode(this.refs.inputCp).value,
            hp : ReactDOM.findDOMNode(this.refs.inputHp).value,
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
          <FormControl componentClass="select" onChange={event => this.setState({pokemon: event.target.value})}>
              {this.state.pokedex.map(p => <option key={p.id} value={p.id}>{p.id} - {p.name}</option>)}
          </FormControl>
        );
    },

    renderSelectDust() {
        return (
            <FormControl componentClass="select" onChange={event => this.setState({dust: event.target.value})}>
                {this.state.dusts.map(p => <option key={p} value={p}>{p}</option>)}
            </FormControl>
        );
    },

    renderSelectTeam() {
        return (
            <FormControl componentClass="select" onChange={event => this.setState({team: event.target.value})}>
                <option key="RED" value="RED">Valor (Red)</option>
                <option key="YELLOW" value="YELLOW">Spark (Yellow)</option>
                <option key="BLUE" value="BLUE">Mystic (Blue)</option>
            </FormControl>
        );
    },

    render() {
        return (
            <div className="App container">
                <PageHeader>Pokemon GO<small> Stats Sheet</small></PageHeader>

                <Form horizontal onSubmit={this.handleSubmit}>
                    <FormGroup controlId="formTrainer" bsSize="sm">
                        <Col componentClass={ControlLabel} sm={2}>
                            Team
                        </Col>
                        <Col sm={2}>
                            {this.renderSelectTeam()}
                        </Col>
                        <Col componentClass={ControlLabel} sm={2}>
                            Trainer
                        </Col>
                        <Col sm={2}>
                            <FormControl type="text" placeholder="Sacha" defaultValue="Sacha" ref="inputTrainer" />
                        </Col>
                        <Col sm={4}/>
                    </FormGroup>
                    <FormGroup controlId="formPokemon" bsSize="sm">
                        <Col sm={3}>
                            <br/>
                            {this.renderSelectPokemon()}
                            <PokemonSnap id={this.state.pokemon} size="120px"/>&nbsp;
                        </Col>
                        <Col sm={3}>
                            <ControlLabel>CP</ControlLabel>
                            <FormControl type="number" min="10" max="5000" required="true" ref="inputCp" />
                            <ControlLabel>HP</ControlLabel>
                            <FormControl type="number" min="1" max="500" required="true" ref="inputHp" />
                            <ControlLabel>Dust</ControlLabel>
                            {this.renderSelectDust()}
                        </Col>
                        <Col sm={6}>
                            <br/>
                            <Button bsStyle="primary" bsSize="large" onClick={this.handleSubmit}>Rate Pokemon !</Button>
                        </Col>
                    </FormGroup>
                </Form>

                <hr/>

                <PokemonRate rate={this.state.rate}/>
            </div>
        );
    }

});

export default Application;

