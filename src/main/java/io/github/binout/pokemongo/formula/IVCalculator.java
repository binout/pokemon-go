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
package io.github.binout.pokemongo.formula;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.binout.pokemongo.IndividualValue;
import io.github.binout.pokemongo.Pokedex;
import io.github.binout.pokemongo.Pokemon;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Inspired by :
 * https://github.com/andromedado/pokemon-go-iv-calculator
 */
public class IVCalculator {

    private final Pokedex pokedex;
    private final Level[] levels;
    private final CPCalculator cpCalculator;
    private final HPCalculator hpCalculator;

    public IVCalculator(Pokedex pokedex) {
        try {
            this.pokedex = pokedex;
            this.cpCalculator = new CPCalculator(pokedex);
            this.hpCalculator = new HPCalculator(pokedex);
            this.levels = new ObjectMapper().readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("levelUpData.json"), Level[].class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Map<Double, IndividualValue> compute(Pokemon pokemon, int dust) {
        Map<Double, IndividualValue> ivs = new TreeMap<>();
        List<Level> potentialLevels = potentialLevels(dust);
        List<HPIv> hpivs = hpivs(pokemon, potentialLevels);
        for (HPIv hpiv : hpivs) {
            int stamina = hpiv.stamina;
            Level level = hpiv.level;
            IntStream.range(0, 16).forEach(attack -> IntStream.range(0, 16)
                    .filter(defense -> testCP(pokemon, attack, defense, stamina, level))
                    .mapToObj(defense -> new IndividualValue(stamina, attack, defense))
                    .forEach(iv -> ivs.put(level.level, iv)));
        }
        return ivs;
    }

    private List<HPIv> hpivs(Pokemon pokemon, List<Level> potentialLevels) {
        List<HPIv> hpIvs = new ArrayList<>();
        for (Level potentialLevel : potentialLevels) {
            IntStream.range(0, 16)
                    .filter(stamina -> testHP(pokemon, stamina, potentialLevel))
                    .mapToObj(stamina -> new HPIv(potentialLevel, stamina))
                    .forEach(hpIvs::add);
        }
        return hpIvs;
    }

    private boolean testHP(Pokemon pokemon, int stamina, Level level) {
        double theoricHp = hpCalculator.compute(pokemon, stamina, level.cpScalar);
        return pokemon.hp() ==  theoricHp;
    }

    private boolean testCP(Pokemon pokemon, int attackIV, int defenseIV, int staminaIV, Level level) {
        double theoricCp = cpCalculator.compute(pokemon, staminaIV, attackIV, defenseIV, level.cpScalar);
        return pokemon.cp() == (int)theoricCp;
    }

    private List<Level> potentialLevels(int dust) {
        Stream<Level> potentialLevels = Arrays.stream(levels).filter(l -> l.dust == dust);
        return potentialLevels.sorted(Comparator.comparing(Level::getLevel).reversed()).collect(Collectors.toList());
    }

    private static class HPIv {
        private final Level level;
        private final int stamina;

        private HPIv(Level level, int stamina) {
            this.level = level;
            this.stamina = stamina;
        }
    }

    private static class Level {
        private double level;
        private int dust;
        private int candy;
        private double cpScalar;

        public double getLevel() {
            return level;
        }

        public void setLevel(double level) {
            this.level = level;
        }

        public int getDust() {
            return dust;
        }

        public void setDust(int dust) {
            this.dust = dust;
        }

        public int getCandy() {
            return candy;
        }

        public void setCandy(int candy) {
            this.candy = candy;
        }

        public double getCpScalar() {
            return cpScalar;
        }

        public void setCpScalar(double cpScalar) {
            this.cpScalar = cpScalar;
        }
    }
}
