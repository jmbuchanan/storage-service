package com.storage.site.repository;

import com.storage.site.model.StorageUnit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageUnitRepository extends CrudRepository<StorageUnit, Long> {
}