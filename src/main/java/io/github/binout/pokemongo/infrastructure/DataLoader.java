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
package io.github.binout.pokemongo.infrastructure;

import java.util.List;
import java.util.function.Function;

public interface DataLoader {

    <T> T fromJson(Class<T> clazz);

    <T> List<T> fromTxt(Class<T> clazz, Function<String, T> consumer, int skipLines);

    default <T> List<T> fromTxt(Class<T> clazz, Function<String, T> consumer) {
        return fromTxt(clazz, consumer, 0);
    }

    static DataLoader fromFile(String file) {
        return new FileDataLoader(file);
    }
}
