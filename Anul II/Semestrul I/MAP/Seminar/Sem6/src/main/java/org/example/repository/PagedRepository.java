package org.example.repository;

import org.example.model.Entity;
import org.example.repository.dto.Page;
import org.example.repository.dto.Pageable;


public interface PagedRepository<ID, E extends Entity<ID>>  extends Repository<ID,E>{
    Page<E> findAllOnPage(Pageable pageable);
}
