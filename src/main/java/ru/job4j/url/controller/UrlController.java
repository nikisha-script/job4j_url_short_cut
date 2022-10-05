package ru.job4j.url.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.url.entity.Person;
import ru.job4j.url.entity.Url;
import ru.job4j.url.exception.UrlNoValidException;
import ru.job4j.url.model.Site;
import ru.job4j.url.model.UrlDto;
import ru.job4j.url.service.PersonService;
import ru.job4j.url.service.UrlService;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UrlController {

    private final UrlService urlService;
    private final PersonService personService;

    @GetMapping
    public List<Url> findAll() {
        return urlService.findAllUrl();
    }

    @GetMapping("{id}")
    public Optional<Url> findById(@PathVariable Long id) {
        return urlService.findById(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        urlService.delete(id);
    }

    @PostMapping("/registration")
    public ResponseEntity<UrlDto> saveUrl(@Valid @RequestBody Site site) throws MalformedURLException, URISyntaxException {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        Optional<Person> person = personService.findByUsername(principal.getName());
        var url = new URL(site.getSite()).toURI();
        if (!url.isAbsolute()) {
            throw new UrlNoValidException("bad site");
        }
        if (urlService.findByName(site.getSite()).isPresent()) {
            return new ResponseEntity<>(
                    new UrlDto(false),
                    HttpStatus.BAD_REQUEST
            );
        }
        String login = urlService.generateExecuteLogin(site.getSite());
        String password = urlService.generatorExecutePassword(site.getSite().length());
        urlService.saveOrUpdate(
                new Url(site.getSite(), login, password, person.get(), 0)
        );
        return new ResponseEntity<>(
                new UrlDto(login, password, true),
                HttpStatus.OK
        );
    }

}
