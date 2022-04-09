package tyler2cr.spring.testcontainers;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@DirtiesContext
public abstract class TestContainersTest {

    @Container
    public static PostgreSQLContainer<?> sharedPostgresDatabase =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:11.6"))
                    .withDatabaseName("test")
                    .withExposedPorts(5432)
                    .withStartupAttempts(2);

    @DynamicPropertySource
    static void aimsPlusPlusDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sharedPostgresDatabase::getJdbcUrl);
        registry.add("spring.datasource.username", sharedPostgresDatabase::getUsername);
        registry.add("spring.datasource.password", sharedPostgresDatabase::getPassword);
    }
}