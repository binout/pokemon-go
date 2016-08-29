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
package io.github.binout.pokemongo.domain.move;

public class PokemonMove {

    private String name;
    private int attack;
    private double dps;

    private PokemonMove(String name, int attack, double dps) {
        this.name = name;
        this.attack = attack;
        this.dps = dps;
    }

    public String name() {
        return name;
    }

    public int attack() {
        return attack;
    }

    public double dps() {
        return dps;
    }

    public static class QuickMove extends PokemonMove {

        public QuickMove(String name, int attack, double dps) {
            super(name, attack, dps);
        }
    }

    public static class ChargeMove extends PokemonMove {

        public ChargeMove(String name, int attack, double dps) {
            super(name, attack, dps);
        }
    }
}
