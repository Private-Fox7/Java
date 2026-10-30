/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mongocrud;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;


/**
 *
 * @author santh
 */
public class MongoCRUD {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "MyTestDB";
    private static final String COLLECTION_NAME = "users";

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
             createDocument(collection);
             readDocuments(collection, "STD");
    
                         // U - UPDATE
            updateDocument(collection, "STD", 31);
            // R - READ (Verify Update)
            readDocuments(collection, "STD");
            // D - DELETE
            deleteDocument(collection, "STD");
            // R - READ (Verify Delete)
            readAllDocuments(collection);
                  } catch (Exception e) {
            System.out.println("Exception occured");
        }
    }
    private static void createDocument(MongoCollection<Document> collection) {
        System.out.println("\n[CREATE] Inserting a new document...");
        Document user = new Document("name", "ABC")
                            .append("age", 30)
                            .append("city", "Bengaluru");
       
        //InsertOneResult result = collection.insertOne(user);
        collection.insertOne(user);
        System.out.println("Inserted Successfully");
       //System.out.println("Inserted document with ID: " + result.getInsertedId());
    }
    
    private static void readDocuments(MongoCollection<Document> collection, String name) {
        System.out.println("\n[READ] Finding documents for name: " + name);
        Bson filter = eq("name", name);
        Document doc = collection.find(filter).first();
        if (doc != null) {
            System.out.println("Found: " + doc.toJson());
        } else {
            System.out.println("No user found with name: " + name);
        }
    }
    
    private static void readAllDocuments(MongoCollection<Document> collection) {
        System.out.println("\n[READ] Finding all remaining documents...");
        for (Document doc : collection.find()) {
            System.out.println("Remaining: " + doc.toJson());
        }
    }

    // --- U: UPDATE ---
    private static void updateDocument(MongoCollection<Document> collection, String name, int newAge) {
        System.out.println("\n[UPDATE] Updating age for " + name);
        Bson filter = eq("name", name);
        Bson updateOperation = set("age", newAge);
        UpdateResult result = collection.updateOne(filter, updateOperation);
        System.out.println("Matched count: " + result.getMatchedCount() +
                           ", Modified count: " + result.getModifiedCount());
    }

    // --- D: DELETE ---
    private static void deleteDocument(MongoCollection<Document> collection, String name) {
        System.out.println("\n[DELETE] Deleting user: " + name);
        Bson filter = eq("name", name);
            DeleteResult result = collection.deleteOne(filter);
        System.out.println("Deleted count: " + result.getDeletedCount());
    }
}



