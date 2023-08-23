package com.helloWorld.rshop.repo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import com.helloWorld.rshop.model.Schema;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


@Component
public class SearchRepoImp implements SearchRepo  {
    
    @Autowired
    MongoClient client;

    @Autowired
    MongoConverter converter;

    @Override
    public List<Schema> findById(String id){
        
        final List<Schema> schemas =new ArrayList<>();
        MongoDatabase database = client.getDatabase("rshop");
        MongoCollection<Document> collection = database.getCollection("idk");

        Document matchQuery = new Document("$match",
                new Document("post.id", id));

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
                matchQuery,
                new Document("$search",
                        new Document("text",
                                new Document("query", id)
                                        .append("path", "post/id")))));

        result.forEach(doc -> schemas.add(converter.read(Schema.class, doc)));
        return schemas;
    }
}

