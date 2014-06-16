package net.therap.dao;

import net.therap.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by imran.azad on 6/11/14.
 */
@Repository
@Qualifier("jpaUserDao")
public class JpaUserDao implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUserByUserName(String userName) {
        User user = entityManager.find(User.class, 1);
        return user;
    }

    @Override
    public User getInfoOfUser(User user) {
        return null;
    }

    @Override
    public void insertUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }

    @Override
    public List<User> getAdmins() {
        Query query = entityManager.createQuery("FROM User AS T WHERE T.isAdmin=:admin");
        query.setParameter("admin", true);
        return query.getResultList();
    }
}
