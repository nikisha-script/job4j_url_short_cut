package ru.job4j.url.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.url.entity.Person;
import ru.job4j.url.service.PersonService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PersonController {

    private final PersonService personService;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/sign-up")
    public Person save(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        return personService.save(person);
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return personService.findAll();
    }

}
