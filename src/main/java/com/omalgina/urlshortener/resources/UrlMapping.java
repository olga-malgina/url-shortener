package com.omalgina.urlshortener.resources;

import com.google.code.siren4j.annotations.Siren4JEntity;
import com.google.code.siren4j.resource.BaseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Siren4JEntity(name = "urlMapping", uri = "/mappings/full-url?hash={hash}")
public class UrlMapping extends BaseResource {
    String hash;
    String fullUrl;

    public UrlMapping(String hash, String fullUrl) {
        this.hash = hash;
        this.fullUrl = fullUrl;
    }
}
