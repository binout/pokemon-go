/*
 * Copyright 2015 Benoît Prioux
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
package io.github.binout.pokemongo;

import java.math.BigDecimal;

public class IndividualValue {

    private final int stamina;
    private final int attack;
    private final int defense;

    IndividualValue(int stamina, int attack, int defense) {
        this.stamina = stamina;
        this.attack = attack;
        this.defense = defense;
    }

    public int stamina() {
        return stamina;
    }

    public int attack() {
        return attack;
    }

    public int defense() {
        return defense;
    }

    public double perfectRate() {
        int sum = stamina + defense + attack;
        return new BigDecimal(sum).multiply(new BigDecimal(2.22)).doubleValue();
    }
}
