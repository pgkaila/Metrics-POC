package test.POC.Metrics.DAO;

import java.util.List;

import test.POC.Metrics.Domain.User;
 
 
public interface UserDAO {
 
    public void addUser(User p);
    public void updateUser(User p);
    public List<User> listUsers();
    public User getUserById(int id);
    public void removeUser(int id);
}