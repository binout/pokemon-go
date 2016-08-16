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

import io.github.binout.pokemongo.domain.IndividualValues;
import io.github.binout.pokemongo.domain.Pokemon;
import io.github.binout.pokemongo.domain.PokemonRate;
import net.codestory.http.WebServer;
import net.codestory.http.payload.Payload;

import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main(String[] args) {
        new WebServer().configure(routes ->
            routes.url("/pokemon-rates").post(context -> computeRate(context.extract(RestPokemon.class)))
        ).start();
    }

    private static Payload computeRate(RestPokemon restPokemon) {
        Pokemon pokemon = new Pokemon(restPokemon.getId(), restPokemon.getCp(), restPokemon.getHp());
        PokemonRate pokemonRate = new PokemonRate(pokemon, restPokemon.getDust());
        return new Payload(convertToRestModel(pokemonRate));
    }

    private static RestPokemonRate convertToRestModel(PokemonRate pokemonRate) {
        RestPokemonRate rate = new RestPokemonRate();
        rate.setId(pokemonRate.pokemon().id());
        rate.setCp(pokemonRate.pokemon().cp());
        rate.setHp(pokemonRate.pokemon().hp());
        rate.setName(pokemonRate.pokemon().name());
        rate.setDust(pokemonRate.dust());
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
        restIv.setPerfect(iv.perfectRate());
        return restIv;
    }

    private static class RestPokemonRate extends RestPokemon {
        private String name;
        private int maxCp;
        private int maxHp;
        private List<RestIv> ivs;

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
            if (ivs == null) {
                ivs = new ArrayList<>();
            }
            ivs.add(restIv);
        }

        public List<RestIv> getIvs() {
            return ivs;
        }

        public void setIvs(List<RestIv> ivs) {
            this.ivs = ivs;
        }
    }

    private static class RestIv {
        private double level;
        private int stamina;
        private int attack;
        private int defense;
        private double perfect;

        public double getPerfect() {
            return perfect;
        }

        public void setPerfect(double perfect) {
            this.perfect = perfect;
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
        private int cp;
        private int hp;
        private int dust;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
