package ru.job4j.url.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.url.entity.Url;
import ru.job4j.url.repository.UrlRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public Url saveOrUpdate(Url url) {
        return urlRepository.save(url);
    }

    public Optional<Url> findById(Long id) {
        return urlRepository.findById(id);
    }

    public void delete(Long id) {
        urlRepository.delete(findById(id).get());
    }

    public List<Url> findAllUrl() {
        return urlRepository.findAll();
    }

    public Optional<Url> findByName(String name) {
        return urlRepository.findByName(name);
    }

    public Optional<Url> findByLogin(String code) {
        return urlRepository.findByLogin(code);
    }

    public void incrementViews(String code) {
        urlRepository.incrementViews(code);
    }

}
