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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PokemonName {

    private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
    private Map<Locale, String> names;

    public PokemonName(String name) {
        this.names = new HashMap<>();
        setName(DEFAULT_LOCALE, name);
    }

    public void setName(Locale locale, String name) {
        this.names.put(locale, name);
    }

    public String getName(Locale locale) {
        return this.names.getOrDefault(locale, this.names.get(DEFAULT_LOCALE));
    }

}
