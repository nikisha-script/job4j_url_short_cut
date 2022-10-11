package ru.job4j.url.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import ru.job4j.url.entity.Url;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByName(String name);

    Optional<Url> findByLogin(String login);

    @Modifying
    @Transactional
    @Query("UPDATE Url u set u.count = u.count + 1 where u.login = ?1")
    void incrementViews(String code);

}
