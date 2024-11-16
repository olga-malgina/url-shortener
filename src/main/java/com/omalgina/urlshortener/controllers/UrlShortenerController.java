package com.omalgina.urlshortener.controllers;

import com.google.code.siren4j.component.Entity;
import com.google.code.siren4j.converter.ReflectingConverter;
import com.google.code.siren4j.converter.ResourceConverter;
import com.google.code.siren4j.error.Siren4JException;
import com.omalgina.urlshortener.resources.UrlMapping;
import com.omalgina.urlshortener.services.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShortenerController {

    public static final String HASH_URL = "/hash";
    public static final String FULL_URL = "/full-url";

    @Autowired
    UrlShortenerService urlShortenerService;

    @Operation(summary = "Get hash for an url")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hash provided and saved if new",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UrlMapping.class))}),
            @ApiResponse(responseCode = "400", description = "Url not provided",
                    content = @Content)
    })
    @GetMapping(HASH_URL)
    ResponseEntity<Entity> getHash(@RequestParam String url) throws Siren4JException {
        UrlMapping response = urlShortenerService.getUrlHash(url);
        return new ResponseEntity<>(convertToSirenEntity(response), HttpStatus.OK);
    }

    @Operation(summary = "Get full url from supplied hash")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Url found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UrlMapping.class))}),
            @ApiResponse(responseCode = "400", description = "Hash not provided",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Hash not found",
                    content = @Content),
    })
    @GetMapping(FULL_URL)
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
