package test.POC.Metrics.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import test.POC.Metrics.Domain.User;
import test.POC.Metrics.DAO.UserDAO;

@Service
public class UserServiceImpl implements UserService {
	
	private UserDAO userDAO;
	 
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
 
    @Override
    @Transactional
    public void addUser(User p) {
        this.userDAO.addUser(p);
    }
 
    @Override
    @Transactional
    public void updateUser(User p) {
        this.userDAO.updateUser(p);
    }
 
    @Override
    @Transactional
    public List<User> listUsers() {
        return this.userDAO.listUsers();
    }
 
    @Override
    @Transactional
    public User getUserById(int id) {
        return this.userDAO.getUserById(id);
    }
 
    @Override
    @Transactional
    public void removeUser(int id) {
        this.userDAO.removeUser(id);
    }

}
