package com.mongodb.quickstart;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class Alinea_a {


    private MongoCollection<Document> collection;

    private int curr_max_id;

    public Alinea_a(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public static void main(String[] args) {
        String connectionString = System.getProperty("mongodb.uri");
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("cbd");
            MongoCollection<Document> rest = sampleTrainingDB.getCollection("rest");
            Alinea_a my_obj = new Alinea_a(rest);
            my_obj.insert();
            my_obj.search();
        }
    }

    public void search() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Field to search: ");
        String field = sc.nextLine();

        System.out.print("Value: ");
        String value = sc.nextLine();

        Document query = new Document();
        query.put(field, value);
        MongoCursor<Document> cursor = collection.find(query).iterator();
        while (cursor.hasNext()) {
            System.out.println("Result: " + cursor.next());
        }

    }

    public void edit(int id) {

        Document query = new Document();
        query.append("restaurant_id", "rest");
        Document setData = new Document();
        setData.append("status", 1).append("instagram.likes", 125);
        Scanner sc = new Scanner(System.in);

        System.out.println("address ");
        String a = sc.nextLine();

        System.out.println("building ");
        String b = sc.nextLine();

        System.out.println("lat ");
        Double c1 = sc.nextDouble();

        System.out.println("long");
        Double c2 = sc.nextDouble();

        System.out.println("rua");
        String r = sc.nextLine();

        System.out.println("zipcode ");
        String z = sc.nextLine();

        System.out.println("localidade ");
        String l = sc.nextLine();

        System.out.println("gastronomia ");
        String g = sc.nextLine();

        System.out.println("grades >>> ");
        System.out.println("grade ");
        String gr = sc.nextLine();

        System.out.print("date ");
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String date = df.format(new Date());
        System.out.println(date);

        System.out.println("address ");
        int score = sc.nextInt();

        Document update = new Document();
        update.append("$set", setData);
        collection.updateOne(query, update);
    }

    public void insert() {
        Scanner sc = new Scanner(System.in);
        Document rest = new Document("_id", new ObjectId());
        int id = setId();
        System.out.println("id " + id);

        String a = "address" + id;
        String b = "building" + id;
        Double c1 = Math.random() * (90 - (-90));
        Double c2 = Math.random() * (80 - (-180));
        String r = "rua" + id;
        String z = "zipcode" + id;
        String l = "localidade" + id;
        String g = "gastronomia" + id;
        String grs = "grades" + id;
        String gr = "grade" + id;
        String date = "date" + id;
        int score = (int) (Math.random() * 20);
        String n = "nome" + id;

        rest.append("address", a)
                .append("building", b)
                .append("coord", Arrays.asList(c1, c2))
                .append("rua", r).append("zipcode", z)
                .append("localidade", l).append("gastronomia", g)
                .append("grades", Arrays.asList(date, gr, score))
                .append("nome", n)
                .append("restaurant_id", id);

        collection.insertOne(rest);

        System.out.println("Inserted a new restaurant! ID: " + id);
    }

    public Integer setId() {
        collection.find().iterator();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {

                int curr_id = Integer.parseInt(cursor.next().get("restaurant_id").toString());
                if (curr_id > this.curr_max_id) this.curr_max_id = curr_id;
            }
        }

        this.curr_max_id++;
        return this.curr_max_id;
    }
}
