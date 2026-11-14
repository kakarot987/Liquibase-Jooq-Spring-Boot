# Liquibase + jOOQ + Spring Boot + MySQL

An intentionally small Java 11 / Spring Boot 2.4.4 example of **versioned database migrations with Liquibase** and **type-safe SQL with jOOQ**. The generated jOOQ sources are committed, so a normal build does **not** require a running database during compilation.

## Local MySQL

Set development-only credentials in your shell (never commit real passwords):

```sh
export DB_USER=app
export DB_PASSWORD='choose-a-local-password'
export MYSQL_ROOT_PASSWORD='choose-another-local-password'
docker compose up -d mysql
```

The supplied MySQL 8 setup uses `lower-case-table-names=1` because the original Liquibase changeSets use `Saiyans` while the checked-in jOOQ generator emitted lowercase `saiyans`. Configure this before first creating the volume; changing it on an existing volume is not supported. Use `docker compose down -v` **only if you intend to delete all local database data**.

## Run the application

```sh
./gradlew bootRun
```

Liquibase applies changesets on startup. API examples:

```sh
curl http://localhost:8080/getModels
curl -i -X POST http://localhost:8080/addModel -H 'Content-Type: application/json' -d '{"saiyanId":42,"saiyanName":"Bardock","saiyanGender":"male","saiyanBlood":"Pure"}'
```

POST returns 201 on success, 400 for an invalid id/name and 409 for a duplicate key.

## Regenerate jOOQ sources

Start MySQL using the Compose command above, then run the single generator command below. Gradle automatically runs **Liquibase first**, applying all migrations, and then jOOQ introspects the migrated database:

```sh
./gradlew generateJooq
```

The migration and generator share `DB_URL` (defaults to `jdbc:mysql://127.0.0.1:3306/world`), `DB_USER`, and a required `DB_PASSWORD`. Normal compilation uses checked-in generated sources and does not connect to MySQL. The sample forward migration adds an index on `saiyan_name`, leaving the generated POJO shape unchanged. See [architecture](docs/architecture.md) for the migration/codegen flow.

## Tests and CI

```sh
./gradlew test
```

Tests start an isolated MySQL 8 Testcontainers database, run Liquibase on Spring context initialization, and verify reads, writes, HTTP methods, validation, duplicate IDs, and the migration-added index. CI separately validates the migration-first jOOQ generator against a disposable MySQL service. Docker is required for these integration tests.

This is the **preferred maintained jOOQ/Liquibase example**. The older `Spring-Data-Jooq-Liquibase-Mysql` project remains a learning/reference version.
