package ru.job4j.url.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.url.entity.Person;
import ru.job4j.url.entity.Url;
import ru.job4j.url.model.Site;
import ru.job4j.url.model.Statistic;
import ru.job4j.url.service.PersonService;
import ru.job4j.url.service.UrlService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PersonController {

    private final PersonService personService;
    private final UrlService urlService;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/sign-up")
    public Person save(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        return personService.save(person);
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @PostMapping("/convert")
    public ResponseEntity<Site> convert(@RequestBody Site site) {
        if (urlService.findByName(site.getSite()).isEmpty()) {
            throw new IllegalArgumentException("URL has not system");
        }
        site.setSite(urlService.generateExecuteLogin(site.getSite()));
        return new ResponseEntity<>(
                site,
                HttpStatus.OK
        );
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<Void> redirect(@PathVariable(name = "code") String code) {
        Url url = urlService.findByLogin(code).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong url code"));
        urlService.incrementViews(code);
        return ResponseEntity.status(HttpStatus.FOUND).header("REDIRECT", url.getName()).build();
    }

    @GetMapping("/statistic")
    public List<Statistic> statistic() {
        return urlService
                .findAllUrl().stream()
                .map(e -> new Statistic(e.getName(), e.getCount()))
                .collect(Collectors.toList());
    }
}
