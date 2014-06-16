package net.therap.service;

import net.therap.InvalidUserException;
import net.therap.dao.UserDao;
import net.therap.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * Created by imran.azad on 6/16/14.
 */
@Service
@Qualifier("txTemplateUserService")
public class TxTemplateUserService implements UserService {
    @Autowired
    @Qualifier("jpaUserDao")
    private UserDao userDao;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public User verifyUserAndGetLoginDetails(User user) throws InvalidUserException {
        String userName = user.getUserName();
        User verifiedUser = userDao.getUserByUserName(userName);
        boolean userMatchesVerifiedUser = userMatchesVerifiedUser(user, verifiedUser);
        if (userMatchesVerifiedUser) {
            return verifiedUser;
        } else {
            throw new InvalidUserException("invalid handle, password combination");
        }
    }

    private boolean userMatchesVerifiedUser(User user, User verifiedUser) {
        boolean matches = false;
        if (verifiedUser != null) {
            String password = user.getPassword();
            if (password.equals(verifiedUser.getPassword())) {
                matches = true;
            }
        }
        return matches;
    }

    public User getUserInfoOf(User user) {
        User userWithDetailInfo = userDao.getInfoOfUser(user);
        user.setName(userWithDetailInfo.getName());
        user.setAdmin(userWithDetailInfo.isAdmin());
        return user;
    }

    public void addNewUser(final User user) {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus transactionStatus) {
                try {
                    userDao.insertUser(user);
                } catch (RuntimeException e) {
                    transactionStatus.setRollbackOnly();
                    throw e;
                }

                return null;
            }
        });
    }

    public List<User> getListOfAdmins() {
        return userDao.getAdmins();
    }
}
