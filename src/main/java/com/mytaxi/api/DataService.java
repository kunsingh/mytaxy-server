package com.mytaxi.api;
import com.mytaxi.exceptions.ConstraintsViolationException;
import com.mytaxi.exceptions.EntityNotFoundException;

/**
 * Common CRUD methods
 * @param <T>
 */
public interface DataService<T extends DataBase> {

    T find(long id) throws EntityNotFoundException;

    T createOrUpdate(T item) throws ConstraintsViolationException;

    void delete(long id) throws EntityNotFoundException;
}
