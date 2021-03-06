package guru.springframework.springrestclient.services;

import guru.springframework.api.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ApiService {

    public List<User> getUsers(Integer limit);

    public Flux<User> getUsers(Mono<Integer> limit);
}
