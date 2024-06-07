package com.spring.dongnae.socket.config;

import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.spring.dongnae.utils.properties.PropertiesConfig;

@Configuration
@EnableMongoRepositories(basePackages = "com.spring.dongnae.socket.scheme")
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        String connectionString = PropertiesConfig.getMongoDB();
        return new MongoClient(new MongoClientURI(connectionString));
    }

    @Bean
    public SimpleMongoDbFactory mongoDbFactory(MongoClient mongoClient) {
        return new SimpleMongoDbFactory(mongoClient, "mydatabase");
    }

    @Bean
    public MongoTemplate mongoTemplate(SimpleMongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

    @Bean
    public boolean testConnection(MongoClient mongoClient) {
        try {
            MongoDatabase database = mongoClient.getDatabase("admin");
            database.runCommand(new Document("ping", 1));
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}