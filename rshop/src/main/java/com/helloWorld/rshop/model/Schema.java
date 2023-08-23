package com.helloWorld.rshop.model;

import org.springframework.data.annotation.Id;
// import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "idk")
public class Schema {
    // @Id
    // @Field(targetType = FieldType.IMPLICIT)
    private String _id; // Change the type to String
    private String name;
    private int price;
    private String image;

    public Schema() {
    }

    public String getId() {
        return _id;
    }

    // Custom setter to ensure the ID only contains numeric characters
    public void setId(int id) {
        this._id = Integer.toString(id);
    }

    // Custom setter to ensure the ID only contains numeric characters
    public void setId(String id) {
        if (id.matches("\\d+")) { // Check if the string contains only digits
            this._id = id;
        } else {
            throw new IllegalArgumentException("ID must contain only numeric characters.");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
