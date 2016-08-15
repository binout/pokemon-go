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
package io.github.binout.pokemongo;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        int pokemonId = 22;
        int cp = 831;
        int hp = 79;
        int dust = 2200;
        Pokemon pokemon = new Pokemon(pokemonId, cp, hp);
        Map<Double, IndividualValues> potentialIvs = pokemon.potentialIvsByLevel(dust);
        potentialIvs.forEach((level,iv) -> System.out.println(
                "Level : " + level
                        + ", MaxCP : " + pokemon.maxCp()
                        + ", MaxHP : " + pokemon.maxHp()
                        + ", Stamina : " + iv.stamina()
                        + ", Attack : " + iv.attack()
                        + ", Defense : " + iv.defense()
                        + ", Perfection : " + iv.perfectRate() + " %"));

    }
}
