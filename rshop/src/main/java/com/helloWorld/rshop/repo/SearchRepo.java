package com.helloWorld.rshop.repo;

import java.util.List;

import com.helloWorld.rshop.model.Schema;

public interface SearchRepo {
    
    List<Schema> findById(String id);
}
