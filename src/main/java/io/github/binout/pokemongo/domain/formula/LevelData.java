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
package io.github.binout.pokemongo.domain.formula;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class LevelData {

    private static LevelData[] ALL;

    static LevelData[] all() {
        if (ALL == null) {
            try {
                ALL = new ObjectMapper().readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("levelUpData.json"), LevelData[].class);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return ALL;
    }

    static double maxScalar() {
        return Arrays.stream(all()).max(Comparator.comparing(LevelData::getLevel)).map(LevelData::getCpScalar).orElseThrow(RuntimeException::new);
    }

    public static IntStream allDusts() {
        return Arrays.stream(all()).mapToInt(l -> l.dust).distinct();
    }

    private double level;
    private int dust;
    private int candy;
    private double cpScalar;

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public int getDust() {
        return dust;
    }

    public void setDust(int dust) {
        this.dust = dust;
    }

    public int getCandy() {
        return candy;
    }

    public void setCandy(int candy) {
        this.candy = candy;
    }

    public double getCpScalar() {
        return cpScalar;
    }

    public void setCpScalar(double cpScalar) {
        this.cpScalar = cpScalar;
    }
}
