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
package io.github.binout.pokemongo.domain.iv;

public class IndividualValues {

    private final IndividualValue stamina;
    private final IndividualValue attack;
    private final IndividualValue defense;

    public IndividualValues(IndividualValue stamina, IndividualValue attack, IndividualValue defense) {
        this.stamina = stamina;
        this.attack = attack;
        this.defense = defense;
    }

    public int stamina() {
        return stamina.value();
    }

    public int attack() {
        return attack.value();
    }

    public int defense() {
        return defense.value();
    }

    public IndividualValuesGrade grade() {
        return new IndividualValuesGrade(this);
    }
}
