package com.mytaxi.api;

import java.util.List;

/**
 * Convert DTO to entity & Entity to DTO
 * @param <O>
 * @param <E>
 */
public interface Mapper<O extends DataBase, E extends EntityBase> {

    O entityToObject(E entity);

    E objectToEntity(O object);

    List<E> objectsToEntities(List<O> objects);

    List<O> entitiesToObjects(List<E> entities);
}
