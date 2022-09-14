package com.example.cinemamanagement.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Poster extends AbstractEqualsAndHashCode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String filePath;

    @OneToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    @ToString.Exclude
    private Movie movie;

    public Poster(Long id, String filePath) {
        super(id);
        this.filePath = filePath;
    }
}
