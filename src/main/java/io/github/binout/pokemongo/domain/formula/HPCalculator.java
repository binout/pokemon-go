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
package io.github.binout.pokemongo.domain.formula;

import io.github.binout.pokemongo.domain.iv.IndividualValue;
import io.github.binout.pokemongo.domain.Pokedex;
import io.github.binout.pokemongo.domain.Pokemon;

public class HPCalculator {

    private final Pokedex pokedex;

    public HPCalculator(Pokedex pokedex) {
        this.pokedex = pokedex;
    }

    public double compute(Pokemon pokemon, IndividualValue staminaIV, double cpScalar) {
        return Math.floor((pokedex.getStaminaOf(pokemon.id()) + staminaIV.value()) * cpScalar);
    }

    public double computeMax(Pokemon pokemon) {
        return compute(pokemon, IndividualValue.max(), LevelData.maxScalar());
    }
}
