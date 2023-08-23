package com.helloWorld.rshop.repo;

import com.helloWorld.rshop.model.Schema;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepo extends MongoRepository<Schema,String>
{

}