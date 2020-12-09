package com.mongodb.quickstart;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

public class Alinea_b {
    public static void main(String[] args) {

        String connectionString = System.getProperty("mongodb.uri");

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("cbd");
            MongoCollection<Document> rest = sampleTrainingDB.getCollection("rest");

            rest.createIndex(Indexes.ascending("localidade"));
            rest.createIndex(Indexes.ascending("gastronomia"));
            rest.createIndex(Indexes.ascending("nome"));

        }
    }
}