package ru.job4j.url.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.url.model.Link;
import ru.job4j.url.repository.LinkRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public Link save(Link link) {
        return linkRepository.save(link);
    }

    public Optional<Link> findByCode(String code) {
        return linkRepository.findByCode(code);
    }

    public void incrementViews(String code) {
        linkRepository.incrementTotal(code);
    }

}
