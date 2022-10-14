package ru.job4j.url.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "links")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "total")
    private Integer total;

    @Column(name = "code")
    private String code;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Link link = (Link) o;
        return id != null && Objects.equals(id, link.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
