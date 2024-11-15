package com.omalgina.urlshortener.resources;

import com.google.code.siren4j.annotations.Siren4JEntity;
import com.google.code.siren4j.resource.BaseResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@Siren4JEntity(name = "urlMapping", uri = "/mappings/{fullUrl}")
public class UrlMapping extends BaseResource {
    String shortUrl;
    String fullUrl;
}
