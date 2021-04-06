package com.pz.offersservice.tags.model;

import javax.persistence.*;


@Entity
@Table(name = "tags")
public class Tag {

    private Long id;
    private String value;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
