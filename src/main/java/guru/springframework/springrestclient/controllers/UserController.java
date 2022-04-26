package guru.springframework.springrestclient.controllers;

import guru.springframework.springrestclient.services.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class UserController {

    private ApiService apiService;

    public UserController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }

    @PostMapping("/users")
    public String formPost(Model model, ServerWebExchange serverWebExchange) {

        model.addAttribute("users",
                apiService.getUsers(
                        serverWebExchange
                        .getFormData()
                        .flatMap(data -> {
                                    Integer limitInteger = Integer.valueOf(data.getFirst("limit"));
                                    return Mono.just(limitInteger);
                        })));

        return "userlist";
    }

}
