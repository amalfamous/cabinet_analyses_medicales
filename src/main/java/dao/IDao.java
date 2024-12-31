package dao;

import java.util.List;

public interface IDao <T> {
    void create(T entity);
    void update(Long id, T entity);
    void delete(Long id);
    T findById(Long id);
    List<T> findAll();
}