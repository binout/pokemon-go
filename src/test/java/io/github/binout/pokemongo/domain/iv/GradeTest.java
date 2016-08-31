package io.github.binout.pokemongo.domain.iv;

import io.github.binout.pokemongo.domain.PokemonName;
import io.github.binout.pokemongo.domain.Trainer;
import io.github.binout.pokemongo.domain.iv.IndividualValuesGrade;
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