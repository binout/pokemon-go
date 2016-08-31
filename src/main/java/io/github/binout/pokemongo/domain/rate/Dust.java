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
package io.github.binout.pokemongo.domain.rate;

import io.github.binout.pokemongo.domain.formula.LevelData;

import java.util.stream.IntStream;

public class Dust {

    private final int value;

    public Dust(int value) {
        this.value = allValues().filter(d -> d == value).findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public int value() {
        return value;
    }

    public static IntStream allValues() {
        return LevelData.allDusts();
    }
}
