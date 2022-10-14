package ru.job4j.url.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LinkDto {

    private String url;
    private String code;

    public LinkDto(String url, String code) {
        this.url = url;
        this.code = code;
    }
}
