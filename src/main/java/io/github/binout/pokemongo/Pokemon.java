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

import java.util.Map;

import static java.util.Optional.ofNullable;

public class Pokemon {

    private final int id;
    private final int cp;
    private final int hp;

    public Pokemon(int id, int cp, int hp) {
        this.id = id;
        this.cp = cp;
        this.hp = hp;
    }

    public int id() {
        return id;
    }

    public int cp() {
        return cp;
    }

    public int hp() {
        return hp;
    }

    public Map<Double, IndividualValue> potentialIvsByLevel(int dust) {
        return new IndividualCalculator().compute(this, dust);
    }

    public IndividualValue iv(double level, int dust) {
        return ofNullable(potentialIvsByLevel(dust).get(level))
                .orElseThrow(IllegalArgumentException::new);
    }

}
