package ru.job4j.url.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Site {

    private String site;

    public Site(String site) {
        this.site = site;
    }
}
