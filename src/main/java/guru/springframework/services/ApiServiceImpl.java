package guru.springframework.services;

import guru.springframework.api.domain.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
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

    @Override
    public Flux<User> getUsers(Mono<Integer> limit) {

        Flux<User> userFlux = WebClient
                .create(apiURL)
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("_limit", limit.subscribe()).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class);

        return userFlux;
    }

}
