package com.omalgina.urlshortener.repositories;

import com.omalgina.urlshortener.resources.UrlMappingDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMappingDO, String> {

    UrlMappingDO findByUrl(String url);

    UrlMappingDO findByHash(String hash);
}
