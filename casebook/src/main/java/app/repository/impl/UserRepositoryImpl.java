package app.repository.impl;

import app.domain.entities.User;
import app.repository.UserRepository;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    @Inject
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(User user) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(user);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void update(User user) {
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(user);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public User findById(String id) {
        this.entityManager.getTransaction().begin();
        User user = this.entityManager.createQuery("SELECT u FROM User AS u where u.id=:id", User.class)
                .setParameter("id", id)
                .getSingleResult();
        this.entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {

        User user = null;

        this.entityManager.getTransaction().begin();
        try {
            user = this.entityManager.createQuery("SELECT u FROM User AS u where u.username=:username AND u.password = :password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
        }
        this.entityManager.getTransaction().commit();

        return user;

    }

    @Override
    public List<User> findAll() {
        this.entityManager.getTransaction().begin();

        List<User> users = this.entityManager.createQuery("SELECT  u FROM User as u", User.class)
                .getResultList();

        this.entityManager.getTransaction().commit();

        return users;

    }
}
