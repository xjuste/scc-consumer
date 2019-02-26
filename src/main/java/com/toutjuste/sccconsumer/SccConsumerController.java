package com.toutjuste.sccconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path="/")
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.REMOTE,
        repositoryRoot = "git://https://github.com/xjuste/sccpoc.git",
        ids = {"com.toutjuste:sccpoc:0.0.1-SNAPSHOT:stubs:8091"}
)
public class SccConsumerController {

    private final static String serviceURL = "https://jsonplaceholder.typicode.com/posts";
    private final static String localURL = "http://localhost:8091/api/endpoint1";

    private RestTemplate kataRestTemplate;

    @Autowired
    private YAMLConfig yamlConfig;

    public SccConsumerController(RestTemplate restTemplate) {
        this.kataRestTemplate = restTemplate;
    }

    @GetMapping(path="/api/endpoint1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getData() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
       ResponseEntity<String> response = kataRestTemplate.exchange(yamlConfig.getDomain() +":"+ yamlConfig.getPort() + yamlConfig.getEndpoint(),
                                                    HttpMethod.GET,
               new HttpEntity<String>(headers),
               String.class);

       return ResponseEntity.ok(response.getBody());
    }

}
