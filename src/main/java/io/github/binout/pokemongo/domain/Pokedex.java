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
package io.github.binout.pokemongo.domain;

import io.github.binout.pokemongo.infrastructure.DataLoader;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pokedex {

    private static Pokedex INSTANCE;

    public static Pokedex get() {
        if (INSTANCE == null) {
            INSTANCE = new Pokedex();
        }
        return INSTANCE;
    }

    private final Map<Integer, Pokemon> allById;
    private final Map<Integer, PokeName> allNames;

    private Pokedex() {
        allById = Arrays.stream(DataLoader.fromFile("pokedex.json").fromJson(Pokemon[].class))
                .collect(Collectors.toMap(Pokemon::getId, Function.identity()));

        allNames = DataLoader.fromFile("pokenames.txt").fromTxt(PokeName.class, line -> {
            PokeName pokeName = new PokeName();
            String[] tokens = line.split(" ");
            pokeName.setId(Integer.parseInt(tokens[0]));
            pokeName.setFr(tokens[1]);
            pokeName.setEn(tokens[2]);
            pokeName.setJp(tokens[3]);
            return pokeName;
        }).stream().collect(Collectors.toMap(PokeName::getId, Function.identity()));
    }

    public IntStream allIds() {
        return allById.keySet().stream().mapToInt(i -> i);
    }

    public int count() {
        return allById.size();
    }

    public PokemonName getNameOf(int id) {
        PokeName pokeName = Optional.ofNullable(allNames.get(id)).orElseThrow(IllegalArgumentException::new);
        PokemonName pokemonName = new PokemonName(pokeName.getEn());
        pokemonName.setName(Locale.FRENCH, pokeName.getFr());
        pokemonName.setName(Locale.JAPANESE, pokeName.getJp());
        return pokemonName;
    }

    public int getStaminaOf(int id) {
        return getOf(id, Pokemon::getStamina);
    }

    public int getAttackOf(int id) {
        return getOf(id, Pokemon::getAttack);
    }

    public int getDefenseOf(int id) {
        return getOf(id, Pokemon::getDefense);
    }

    private <T> T getOf(int id, Function<Pokemon, T> getter) {
        return Optional.ofNullable(allById.get(id)).map(getter).orElseThrow(IllegalArgumentException::new);
    }

    private static class Pokemon {
        private int id;
        private String name;
        private int stamina;
        private int attack;
        private int defense;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStamina() {
            return stamina;
        }

        public void setStamina(int stamina) {
            this.stamina = stamina;
        }

        public int getAttack() {
            return attack;
        }

        public void setAttack(int attack) {
            this.attack = attack;
        }

        public int getDefense() {
            return defense;
        }

        public void setDefense(int defense) {
            this.defense = defense;
        }
    }

    private static class PokeName {
        private int id;
        private String fr;
        private String en;
        private String jp;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFr() {
            return fr;
        }

        public void setFr(String fr) {
            this.fr = fr;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getJp() {
            return jp;
        }

        public void setJp(String jp) {
            this.jp = jp;
        }
    }
}
