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

import io.github.binout.pokemongo.domain.*;
import net.codestory.http.Context;
import net.codestory.http.WebServer;
import net.codestory.http.payload.Payload;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class Server {

    public static void main(String[] args) {
        new WebServer().configure(routes -> {
                    routes.url("/pokemon-rates").post(context -> computeRate(language(context), context.extract(RestPokemon.class)));
                    routes.url("/dusts").get(context -> dusts());
                    routes.url("/pokedex").get(context -> pokedex(language(context)));
                }
        ).start();
    }

    private static Locale language(Context context) {
        return ofNullable(context.header("Accept-Language")).map(Locale::forLanguageTag).orElse(Locale.ENGLISH);
    }

    private static Payload dusts() {
        return new Payload(Dust.allValues().sorted().mapToObj(Integer::new).collect(Collectors.toList()));
    }

    private static Payload pokedex(Locale locale) {
        return new Payload(Pokedex.get().allIds().map(id -> {
            RestPokedexItem item = new RestPokedexItem();
            item.setId(id.value());
            item.setName(Pokedex.get().getNameOf(id).getName(locale));
            return item;
        }).collect(Collectors.toList()));
    }

    private static Payload computeRate(Locale locale, RestPokemon restPokemon) {
        Pokemon pokemon = new Pokemon(new PokemonId(restPokemon.getId()), restPokemon.getCp(), restPokemon.getHp());
        PokemonRate pokemonRate = new PokemonRate(restPokemon.getTrainer(), pokemon, new Dust(restPokemon.getDust()));
        return new Payload(convertToRestModel(pokemonRate, locale));
    }

    private static RestPokemonRate convertToRestModel(PokemonRate pokemonRate, Locale locale) {
        RestPokemonRate rate = new RestPokemonRate();
        rate.setId(pokemonRate.pokemon().id().value());
        rate.setTrainer(pokemonRate.trainer());
        rate.setDate(DateTimeFormatter.ISO_DATE.format(pokemonRate.date()));
        rate.setCp(pokemonRate.pokemon().cp());
        rate.setHp(pokemonRate.pokemon().hp());
        rate.setName(pokemonRate.pokemon().name().getName(locale));
        rate.setDust(pokemonRate.dust().value());
        rate.setMaxCp(pokemonRate.pokemon().maxCp());
        rate.setMaxHp(pokemonRate.pokemon().maxHp());
        pokemonRate.ivsByLevel().forEach((level, iv) -> rate.addIv(convertToRestModel(level, iv)));
        return rate;
    }

    private static RestIv convertToRestModel(Double level, IndividualValues iv) {
        RestIv restIv = new RestIv();
        restIv.setLevel(level);
        restIv.setAttack(iv.attack());
        restIv.setDefense(iv.defense());
        restIv.setStamina(iv.stamina());
        restIv.setRate(iv.grade().rate());
        restIv.setGrade(iv.grade().value().name());
        return restIv;
    }

    private static class RestPokedexItem {
        private int id;
        private String name;

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
    }

    private static class RestPokemonRate extends RestPokemon {
        private String name;
        private String date;
        private int maxCp;
        private int maxHp;
        private List<RestIv> ivs = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMaxCp() {
            return maxCp;
        }

        public void setMaxCp(int maxCp) {
            this.maxCp = maxCp;
        }

        public int getMaxHp() {
            return maxHp;
        }

        public void setMaxHp(int maxHp) {
            this.maxHp = maxHp;
        }

        public void addIv(RestIv restIv) {
            ivs.add(restIv);
        }

        public List<RestIv> getIvs() {
            return ivs;
        }

        public void setIvs(List<RestIv> ivs) {
            this.ivs = ivs;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    private static class RestIv {
        private double level;
        private int stamina;
        private int attack;
        private int defense;
        private double rate;
        private String grade;

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public double getLevel() {
            return level;
        }

        public void setLevel(double level) {
            this.level = level;
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

    private static class RestPokemon {
        private int id;
        private String trainer;
        private int cp;
        private int hp;
        private int dust;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTrainer() {
            return trainer;
        }

        public void setTrainer(String trainer) {
            this.trainer = trainer;
        }

        public int getCp() {
            return cp;
        }

        public void setCp(int cp) {
            this.cp = cp;
        }

        public int getHp() {
            return hp;
        }

        public void setHp(int hp) {
            this.hp = hp;
        }

        public int getDust() {
            return dust;
        }

        public void setDust(int dust) {
            this.dust = dust;
        }
    }
}
