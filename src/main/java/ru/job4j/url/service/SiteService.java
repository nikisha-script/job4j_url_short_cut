package ru.job4j.url.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.url.dto.Statistic;
import ru.job4j.url.jwt.Encypt;
import ru.job4j.url.model.Site;
import ru.job4j.url.repository.SiteRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class SiteService implements UserDetailsService {

    private final SiteRepository siteRepository;
    private final PasswordEncoder encoder;
    private final Encypt encypt;

    public Site saveOrUpdate(Site site) {
        String login = encypt.generateExecuteLogin(site.getSite());
        String password = encypt.generatorExecutePassword(site.getSite().length());
        site.setLogin(login);
        site.setPassword(encoder.encode(password));
        return siteRepository.save(site);
    }

    public Optional<Site> findById(Long id) {
        return siteRepository.findById(id);
    }

    public void delete(Long id) {
        siteRepository.delete(findById(id).orElseThrow(() -> {
            throw new NoSuchElementException("Site is not found");
        }));
    }

    public List<Site> findAllUrl() {
        return siteRepository.findAll();
    }

    public Optional<Site> findBySite(String name) {
        return siteRepository.findBySite(name);
    }

    public Optional<Site> findByLogin(String code) {
        return siteRepository.findByLogin(code);
    }

    public List<Statistic> statistic() {
        return findAllUrl().stream()
                .flatMap(e -> e.getLinks().stream().map(l -> new Statistic(l.getUrl(), l.getTotal())))
                .collect(Collectors.toList());
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Site> site = siteRepository.findByLogin(login);
        if (site.isEmpty()) {
            throw new UsernameNotFoundException(login);
        }
        return new User(site.get().getLogin(), site.get().getPassword(), emptyList());
    }
}
