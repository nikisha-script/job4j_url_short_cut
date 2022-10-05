package ru.job4j.url.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.url.entity.Url;
import ru.job4j.url.repository.UrlRepository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final ThreadLocalRandom randomNumber = ThreadLocalRandom.current();

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

    public String generateExecuteLogin(String pass) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(pass.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger bigInteger = new BigInteger(1, digest);
        StringBuilder m5dHex = new StringBuilder(bigInteger.toString(16));
        while (m5dHex.length() < 32) {
            m5dHex.insert(0, "0");
        }
        return m5dHex.toString();
    }

    public String generatorExecutePassword(int wordLength) {
        StringBuilder word = new StringBuilder();
        int exitLoop = 0;
        while (exitLoop < wordLength) {
            int number = randomNumber.nextInt(40, 130);
            if ((number <= 60 || number >= 70) && (number <= 90 || number >= 100)) {
                word.appendCodePoint(number);
                exitLoop++;
            }
        }
        return word.toString();
    }

}
