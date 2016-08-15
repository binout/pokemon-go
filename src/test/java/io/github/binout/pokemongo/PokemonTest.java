package io.github.binout.pokemongo;

import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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
public class PokemonTest {

    @Test
    public void should_compute_ivs_for_evoli() {
        Map<Double, IndividualValues> potentialIvs = new Pokemon(133, 335, 55).potentialIvsByLevel(1300);
        assertThat(potentialIvs).hasSize(4);

        assertIv(potentialIvs.get(11.0), 15, 12, 15);
        assertIv(potentialIvs.get(11.5), 13, 7, 13);
        assertIv(potentialIvs.get(12.0), 12, 0, 11);
        assertIv(potentialIvs.get(12.5), 8, 1, 8);
    }

    private void assertIv(IndividualValues iv, int attack, int defense, int stamina) {
        assertThat(iv.attack()).isEqualTo(attack);
        assertThat(iv.defense()).isEqualTo(defense);
        assertThat(iv.stamina()).isEqualTo(stamina);
    }

}