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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Pokedex {

    private static Pokedex INSTANCE;

    public static Pokedex get() {
        if (INSTANCE == null) {
            INSTANCE = new Pokedex();
        }
        return INSTANCE;
    }

    private final Map<Integer, Pokemon> allById;

    private Pokedex() {
        try {
            Pokemon[] pokemons = new ObjectMapper().readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("pokedex.json"), Pokemon[].class);
            allById = Arrays.stream(pokemons).collect(Collectors.toMap(Pokemon::getId, Function.identity()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public int count() {
        return allById.size();
    }

    public String getNameOf(int id) {
        return getOf(id, Pokemon::getName);
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
}
