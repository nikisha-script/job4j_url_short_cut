package ru.job4j.url.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UrlDto {

    private String login;
    private Boolean isValid;

    public UrlDto(String login, Boolean isValid) {
        this.login = login;
        this.isValid = isValid;
    }

    public UrlDto(Boolean isValid) {
        this.isValid = isValid;
    }
}
