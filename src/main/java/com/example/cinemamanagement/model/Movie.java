package com.example.cinemamanagement.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Movie extends AbstractEqualsAndHashCode{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private Genres category;
    private Integer length;
    private String description;
    private Integer requiredAge;
    @Getter(value = AccessLevel.NONE)
    @ToString.Exclude
    @ManyToMany(mappedBy = "movies", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Screening> screenings;

    public Movie(Long id, String title, Genres category, Integer length, String description, Integer requiredAge) {
        super(id);
        this.title = title;
        this.category = category;
        this.length = length;
        this.description = description;
        this.requiredAge = requiredAge;
    }

    public List<Screening> getScreenings() {
        if (screenings == null) screenings = new ArrayList<>();
        return screenings;
    }

}
