package com.mytaxi.api;

import com.mytaxi.exceptions.ConstraintsViolationException;
import com.mytaxi.exceptions.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Common CRUD implementations
 * @param <T> {@link DataBase}
 * @param <E> {@link AbstractEntity}
 * @param <R> {@link EntityBaseRepository}
 */
public abstract class AbstractDataService<T extends DataBase, E extends AbstractEntity, R extends EntityBaseRepository<E>> implements DataService<T> {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(AbstractDataService.class);

    private final R repository;

    private final Mapper<T, E> mapper;

    public AbstractDataService(final R repository, final Mapper<T, E> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public T find(final long id) throws EntityNotFoundException {
        E e = findEntityChecked(id);
        return mapper.entityToObject(e);
    }

    @Override
    @Transactional
    public T createOrUpdate(T item)  throws ConstraintsViolationException {
        T result;
        try
        {
            result = mapper.entityToObject(repository.save(mapper.objectToEntity(item)));
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a {}: {}", item.getClass(), e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return result;
    }

    @Override
    @Transactional
    public void delete(long id) throws EntityNotFoundException{
        E entity = findEntityChecked(id);
        //soft delete
        entity.setDeleted(true);
    }

    public R getRepository() {
        return repository;
    }

    public Mapper<T, E> getMapper() {
        return mapper;
    }

    protected E findEntityChecked(Long id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + id));
    }
}
