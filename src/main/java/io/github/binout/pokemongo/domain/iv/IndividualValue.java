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
package io.github.binout.pokemongo.domain.iv;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IndividualValue {

    private static final int MAX_IV = 15;
    private static final int MIN_IV = 0;

    private final int value;

    public IndividualValue(int value) {
        if (value<MIN_IV || value>MAX_IV) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static Stream<IndividualValue> range() {
        return IntStream.range(MIN_IV, MAX_IV+1).mapToObj(IndividualValue::new);
    }

    public static IndividualValue max() {
        return new IndividualValue(MAX_IV);
    }
}
