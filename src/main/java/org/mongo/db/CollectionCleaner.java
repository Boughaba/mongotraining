package org.mongo.db;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BOUGHABA on 28/01/2017.
 */
public class CollectionCleaner {

    public void removeLowScore(List<Document> documents, MongoCollection<Document> gradeCollection){

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
