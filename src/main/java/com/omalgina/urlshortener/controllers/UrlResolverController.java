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
public class UrlResolverController {

    public static final String HASH_URL = "/mappings/hash";
    public static final String FULL_URL = "/mappings/full-url";

    @Autowired
    UrlShortenerService urlShortenerService;

    @GetMapping(path = HASH_URL)
    ResponseEntity<Entity> getHash(@RequestParam String url) throws Siren4JException {
        String hash = urlShortenerService.getUrlHash(url);
        UrlMapping dummy = new UrlMapping(url, hash);
        ResourceConverter converter = ReflectingConverter.newInstance();
        Entity dummyResponse = converter.toEntity(dummy);
        return new ResponseEntity<>(dummyResponse, HttpStatus.OK);
    }

    @GetMapping(path = FULL_URL)
    ResponseEntity<Entity> getFullUrl(@RequestParam String hash) throws Siren4JException {
        UrlMapping mapping = urlShortenerService.getFullUrl(hash);
        if (mapping == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ResourceConverter converter = ReflectingConverter.newInstance();
        Entity response = converter.toEntity(mapping);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
