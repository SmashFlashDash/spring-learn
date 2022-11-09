package com.example.MyBookShopApp.config;

import com.example.MyBookShopApp.data.TestEntity;
import com.example.MyBookShopApp.data.TestEntityCrudRepository;
import com.example.MyBookShopApp.data.TestEntityDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Transient;
import java.util.logging.Logger;

@Configuration
public class CommandLineRunnerImp implements CommandLineRunner {
    TestEntityCrudRepository testEntityCrudRepository;
    @Autowired
    public CommandLineRunnerImp(TestEntityCrudRepository testEntityCrudRepository) {
        this.testEntityCrudRepository = testEntityCrudRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        for (int i=0; i<5; i++){
            createTestEntity(new TestEntity());
        }
        TestEntity readTestEntity = readTestEntityById(3L);
        if (readTestEntity != null) {
            Logger.getLogger(CommandLineRunnerImp.class.getSimpleName()).info("read_" + readTestEntity.toString());
        } else {
            throw new NullPointerException();
        }
        TestEntity updateTestEntity = updateTestEntityById(5L);
        if (updateTestEntity != null) {
            Logger.getLogger(CommandLineRunnerImp.class.getSimpleName()).info("update_" + readTestEntity.toString());
        } else {
            throw new NullPointerException();
        }

        deleteTestEntityById(4L);
    }

    private void deleteTestEntityById(Long id) {
        TestEntity testEntity = testEntityCrudRepository.findById(id).get();
        testEntityCrudRepository.delete(testEntity);
    }
    private TestEntity updateTestEntityById(long id) {
        TestEntity testEntity = testEntityCrudRepository.findById(id).get();
        // получаем testEntity
        testEntity.setData("NEW DATA"); // преобразуем как хотим
        testEntityCrudRepository.save(testEntity);
        return testEntity;
    }
    private TestEntity readTestEntityById(long id) {
        return testEntityCrudRepository.findById(id).get();
        // findById возаращает тип Optional поэтмоу нужно использовать get
    }
    private void createTestEntity(TestEntity entity) {
        entity.setData(entity.getClass().getSimpleName()+entity.hashCode());
        // заполнить поле данными String
        testEntityCrudRepository.save(entity);
    }
}
