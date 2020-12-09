package com.mongodb.quickstart;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;


public class Alinea_c {
    private final MongoCollection<Document> rest;

    public Alinea_c(MongoCollection<Document> rest) {
        this.rest = rest;
    }

    public static void main(String[] args) {
        String connectionString = System.getProperty("mongodb.uri");

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("cbd");
            MongoCollection<Document> rest = sampleTrainingDB.getCollection("rest");
            Alinea_c c = new Alinea_c(rest);

            System.out.println("\nNumero de localidades distintas: " + c.countLocalidades());

            System.out.println("\nNumero de restaurantes por localidade: ");
            Map<String, Integer> countRestByLocalidade = c.countRestByLocalidade();
            countRestByLocalidade.forEach((localidade, count) -> System.out.println(localidade + " -> " + count));

            System.out.println("\nNumero de restaurantes por localidade por gastronomia: ");
            Map<String, Integer> countRestByLocalidadeByGastronomia = c.countRestByLocalidadeByGastronomia();
            countRestByLocalidadeByGastronomia.forEach((localidade, gastronomias) -> System.out.println(localidade + " - " + gastronomias));

            System.out.println("\nNome de restaurantes contendo Park no nome: ");
            List<String> getRestWithNameCloserTo = c.getRestWithNameCloserTo();
            getRestWithNameCloserTo.forEach(System.out::println);


        }
    }

    public Map<String, Integer> countRestByLocalidadeByGastronomia() {
        Map<String, Integer> rests = new HashMap<>();

        Iterable<Document> result = this.rest.aggregate(Collections.singletonList(
                new Document("$group", new Document()
                        .append("_id", new Document().append("localidade", "$localidade").append("gastronomia", "$gastronomia"))
                        .append("count", new Document("$sum", 1))
                )));

        result.forEach((doc) -> {
            Document _id = (Document) doc.get("_id");
            rests.put(_id.getString("localidade") + " | " + _id.getString("gastronomia"), (Integer) doc.get("count"));
        });
        return rests;
    }

    public List<String> getRestWithNameCloserTo() {
        List<String> rests = new ArrayList<>();

        // este regex quer dizer para aceitar tudo para tras e tudo para a frente
        Document doc = new Document("nome", new Document("$regex", java.util.regex.Pattern.compile(".*Park.*")));
        this.rest.find(doc).forEach((obj) -> rests.add((String) obj.get("nome")));
        return rests;
    }

    public int countLocalidades() {
        List<String> distinct = this.rest.distinct("localidade", String.class).into(new ArrayList<>());
        return distinct.size();
    }

    public Map<String, Integer> countRestByLocalidade() {
        Map<String, Integer> rests = new HashMap<>();

        Iterable<Document> result = this.rest.aggregate(Collections.singletonList(
                new Document("$group", new Document().append("_id", "$localidade").append("count", new Document("$sum", 1)))
        ));
        result.forEach((doc) -> rests.put((String) doc.get("_id"), (Integer) doc.get("count")));
        return rests;

    }
}
