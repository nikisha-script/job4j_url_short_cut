package ru.job4j.url.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.job4j.url.model.Link;

import javax.transaction.Transactional;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByUrl(String url);

    Optional<Link> findByCode(String url);

    @Modifying
    @Transactional
    @Query("UPDATE Link l set l.total = l.total + 1 where l.code = ?1")
    void incrementTotal(String code);

}
