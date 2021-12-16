package ru.alina.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = {
        "classpath:root-context.xml",
        "classpath:spring-db.xml"
})
@ExtendWith(SpringExtension.class)
/* запуск файла перед каждым тестом */
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles("hsqldb")
public abstract class ServiceTest {
}
