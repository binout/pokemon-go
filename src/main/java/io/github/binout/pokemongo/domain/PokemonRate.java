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

import java.time.LocalDate;
import java.util.Map;

public class PokemonRate {

    private final String trainer;
    private final LocalDate date;
    private final Pokemon pokemon;
    private final Dust dust;
    private final Map<Double, IndividualValues> ivsByLevel;

    public PokemonRate(String trainer, Pokemon pokemon, Dust dust) {
        this.date = LocalDate.now();
        this.trainer = trainer;
        this.pokemon = pokemon;
        this.dust = dust;
        this.ivsByLevel = pokemon.potentialIvsByLevel(dust);
    }

    public String trainer() {
        return trainer;
    }

    public LocalDate date() {
        return date;
    }

    public Pokemon pokemon() {
        return pokemon;
    }

    public Dust dust() {
        return dust;
    }

    public Map<Double, IndividualValues> ivsByLevel() {
        return ivsByLevel;
    }
}
