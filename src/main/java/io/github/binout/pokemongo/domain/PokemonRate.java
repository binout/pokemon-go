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

import java.util.Map;

public class PokemonRate {

    private final Pokemon pokemon;
    private final int dust;
    private final Map<Double, IndividualValues> ivsByLevel;

    public PokemonRate(Pokemon pokemon, int dust) {
        this.pokemon = pokemon;
        this.dust = dust;
        this.ivsByLevel = pokemon.potentialIvsByLevel(dust);
    }

    public Pokemon pokemon() {
        return pokemon;
    }

    public int dust() {
        return dust;
    }

    public Map<Double, IndividualValues> ivsByLevel() {
        return ivsByLevel;
    }
}
