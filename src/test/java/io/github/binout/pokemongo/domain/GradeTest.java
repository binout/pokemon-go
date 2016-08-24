package io.github.binout.pokemongo.domain;

import org.testng.annotations.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class GradeTest {

    @Test
    public void should_load_message() {
        String message = IndividualValuesGrade.Grade.A.message(Locale.ENGLISH, Trainer.Team.RED, new PokemonName("Pikachu"));
        assertThat(message).isNotNull();
    }
}