package com.omalgina.urlshortener.services;


import com.omalgina.urlshortener.resources.UrlMapping;

public interface UrlShortenerService {

    String getUrlHash(String fullUrl);

    UrlMapping getFullUrl(String hash);

}
