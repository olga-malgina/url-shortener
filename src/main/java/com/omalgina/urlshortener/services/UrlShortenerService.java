package com.omalgina.urlshortener.services;


import com.omalgina.urlshortener.resources.UrlMapping;

public interface UrlShortenerService {

    UrlMapping getUrlHash(String fullUrl);

    UrlMapping getFullUrl(String hash);

}
