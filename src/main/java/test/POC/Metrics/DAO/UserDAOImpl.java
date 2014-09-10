package test.POC.Metrics.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import test.POC.Metrics.Domain.User;

@Repository
public class UserDAOImpl implements UserDAO {
     
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
 
    @Autowired
    private SessionFactory sessionFactory;
 
    @Override
    public void addUser(User p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(p);
//        logger.info("User saved successfully, User Details="+p);
    }
 
    @Override
    public void updateUser(User p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(p);
//        logger.info("User updated successfully, User Details="+p);
    }
 
    @Override
    public List<User> listUsers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> UsersList = session.createQuery("from User").list();
        for(User p : UsersList){
//            logger.info("User List::"+p);
        }
        return UsersList;
    }
 
    @Override
    public User getUserById(int id) {
        Session session = this.sessionFactory.getCurrentSession();      
        User p = (User) session.load(User.class, new Integer(id));
//        logger.info("User loaded successfully, User details="+p);
        return p;
    }
 
    @Override
    public void removeUser(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        User p = (User) session.load(User.class, new Integer(id));
        if(null != p){
            session.delete(p);
        }
//        logger.info("User deleted successfully, User details="+p);
    }
}