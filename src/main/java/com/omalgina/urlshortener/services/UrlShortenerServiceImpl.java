package com.omalgina.urlshortener.services;

import com.fasterxml.uuid.Generators;
import com.github.f4b6a3.uuid.codec.base.Base62Codec;
import com.omalgina.urlshortener.repositories.UrlMappingRepository;
import com.omalgina.urlshortener.resources.UrlMapping;
import com.omalgina.urlshortener.resources.UrlMappingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Autowired
    UrlMappingRepository repository;

    public UrlMapping getUrlHash(String fullUrl) {
        UrlMappingDto existingMapping = repository.findByUrl(fullUrl);
        if (existingMapping != null) {
            return toUrlMapping(existingMapping);
        }
        String hash = generateHash();
        UrlMappingDto urlMapping = repository.save(new UrlMappingDto(hash, fullUrl));
        return toUrlMapping(urlMapping);
    }

    public UrlMapping getFullUrl(String hash) {
        UrlMappingDto mapping = repository.findByHash(hash);
        return mapping == null ? null : toUrlMapping(mapping);
    }

    private String generateHash() {
        UUID uuid = Generators.timeBasedGenerator().generate();
        return Base62Codec.INSTANCE.encode(uuid).substring(0, 8);
    }

    private UrlMapping toUrlMapping(UrlMappingDto urlMappingDto) {
        return new UrlMapping(urlMappingDto.getHash(), urlMappingDto.getUrl());
    }

}
