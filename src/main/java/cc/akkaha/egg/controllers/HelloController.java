package cc.akkaha.egg.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class HelloController {

    @GetMapping("/hello")
    List<String> hello() {
        return Stream.of("hello", "world").collect(Collectors.toList());
    }
}
