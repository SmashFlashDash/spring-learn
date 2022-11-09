package com.example.MyBookShopApp.data;

import org.springframework.data.repository.CrudRepository;

public interface TestEntityCrudRepository extends CrudRepository<TestEntity, Long> {
    // <TestEntity, Long - тип Id TestEntity> generic
}
