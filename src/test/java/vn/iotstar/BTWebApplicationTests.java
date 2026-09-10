package vn.iotstar;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:clothing;MODE=MSSQLServer;DB_CLOSE_DELAY=-1",
                "spring.datasource.username=sa",
                "spring.datasource.password=",
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        }
)
class BTWebApplicationTests {
    @Value("${local.server.port}")
    private int port;

    @Test
    void contextLoads() {
    }

    @Test
    void mainPagesRender() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        for (String path : new String[]{"/", "/home", "/product", "/login", "/register",
                "/admin/category", "/admin/product"}) {
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build();
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            assertEquals(200, response.statusCode(), "GET " + path);
            assertTrue(response.body().length > 0, "GET " + path + " returned an empty body");
            String html = new String(response.body(), StandardCharsets.UTF_8);
            assertTrue(html.contains("</html>"), "GET " + path + " did not render the shared layout");
        }
        HttpResponse<String> home = client.send(
                HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/home")).GET().build(),
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        assertTrue(home.body().contains("Sản phẩm mới nhất"), "Vietnamese UTF-8 text was not rendered correctly");
    }
}
