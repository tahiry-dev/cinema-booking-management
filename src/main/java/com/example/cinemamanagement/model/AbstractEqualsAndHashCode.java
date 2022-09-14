package com.example.cinemamanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public abstract class AbstractEqualsAndHashCode {
    private Long id;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof AbstractEqualsAndHashCode )) return false;

        AbstractEqualsAndHashCode other = (AbstractEqualsAndHashCode) obj;
        return id != null && id.equals(other.getId());

    }
    @Override
    public int hashCode(){ return 31;}
}
