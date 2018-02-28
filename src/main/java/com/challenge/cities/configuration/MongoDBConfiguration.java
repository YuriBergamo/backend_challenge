package com.challenge.cities.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages={"com.challenge.cities.database.repository"},
        mongoTemplateRef="mongoTemplate"
)
public class MongoDBConfiguration {

    @Bean()
    @Primary
    public MongoTemplate mongoTemplate(MongoDbFactory factory){
        return new MongoTemplate(factory);
    }
}
