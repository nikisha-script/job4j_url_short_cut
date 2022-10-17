package ru.job4j.url.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SiteDto {

    private String site;

    public SiteDto(String site) {
        this.site = site;
    }
}
