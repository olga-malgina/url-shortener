package com.omalgina.urlshortener.services;

import com.fasterxml.uuid.Generators;
import com.github.f4b6a3.uuid.codec.base.Base62Codec;
import com.omalgina.urlshortener.repositories.UrlMappingRepository;
import com.omalgina.urlshortener.resources.UrlMappingDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Autowired
    UrlMappingRepository repository;

    public String getUrlHash(String fullUrl) {
        UrlMappingDO existingMapping = repository.findByUrl(fullUrl);
        if (existingMapping != null) {
            return existingMapping.getHash();
        }
        UUID uuid = Generators.timeBasedGenerator().generate();
        String hash = Base62Codec.INSTANCE.encode(uuid).substring(0, 8);
        UrlMappingDO urlMapping = new UrlMappingDO(hash, fullUrl);
        repository.save(urlMapping);
        return hash;
    }

}
