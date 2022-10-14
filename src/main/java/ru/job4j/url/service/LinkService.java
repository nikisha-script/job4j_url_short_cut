package ru.job4j.url.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.url.entity.Link;
import ru.job4j.url.repository.LinkRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public Link save(Link link) {
        return linkRepository.save(link);
    }

    public Optional<Link> findByUrl(String url) {
        return linkRepository.findByUrl(url);
    }

    public Optional<Link> findByCode(String url) {
        return linkRepository.findByCode(url);
    }

    public void incrementViews(String code) {
        linkRepository.incrementViews(code);
    }

}
