package ru.rti.crm_modules_stub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rti.crm_modules_stub.service.StubStateService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;

@RestController
@RequestMapping("/api")
public class OpvController {

    @Value("${ordering.package.validation.url}")
    private String url;
    private StubStateService stubStateService;

    @Autowired
    public OpvController(StubStateService stubStateService) {
        this.stubStateService = stubStateService;
    }


    private String server = "10.11.235.20";
    private int port = 10023;

    //@PostMapping("/re")
    @PostMapping(value = "/re", consumes = "application/json", produces = "application/json")
    public ResponseEntity redirectPostToPost(@RequestBody String body, HttpMethod method, HttpServletRequest request) throws URISyntaxException {
        String requestUrl = "/orderingPackageValidation/enrichPackageValidation";

        URI uri = new URI("http", null, server, port, null, null, null);
        uri = UriComponentsBuilder.fromUri(uri)
                .path(requestUrl)
                .query(request.getQueryString())
                .build(true).toUri();

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.exchange(uri, method, httpEntity, String.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getRawStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
        /*URI uri = null;
        try {
            uri = new URI("http", null, server, port, "/orderingPackageValidation/enrichPackageValidation", request.getQueryString(), null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(uri, method, new HttpEntity<String>(body), String.class);

        return responseEntity.getBody();*/

    }
}
