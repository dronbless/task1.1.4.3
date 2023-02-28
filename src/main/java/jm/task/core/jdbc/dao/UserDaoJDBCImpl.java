package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String comm = "CREATE TABLE IF NOT EXISTS User(id BIGINT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name varchar(255), " +
                "lastName varchar(255), age TINYINT(255))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(comm);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String comm = "DROP TABLE IF EXISTS User";
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(comm);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String comm = "INSERT INTO User(name, lastName, age) VALUES (?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(comm)){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
        } catch (SQLException e ) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String comm = "DELETE FROM User WHERE ID=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(comm)){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> userlist = new ArrayList<>();
        String comm = "SELECT * FROM User";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(comm);
            while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("ID"));
            user.setName(resultSet.getString("NAME"));
            user.setLastName(resultSet.getString("LASTNAME"));
            user.setAge(resultSet.getByte("AGE"));
            userlist.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userlist;
    }

    public void cleanUsersTable() {
        String comm = "DELETE FROM User";
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(comm);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
