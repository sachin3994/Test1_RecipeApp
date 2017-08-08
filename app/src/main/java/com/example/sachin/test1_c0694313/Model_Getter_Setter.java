package com.example.sachin.test1_c0694313;

/**
 * Created by kuljinder singh on 8/4/2017.
 */


public class Model_Getter_Setter {

    String name;
    String image;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;




    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Model_Getter_Setter(String name, String image){

        this.name = name;

        this.image = image;
        this.description=description;


    }
}
