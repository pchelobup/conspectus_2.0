package ru.alina.util;

import ru.alina.util.exception.NotFoundException;

public class ValidationUtil {
    public static <T> void  notNull(T obj, String msg) {
        if (obj == null) {
            throw new NullPointerException(msg);
        }
    }

    public static <T> void  notFound(T obj, int id, String name) {
        if (obj == null) {
            throw new NotFoundException("Not found entity " + name +" with id="+id);
        }
    }

    public static <T> T  notFoundAndReturn(T obj, int id, String name) {
        if (obj == null) {
            throw new NotFoundException("Not found entity " + name +" with id="+id);
        }
        else {
            return obj;
        }
    }

    public static void notFound(Boolean found, int id, String name) {
        if (!found) {
            throw new NotFoundException("Not found entity " + name +" with id="+id);
        }
    }
}
