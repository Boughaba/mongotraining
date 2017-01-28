package org.mongo.db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.mongo.db.model.Score;
import org.mongo.db.model.Student;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by BOUGHABA on 22/01/2017.
 */
public class MainClass {

    public static  void main(String[] args){

        final Morphia morphia = new Morphia();
        MongoClient clientMongo = new MongoClient();

        morphia.mapPackage("org.mongo.db.model");

        final Datastore datastore = morphia.createDatastore(clientMongo, "school");

        Set<String> collection = datastore.getDB().getCollectionNames();

        System.out.println( collection.toString());
  /*      MongoDatabase db = clientMongo.getDatabase("students");

        MongoCollection<Document> gradeCollection =  db.getCollection("grades");

        CollectionCleaner cleaner =  new CollectionCleaner();
        Document criteria = new Document().append("type" , "homework");
        Document beson = new Document().append("student_id",1).append("score" ,1);
        List<Document> documents = gradeCollection.find(criteria).sort(beson).into( new ArrayList<Document>());
        cleaner.removeLowScore(documents,gradeCollection);
        System.out.println("document count " + documents.size());
*/

        final Query<Student> query = datastore.createQuery(Student.class);
            final List<Student> students = query.asList();
            students.forEach(p -> {
               Optional<Score> minimumScore =  p.getScores().stream().filter(type -> "homework".equals(type.getType())).
                       sorted((first, second )-> first.getScore().compareTo(second.getScore())  ).findFirst();
               if(minimumScore.isPresent()){
                   System.out.println(minimumScore.get().getScore());
                 p.getScores().remove(minimumScore.get());

                   datastore.save(p);
               }
            });
    }


}
