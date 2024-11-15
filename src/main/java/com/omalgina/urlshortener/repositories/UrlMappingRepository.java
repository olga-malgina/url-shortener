package com.omalgina.urlshortener.repositories;

import com.omalgina.urlshortener.resources.UrlMappingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMappingDto, String> {

    UrlMappingDto findByUrl(String url);

    UrlMappingDto findByHash(String hash);
}
