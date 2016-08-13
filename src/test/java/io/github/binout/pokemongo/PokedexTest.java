package io.github.binout.pokemongo;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;

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
public class PokedexTest {

    @Test
    public void can_load_pokedex() {
        assertThat(new Pokedex().count()).isEqualTo(151);
    }

    @Test
    public void can_load_all_information() {
        Pokedex pokedex = new Pokedex();
        IntStream.range(1, pokedex.count()).forEach(id -> {
            assertThat(pokedex.getNameOf(id)).isNotNull();
            assertThat(pokedex.getAttackOf(id)).isNotNull();
            assertThat(pokedex.getDefenseOf(id)).isNotNull();
            assertThat(pokedex.getStaminaOf(id)).isNotNull();
        });
    }

}