package com.maycon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    private Long id;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String category;
}
