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

import io.github.binout.pokemongo.domain.IndividualValue;
import io.github.binout.pokemongo.domain.IndividualValues;
import io.github.binout.pokemongo.domain.Pokedex;
import io.github.binout.pokemongo.domain.Pokemon;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Inspired by :
 * https://github.com/andromedado/pokemon-go-iv-calculator
 */
public class IVCalculator {

    private final Pokedex pokedex;
    private final LevelData[] levels;
    private final CPCalculator cpCalculator;
    private final HPCalculator hpCalculator;

    public IVCalculator(Pokedex pokedex) {
        this.pokedex = pokedex;
        this.cpCalculator = new CPCalculator(pokedex);
        this.hpCalculator = new HPCalculator(pokedex);
        this.levels = LevelData.all();
    }

    public Map<Double, IndividualValues> compute(Pokemon pokemon, int dust) {
        Map<Double, IndividualValues> ivs = new TreeMap<>();
        List<LevelData> potentialLevels = potentialLevels(dust);
        List<HPIv> hpivs = hpivs(pokemon, potentialLevels);
        for (HPIv hpiv : hpivs) {
            IndividualValue stamina = hpiv.stamina;
            LevelData level = hpiv.level;
            IndividualValue.range().forEach(attack -> IndividualValue.range()
                    .filter(defense -> testCP(pokemon, attack, defense, stamina, level))
                    .map(defense -> new IndividualValues(stamina, attack, defense))
                    .forEach(iv -> ivs.put(level.getLevel(), iv)));
        }
        return ivs;
    }

    private List<HPIv> hpivs(Pokemon pokemon, List<LevelData> potentialLevels) {
        List<HPIv> hpIvs = new ArrayList<>();
        for (LevelData potentialLevel : potentialLevels) {
            IndividualValue.range()
                    .filter(stamina -> testHP(pokemon, stamina, potentialLevel))
                    .map(stamina -> new HPIv(potentialLevel, stamina))
                    .forEach(hpIvs::add);
        }
        return hpIvs;
    }

    private boolean testHP(Pokemon pokemon, IndividualValue stamina, LevelData level) {
        double theoricHp = hpCalculator.compute(pokemon, stamina, level.getCpScalar());
        return pokemon.hp() ==  theoricHp;
    }

    private boolean testCP(Pokemon pokemon, IndividualValue attackIV, IndividualValue defenseIV, IndividualValue staminaIV, LevelData level) {
        double theoricCp = cpCalculator.compute(pokemon, staminaIV, attackIV, defenseIV, level.getCpScalar());
        return pokemon.cp() == (int)theoricCp;
    }

    private List<LevelData> potentialLevels(int dust) {
        Stream<LevelData> potentialLevels = Arrays.stream(levels).filter(l -> l.getDust() == dust);
        return potentialLevels.sorted(Comparator.comparing(LevelData::getLevel).reversed()).collect(Collectors.toList());
    }

    private static class HPIv {
        private final LevelData level;
        private final IndividualValue stamina;

        private HPIv(LevelData level, IndividualValue stamina) {
            this.level = level;
            this.stamina = stamina;
        }
    }

}
