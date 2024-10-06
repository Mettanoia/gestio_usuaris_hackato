package com.solafides.gestio_usuaris_hackato.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

@Configuration
@EnableR2dbcRepositories
public class DataInitializer {

    private static final String INSERT_USERS = """
                INSERT INTO users (name, surname, email) VALUES
                ('John', 'Doe', 'john.doe@example.com'),
                ('Jane', 'Doe', 'jane.doe@example.com')
            """;

    private static final String INSERT_TASKS = """
                INSERT INTO tasks (name, description, max_capacity) VALUES
                ('Database Design', 'Learn how to design relational databases.', 30),
                ('Spring Boot Mastery', 'Master Spring Boot with hands-on tasks.', 25)
            """;

    private static final String INSERT_ENROLLMENTS = """
                INSERT INTO users_tasks (user_id, task_id) VALUES
                (1, 1),
                (2, 2),
                (1, 2)
            """;

    @Bean
    public ApplicationRunner initializeData(DatabaseClient client) {
        return args ->
                insertUsers(client)
                        .then(insertTasks(client))
                        .then(insertEnrollments(client))
                        .subscribe();
    }

    private Mono<Void> insertUsers(DatabaseClient client) {
        return client.sql(INSERT_USERS)
                .fetch()
                .rowsUpdated()
                .then();
    }

    private Mono<Void> insertTasks(DatabaseClient client) {
        return client.sql(INSERT_TASKS)
                .fetch()
                .rowsUpdated()
                .then();
    }

    private Mono<Void> insertEnrollments(DatabaseClient client) {
        return client.sql(INSERT_ENROLLMENTS)
                .fetch()
                .rowsUpdated()
                .then();
    }

}
