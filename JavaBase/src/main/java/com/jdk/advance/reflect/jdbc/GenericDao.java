package com.jdk.advance.reflect.jdbc;

import java.util.List;

public interface GenericDao<T> {

//    void save(T t) throws Exception;

//    void delete(Object id, Class<T> clazz) throws Exception;

//    void update(T t) throws Exception;

//    T get(Object id, Class<T> clazz) throws Exception;

//    List<T> findAllByConditions(Map<String, Object> sqlWhereMap, Class<T> clazz) throws Exception;

    List<T> getALL(Class<T> clazz) throws Exception;

    List<T> get() throws Exception;

}