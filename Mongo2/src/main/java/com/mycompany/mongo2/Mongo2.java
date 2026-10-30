/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mongo2;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.FindIterable;

/**
 *
 * @author santh
 */
public class Mongo2 {

    public static void main(String[] args) {
        MongoClient mongoClient=new MongoClient("localhost",27017);
        MongoDatabase database=mongoClient.getDatabase("MongoFP2");
        MongoCollection<Document>collection=database.getCollection("testCollection");
        if(collection==null)
        {
            database.createCollection("testCollection");
            collection=database.getCollection("testCollection");                   }
        Document doc1=new Document("name","AAAA")
        .append("age",25);
        Document doc2=new Document("name","BBBB")
        .append("age",30)
        .append("city","Mumbai")
        .append("city","Bangaluru");
        collection.insertOne(doc1);
        collection.insertOne(doc2);
        FindIterable<Document> documents=collection.find();   
        System.out.println("Documents in the collection" + collection.countDocuments());
        for(Document doc:documents){
            System.out.println(doc.toJson());
        }
            mongoClient.close();   }
}

