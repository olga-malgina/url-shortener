package com.omalgina.urlshortener.resources;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table
@Data
@NoArgsConstructor
public class UrlMappingDto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column
    private String hash;
    @Column
    private String url;

    public UrlMappingDto(String hash, String url) {
        this.hash = hash;
        this.url = url;
    }

}
