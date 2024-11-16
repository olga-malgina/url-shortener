package com.omalgina.urlshortener;

import com.omalgina.urlshortener.controllers.UrlShortenerController;
import com.omalgina.urlshortener.resources.UrlMapping;
import com.omalgina.urlshortener.services.UrlShortenerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.omalgina.urlshortener.controllers.UrlShortenerController.FULL_URL;
import static com.omalgina.urlshortener.controllers.UrlShortenerController.HASH_URL;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = UrlShortenerController.class)
@ExtendWith(MockitoExtension.class)
public class UrlShortenerControllerTest {

    @MockBean
    UrlShortenerService urlShortenerService;

    @Autowired
    MockMvc mockMvc;

    final String URL = "www.test.com";
    final String HASH = "hash62";
    final String HASH_NOT_FOUND = "hash";
    final UrlMapping mapping = new UrlMapping(HASH, URL);

    public UrlShortenerControllerTest() {
    }

    @Test
    void testGetHash() throws Exception {
        givenUrlMappingIsFound(URL);
        ResultActions response = mockMvc.perform(get(HASH_URL + "?url=" + URL));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.properties.fullUrl", CoreMatchers.is(mapping.getFullUrl())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.properties.hash", CoreMatchers.is(mapping.getHash())));
    }

    @Test
    void testGetFullUrl() throws Exception {
        givenUrlByHashIsFound(HASH);
        ResultActions response = mockMvc.perform(get(FULL_URL + "?hash=" + HASH));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.properties.fullUrl", CoreMatchers.is(mapping.getFullUrl())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.properties.hash", CoreMatchers.is(mapping.getHash())));
    }

    @Test
    void testGetFullUrlNotFound() throws Exception {
        givenUrlByHashNotFound(HASH_NOT_FOUND);
        ResultActions response = mockMvc.perform(get(FULL_URL + "?hash=" + HASH_NOT_FOUND));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private void givenUrlMappingIsFound(String url) {
        when(urlShortenerService.getUrlHash(url)).thenReturn(mapping);
    }

    private void givenUrlByHashIsFound(String hash) {
        when(urlShortenerService.getFullUrl(hash)).thenReturn(mapping);
    }

    private void givenUrlByHashNotFound(String hash) {
        when(urlShortenerService.getFullUrl(hash)).thenReturn(null);
    }

}
