package guru.springframework.services;

import guru.springframework.api.domain.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService{

    RestTemplate restTemplate;
    private final String apiURL;



    public ApiServiceImpl(RestTemplate restTemplate, @Value("${api.url}") String apiURL) {
        this.restTemplate = restTemplate;
        this.apiURL=apiURL;
    }

    @Override
    public List<User> getUsers(Integer limit) {

        UriComponentsBuilder uriBuilder=UriComponentsBuilder
                .fromUriString(apiURL)
                .queryParam("_limit",limit);

        List<User> userData = restTemplate.getForObject(uriBuilder.toUriString(), List.class);

        return userData;
    }
}
