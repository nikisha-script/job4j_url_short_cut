package ru.job4j.url.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.url.dto.LinkDto;
import ru.job4j.url.model.Link;
import ru.job4j.url.model.Site;
import ru.job4j.url.repository.LinkRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    @Value("${count.for.statistic:0}")
    private int total;

    public Link save(LinkDto linkDto, Site site) {
        Link rsl = new Link();
        rsl.setUrl(linkDto.getUrl());
        rsl.setTotal(total);
        rsl.setCode(site.getLogin());
        rsl.setSite(site);
        return linkRepository.save(rsl);
    }

    public Optional<Link> findByCode(String code) {
        return linkRepository.findByCode(code);
    }

    public void incrementViews(String code) {
        linkRepository.incrementTotal(code);
    }

}
