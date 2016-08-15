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
package io.github.binout.pokemongo.formula;

import io.github.binout.pokemongo.Pokedex;
import io.github.binout.pokemongo.Pokemon;

import java.math.BigDecimal;

public class CPCalculator {

    private final Pokedex pokedex;

    public CPCalculator(Pokedex pokedex) {
        this.pokedex = pokedex;
    }

    public double compute(Pokemon pokemon, int staminaIV, int attackIV, int defenseIV, double cpScalar) {
        int baseAttack = pokedex.getAttackOf(pokemon.id());
        int baseDefense = pokedex.getDefenseOf(pokemon.id());
        int baseStamina = pokedex.getStaminaOf(pokemon.id());
        double attackFactor = baseAttack + attackIV;
        double defenseFactor = Math.pow(baseDefense + defenseIV, 0.5);
        double staminaFactor = Math.pow((baseStamina + staminaIV), 0.5);
        double scalarFactor = Math.pow(cpScalar, 2);
        return new BigDecimal(attackFactor)
                .multiply(new BigDecimal(defenseFactor))
                .multiply(new BigDecimal(staminaFactor))
                .multiply(new BigDecimal(scalarFactor))
                .divide(new BigDecimal(10)).doubleValue();
    }

    public double computePerfect(Pokemon pokemon) {
        return compute(pokemon, 15, 15, 15, 0.7903001);
    }
}
