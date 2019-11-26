package com.storage.site.repository;

import com.storage.site.model.Unit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageUnitRepository extends CrudRepository<Unit, Long> {
}