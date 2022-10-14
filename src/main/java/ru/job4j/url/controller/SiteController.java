package ru.job4j.url.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.url.entity.Link;
import ru.job4j.url.entity.Site;
import ru.job4j.url.exception.UrlNoValidException;
import ru.job4j.url.jwt.Encypt;
import ru.job4j.url.model.LinkDto;
import ru.job4j.url.model.SiteDto;
import ru.job4j.url.model.Statistic;
import ru.job4j.url.model.UrlDto;
import ru.job4j.url.service.LinkService;
import ru.job4j.url.service.SiteService;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SiteController {

    private final SiteService siteService;
    private final LinkService linkService;
    private final PasswordEncoder encoder;
    private final Encypt encypt;

    @GetMapping
    public List<Site> findAll() {
        return siteService.findAllUrl();
    }

    @GetMapping("{id}")
    public Optional<Site> findById(@PathVariable Long id) {
        return siteService.findById(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        siteService.delete(id);
    }

    @PostMapping("/registration")
    public ResponseEntity<UrlDto> saveUrl(@Valid @RequestBody SiteDto siteDto) throws MalformedURLException, URISyntaxException {
        var url = new URL(siteDto.getSite()).toURI();
        if (!url.isAbsolute()) {
            throw new UrlNoValidException("bad site");
        }
        if (siteService.findBySite(siteDto.getSite()).isPresent()) {
            return new ResponseEntity<>(
                    new UrlDto(false),
                    HttpStatus.BAD_REQUEST
            );
        }
        String login = encypt.generateExecuteLogin(siteDto.getSite());
        String password = encypt.generatorExecutePassword(siteDto.getSite().length());
        Site rsl = new Site();
        rsl.setSite(siteDto.getSite());
        rsl.setLogin(login);
        rsl.setPassword(encoder.encode(password));
        siteService.saveOrUpdate(rsl);
        return new ResponseEntity<>(
                new UrlDto(login, password, true),
                HttpStatus.OK
        );
    }

    @PostMapping("/add-link-for-site")
    public ResponseEntity<Link> saveLink(@RequestBody LinkDto linkDto) {
        Site site = siteService.findBySite(linkDto.getUrl()).get();
        Link rsl = new Link();
        rsl.setUrl(linkDto.getUrl());
        rsl.setTotal(0);
        rsl.setCode(site.getLogin());
        Link saveLink = linkService.save(rsl);
        site.getLinks().add(saveLink);
        siteService.saveOrUpdate(site);
        return new ResponseEntity<>(
                saveLink,
                HttpStatus.OK
        );
    }

    @GetMapping("/statistic")
    public List<Statistic> statistic() {
        return siteService
                .findAllUrl().stream()
                .flatMap(e -> e.getLinks().stream().map(l -> new Statistic(l.getUrl(), l.getTotal())))
                .collect(Collectors.toList());
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<Void> redirect(@PathVariable(name = "code") String code) {
        Link url = linkService.findByCode(code).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong url code"));
        linkService.incrementViews(code);
        return ResponseEntity.status(HttpStatus.FOUND).header("REDIRECT", url.getUrl()).build();
    }

    @PostMapping("/convert")
    public ResponseEntity<SiteDto> convert(@RequestBody SiteDto site) {
        if (siteService.findByLogin(site.getSite()).isEmpty()) {
            throw new IllegalArgumentException("URL has not system");
        }
        site.setSite(siteService.findByLogin(site.getSite()).get().getSite());
        return new ResponseEntity<>(
                site,
                HttpStatus.OK
        );
    }

}
