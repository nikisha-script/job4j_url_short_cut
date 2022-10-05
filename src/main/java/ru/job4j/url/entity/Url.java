package ru.job4j.url.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "url")
@Data
@NoArgsConstructor
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String login;
    private String password;
    private Integer count;

    @ManyToOne()
    @JoinColumn(name = "person_id")
    @JsonIgnore
    private Person person;

    public Url(String name, String login, String password, Person person, Integer count) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.person = person;
        this.count = count;
    }
}
