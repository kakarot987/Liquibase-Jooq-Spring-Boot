package com.springBootliquibasejooq;

import co.db.jooq.tables.pojos.Saiyans;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Connection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class SpringBootLiquibaseJooqApplicationTests {

    @Container
    static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("world")
            .withUsername("app")
            .withPassword("test-password-only")
            .withCommand("--lower-case-table-names=1");

    @DynamicPropertySource
    static void datasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired
    private ModelRepository repository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DataSource dataSource;

    @Test
    void migrationsSeedDataAndJooqCanWriteAndRead() {
        assertThat(repository.selectAllSaiyans()).extracting(Saiyans::getSaiyanName)
                .contains("Kakarot", "Vegeta");
        repository.addSaiyan(99, "Bardock", "male", "Pure");
        assertThat(repository.selectAllSaiyans()).extracting(Saiyans::getSaiyanName)
                .contains("Bardock");
    }

    @Test
    void schemaEvolutionAddsNameIndex() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metadata = connection.getMetaData();
            boolean found = false;
            try (ResultSet indexes = metadata.getIndexInfo(null, null, "saiyans", false, false)) {
                while (indexes.next()) {
                    if ("idx_saiyans_name".equalsIgnoreCase(indexes.getString("INDEX_NAME"))) {
                        found = true;
                        break;
                    }
                }
            }
            assertThat(found).as("Liquibase follow-up migration creates a name index").isTrue();
        }
    }

    @Test
    void endpointsValidateInputAndRejectDuplicates() throws Exception {
        mvc.perform(get("/getModels")).andExpect(status().isOk());
        mvc.perform(post("/addModel").contentType(MediaType.APPLICATION_JSON)
                .content("{\"saiyanId\":100,\"saiyanName\":\"Pan\",\"saiyanGender\":\"female\"}"))
                .andExpect(status().isCreated());
        mvc.perform(post("/addModel").contentType(MediaType.APPLICATION_JSON)
                .content("{\"saiyanId\":100,\"saiyanName\":\"Pan\"}"))
                .andExpect(status().isConflict());
        mvc.perform(post("/addModel").contentType(MediaType.APPLICATION_JSON)
                .content("{\"saiyanId\":101,\"saiyanName\":\"\"}"))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/addModel")).andExpect(status().isMethodNotAllowed());
    }
}
