/*
 * Copyright 2016 Benoît Prioux
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.binout.pokemongo.domain;

import io.github.binout.pokemongo.domain.formula.CPCalculator;
import io.github.binout.pokemongo.domain.formula.HPCalculator;
import io.github.binout.pokemongo.domain.formula.IVCalculator;
import io.github.binout.pokemongo.domain.iv.IndividualValues;
import io.github.binout.pokemongo.domain.move.PokemonMove;
import io.github.binout.pokemongo.domain.rate.Dust;

import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class Pokemon {

    private final PokemonId id;
    private final int cp;
    private final int hp;

    public Pokemon(PokemonId id, int cp, int hp) {
        this.id = id;
        this.cp = cp;
        this.hp = hp;
    }

    public PokemonId id() {
        return id;
    }

    public int cp() {
        return cp;
    }

    public int hp() {
        return hp;
    }

    public PokemonName name() {
        return Pokedex.get().getNameOf(id);
    }

    public Map<Double, IndividualValues> potentialIvsByLevel(Dust dust) {
        return new IVCalculator(Pokedex.get()).compute(this, dust);
    }

    public IndividualValues iv(double level, Dust dust) {
        return ofNullable(potentialIvsByLevel(dust).get(level))
                .orElseThrow(IllegalArgumentException::new);
    }

    public int maxCp() {
        return (int) new CPCalculator(Pokedex.get()).computeMax(this);
    }

    public int maxHp() {
        return (int) new HPCalculator(Pokedex.get()).computeMax(this);
    }

    public List<PokemonMove.QuickMove> quickMoves() {
        return Pokedex.get().getQuickMovesOf(id);
    }

    public List<PokemonMove.ChargeMove> chargeMoves() {
        return Pokedex.get().getChargeMovesOf(id);
    }
}
