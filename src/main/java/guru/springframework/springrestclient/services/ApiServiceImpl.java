package guru.springframework.springrestclient.services;

import guru.springframework.api.domain.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

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

        return limit.flatMapMany(limitInteger ->{
            return WebClient
                    .create(apiURL)
                    .get()
                    .uri(uriBuilder -> uriBuilder.queryParam("_limit", limitInteger.toString()).build())
                    //.uri(uriBuilder -> uriBuilder.queryParam("_limit", 7).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(User.class);

        });
    }

}
