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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class IndividualCalculator {

    private final Pokedex pokedex;
    private final Level[] levels;

    IndividualCalculator() {
        try {
            pokedex = new Pokedex();
            levels = new ObjectMapper().readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("levelUpData.json"), Level[].class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    Map<Double, IndividualValue> compute(Pokemon pokemon, int dust) {
        Map<Double, IndividualValue> ivs = new HashMap<>();
        List<Level> potentialLevels = potentialLevels(dust);
        List<HPIv> hpivs = hpivs(pokemon, potentialLevels);
        for (HPIv hpiv : hpivs) {
            int stamina = hpiv.stamina;
            Level level = hpiv.level;
            IntStream.range(0, 16).forEach(attack -> IntStream.range(0, 16)
                    .filter(defense -> testCP(pokemon, attack, defense, stamina, level))
                    .mapToObj(defense -> new IndividualValue(stamina, attack, defense))
                    .forEach(iv -> ivs.put(convertLevel(level.level), iv)));
        }
        return ivs;
    }

    private Double convertLevel(int level) {
        return new BigDecimal(level).divide(BigDecimal.valueOf(2)).add(new BigDecimal(0.5)).doubleValue();
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
        double theoricHp = Math.floor((pokedex.getStaminaOf(pokemon.id()) + stamina) * level.cpScalar);
        return pokemon.hp() ==  theoricHp;
    }

    private boolean testCP(Pokemon pokemon, int attackIV, int defenseIV, int staminaIV, Level level) {
        double attackFactor = pokedex.getAttackOf(pokemon.id()) + attackIV;
        double defenseFactor = Math.pow(pokedex.getDefenseOf(pokemon.id()) + defenseIV, 0.5);
        double staminaFactor = Math.pow((pokedex.getStaminaOf(pokemon.id()) + staminaIV), 0.5);
        double scalarFactor = Math.pow(level.cpScalar, 2);
        double theoricCp = new BigDecimal(attackFactor)
                .multiply(new BigDecimal(defenseFactor))
                        .multiply(new BigDecimal(staminaFactor))
                                .multiply(new BigDecimal(scalarFactor))
                                        .divide(new BigDecimal(10)).doubleValue();
        return pokemon.cp() == Math.floor(theoricCp);
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
        private int level;
        private int dust;
        private int candy;
        private double cpScalar;

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
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
