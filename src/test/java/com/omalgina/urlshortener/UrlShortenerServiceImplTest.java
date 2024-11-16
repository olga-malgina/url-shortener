package com.omalgina.urlshortener;

import com.omalgina.urlshortener.repositories.UrlMappingRepository;
import com.omalgina.urlshortener.resources.UrlMapping;
import com.omalgina.urlshortener.resources.UrlMappingDto;
import com.omalgina.urlshortener.services.UrlShortenerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlShortenerServiceImplTest {

    @Mock
    UrlMappingRepository repository;

    @InjectMocks
    private UrlShortenerServiceImpl urlShortenerService;

    private final String URL = "www.test.com";
    private final String HASH = "hash";
    private final UrlMappingDto MAPPING_DTO = new UrlMappingDto(URL, HASH);
    private final UrlMapping MAPPING = new UrlMapping(URL, HASH);

    @Test
    void testGetUrlHashNew() {
        givenMappingIsSaved();
        UrlMapping savedMapping = urlShortenerService.getUrlHash(URL);
        verify(repository, times(1)).findByUrl(URL);
        Assertions.assertEquals(MAPPING.getFullUrl(), savedMapping.getFullUrl());
        Assertions.assertEquals(MAPPING.getHash(), savedMapping.getHash());
    }

    @Test
    void testGetUrlHashExisting() {
        givenMappingIsFoundByUrl();
        UrlMapping foundMapping = urlShortenerService.getUrlHash(URL);
        verify(repository, times(1)).findByUrl(URL);
        Assertions.assertEquals(MAPPING.getFullUrl(), foundMapping.getFullUrl());
        Assertions.assertEquals(MAPPING.getHash(), foundMapping.getHash());
    }

    @Test
    void testGetFullUrl() {
        givenMappingFoundByHash();
        UrlMapping foundMapping = urlShortenerService.getFullUrl(HASH);
        verify(repository, times(1)).findByHash(HASH);
        Assertions.assertEquals(MAPPING.getFullUrl(), foundMapping.getFullUrl());
        Assertions.assertEquals(MAPPING.getHash(), foundMapping.getHash());
    }

    @Test
    void testGetFullUrlNotFound() {
        givenMappingNotFoundByHash();
        UrlMapping mapping = urlShortenerService.getFullUrl(HASH);
        verify(repository, times(1)).findByHash(HASH);
        Assertions.assertNull(mapping);
    }

    private void givenMappingIsSaved() {
        when(repository.findByUrl(URL)).thenReturn(null);
        when(repository.save(any(UrlMappingDto.class))).thenReturn(MAPPING_DTO);
    }

    private void givenMappingIsFoundByUrl() {
        when(repository.findByUrl(URL)).thenReturn(MAPPING_DTO);
    }

    private void givenMappingFoundByHash() {
        when(repository.findByHash(HASH)).thenReturn(MAPPING_DTO);
    }

    private void givenMappingNotFoundByHash() {
        when(repository.findByHash(HASH)).thenReturn(null);
    }

}
