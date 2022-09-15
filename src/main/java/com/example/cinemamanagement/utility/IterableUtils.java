package com.example.cinemamanagement.utility;

import java.util.ArrayList;
import java.util.List;

public class IterableUtils {

    public static <T> List<T> iterableToList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<>();
        iterable.forEach((n -> resultList.add(n)));
        return resultList;
    }
}
