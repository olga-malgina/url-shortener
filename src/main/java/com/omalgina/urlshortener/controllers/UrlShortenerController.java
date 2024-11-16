package com.omalgina.urlshortener.controllers;

import com.google.code.siren4j.component.Entity;
import com.google.code.siren4j.converter.ReflectingConverter;
import com.google.code.siren4j.converter.ResourceConverter;
import com.google.code.siren4j.error.Siren4JException;
import com.omalgina.urlshortener.resources.UrlMapping;
import com.omalgina.urlshortener.services.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UrlShortenerController {

    public static final String HASH_URL = "/mappings/hash";
    public static final String FULL_URL = "/mappings/full-url";

    @Autowired
    UrlShortenerService urlShortenerService;

    @GetMapping(path = HASH_URL)
    ResponseEntity<Entity> getHash(@RequestParam String url) throws Siren4JException {
        UrlMapping response = urlShortenerService.getUrlHash(url);
        return new ResponseEntity<>(convertToSirenEntity(response), HttpStatus.OK);
    }

    @GetMapping(path = FULL_URL)
    ResponseEntity<Entity> getFullUrl(@RequestParam String hash) throws Siren4JException {
        UrlMapping mapping = urlShortenerService.getFullUrl(hash);
        if (mapping == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(convertToSirenEntity(mapping), HttpStatus.OK);
    }

    private Entity convertToSirenEntity(UrlMapping mapping) throws Siren4JException {
        ResourceConverter sirenConverter = ReflectingConverter.newInstance();
        return sirenConverter.toEntity(mapping);
    }

}
