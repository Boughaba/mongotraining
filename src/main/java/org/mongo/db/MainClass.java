package org.mongo.db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BOUGHABA on 22/01/2017.
 */
public class MainClass {

    public static  void main(String[] args){
        MongoClient clientMongo = new MongoClient();

        MongoDatabase db = clientMongo.getDatabase("students");

       MongoCollection<Document> gradeCollection =  db.getCollection("grades");

       Document criteria = new Document().append("type" , "homework");
        Document beson = new Document().append("student_id",1).append("score" ,1);
       List<Document> documents = gradeCollection.find(criteria).sort(beson).into( new ArrayList<Document>());

        System.out.println("document count " + documents.size());
        Integer idTest = Integer.parseInt("-1");
       for(Document doc : documents){
           if(!idTest.equals(doc.getInteger("student_id"))) {
               System.out.println(doc.toJson());
               idTest = doc.getInteger("student_id");
               gradeCollection.deleteOne(doc);
           }
       }

    }
}
