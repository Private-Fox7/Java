

package com.mycompany.prg1_mongo1;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


/**
 *
 * @author santh
 */
public class PRG1_MONGO1 {

    public static void main(String[] args) {
        MongoClient mongoClient = null;
        try {
            // Connect to MongoDB server
            mongoClient = new MongoClient("localhost", 27017);
            MongoDatabase database =  mongoClient.getDatabase("MongoFP2");
            database.runCommand( new org.bson.Document("ping", 1));
            System.out.println("MongoDB Connected Successfully!");
            System.out.println("Database Name: " + database.getName());
        } 
        catch (Exception e) {
            System.out.println( "MongoDB Connection Failed!");
            System.out.println(e.getMessage());
        } 
        finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }
}

        
    
