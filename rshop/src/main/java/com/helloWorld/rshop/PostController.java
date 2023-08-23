package com.helloWorld.rshop;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;

import com.helloWorld.rshop.model.Schema;
import com.helloWorld.rshop.repo.PostRepo;
import com.helloWorld.rshop.repo.SearchRepo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import springfox.documentation.annotations.ApiIgnore;

@RestController
public class PostController {

    @Autowired //sting will create obj and map it
    PostRepo repo;

    @Autowired
    SearchRepo srepo;
    
    @ApiIgnore
    @RequestMapping(value = "/")
    public void redirect(HttpServletResponse response) throws IOException{

        response.sendRedirect("./swagger-uo.html");
    }



    @GetMapping(value="/posts")

    public List<Schema> getAllPosts(){

        return repo.findAll();
    }

    @GetMapping(value="/posts/{id}")
    // public ResponseEntity<Schema> getPostById(@PathVariable("id") String id) {
    //     System.out.println(id);
    //     Optional<Schema> optionalSchema = repo.findById(id);
    //     System.out.println(id);
    //     if (optionalSchema.isPresent()) {
    //         System.out.println(id);
    //         return new ResponseEntity<>(optionalSchema.get(), HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }
    public static Schema findCustomerById(String id) {
        try (MongoClient mongoClient = MongoClients.create("rshop")) {
            MongoDatabase database = mongoClient.getDatabase("idk");
            Bson filter = Filters.eq("_id", new ObjectId(id));
            MongoCollection<Document> collection = database.getCollection("customer");
    
            // Tries to find the document
            Document doc = collection.find(filter).first();
            
            // Convert the document to a Schema object
            Schema schema = new Schema();
            schema.setId(doc.getObjectId("_id").toString()); // Assuming "id" is of type ObjectId
            
            // Set other properties using doc.get(...) for each field
            
            return schema;
        } catch (Exception e) {
            System.out.println("Error finding document");
            System.out.println("Error in: " + e.getMessage());
            e.printStackTrace();
            return null; // Return null or throw an exception based on your error handling strategy
        }
    }
    

    @PostMapping(value="/post")
    public Schema addPost(@RequestBody Schema schema){
        
        return repo.save(schema);
    }

    @DeleteMapping(value="/del/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") String id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>("Item with ID " + id + " deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting item with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}
