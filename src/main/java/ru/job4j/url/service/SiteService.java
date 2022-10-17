package ru.job4j.url.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.url.model.Site;
import ru.job4j.url.repository.SiteRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class SiteService implements UserDetailsService {

    private final SiteRepository siteRepository;

    public Site saveOrUpdate(Site url) {
        return siteRepository.save(url);
    }

    public Optional<Site> findById(Long id) {
        return siteRepository.findById(id);
    }

    public void delete(Long id) {
        siteRepository.delete(findById(id).get());
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


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Site> site = siteRepository.findByLogin(login);
        if (site.isEmpty()) {
            throw new UsernameNotFoundException(login);
        }
        return new User(site.get().getLogin(), site.get().getPassword(), emptyList());
    }
}
