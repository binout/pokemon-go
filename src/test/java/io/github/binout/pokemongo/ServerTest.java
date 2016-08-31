package io.github.binout.pokemongo;

import com.github.kevinsawicki.http.HttpRequest;
import io.github.binout.pokemongo.domain.Pokedex;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ServerTest {

    private Server server;
    private String baseUrl;

    @BeforeClass
    public void startServer() {
        this.server = new Server();
        int port = server.startOnRandomPort();
        this.baseUrl = "http://localhost:"+port;
    }

    @AfterClass
    public void stopServer() {
        this.server.stop();
    }

    @Test
    public void should_display_home() {
        assertThat(HttpRequest.get(baseUrl).ok()).isTrue();
    }

    @Test
    public void should_rate_all_pokemons() {
        Pokedex.get().allIds().forEach(id -> {
            String json = "{\"id\": " + id.value() + ", \"team\": \"RED\", \"trainer\": \"Sacha\", \"cp\": 0, \"hp\": 0, \"dust\": 200}";
            assertThat(HttpRequest.post(baseUrl + "/pokemon-rates").contentType("application/json").send(json.getBytes()).ok()).isTrue();
        });
    }
}