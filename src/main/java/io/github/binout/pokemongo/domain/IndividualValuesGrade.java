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

import java.math.BigDecimal;
import java.math.RoundingMode;

public class IndividualValuesGrade {

    public enum Grade {
        A,
        B,
        C,
        D
    }

    private final IndividualValues ivs;

    IndividualValuesGrade(IndividualValues ivs) {
        this.ivs = ivs;
    }

    public double rate() {
        int sum = ivs.stamina() + ivs.defense() + ivs.attack();
        return new BigDecimal(sum).multiply(new BigDecimal(2.22)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public Grade value() {
        double rate = rate();
        if (rate >= 80) {
            return Grade.A;
        } else if (rate >= 67) {
            return Grade.B;
        } else if (rate >= 51) {
            return Grade.C;
        } else {
            return Grade.D;
        }
    }

}
