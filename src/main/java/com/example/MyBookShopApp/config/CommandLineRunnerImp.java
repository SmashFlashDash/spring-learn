package com.example.MyBookShopApp.config;

import com.example.MyBookShopApp.data.TestEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Transient;
import java.util.logging.Logger;

@Configuration
public class CommandLineRunnerImp implements CommandLineRunner {

    EntityManagerFactory entityManagerFactory;

    public CommandLineRunnerImp(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i=0; i<5; i++){
            createTestEntity(new TestEntity());
        }
        TestEntity readTestEntity = readTestEntity(3L);
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
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            TestEntity findEntity = readTestEntity(id);
            TestEntity mergedTestEntity = (TestEntity) session.merge(findEntity); // убрать DETACHED
            session.remove(mergedTestEntity);
            tx.commit();
        } catch (HibernateException hex){
            if (tx != null) {
                tx.rollback();
            } else {
                hex.printStackTrace();
            }
        } finally {
            session.close();
        }
    }

    private TestEntity updateTestEntityById(long id) {
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction tx = null;
        TestEntity result = null;

        try {
            tx = session.beginTransaction();
            TestEntity findEntity = readTestEntity(id);
            findEntity.setData("NEW DATA UPDATE");
            //  найдя запсись выполним session.merge т.к. поулченаая сущность
            // в состоянии DETACHED, и мы не можем изменять  findEntity
            result = (TestEntity) session.merge(findEntity);
            tx.commit();
        } catch (HibernateException hex){
            if (tx != null) {
                tx.rollback();
            } else {
                hex.printStackTrace();
            }
        } finally {
            session.close();
        }
        return result;
    }

    private TestEntity readTestEntity(long id) {
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction tx = null;
        TestEntity result = null;

        try {
            tx = session.beginTransaction();
            result = session.find(TestEntity.class, id);
            tx.commit();
        } catch (HibernateException hex){
            if (tx != null) {
                tx.rollback();
            } else {
                hex.printStackTrace();
            }
        } finally {
            session.close();
        }
        return result;
    }

    private void createTestEntity(TestEntity entity) {
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            entity.setData(entity.getClass().getSimpleName() + entity.hashCode());
            session.save(entity);
            tx.commit();
        } catch (HibernateException hex){
            if (tx != null) {
                tx.rollback();
            } else {
                hex.printStackTrace();
            }
        } finally {
            session.close();
        }
    }
}
