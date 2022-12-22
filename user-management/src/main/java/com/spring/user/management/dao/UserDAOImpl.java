package com.spring.user.management.dao;

import com.spring.user.management.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO{

    @Autowired
    DataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);

    private void closeConnection(Connection connection , PreparedStatement statement , ResultSet resultSet){

        if(resultSet != null){
            try {
                resultSet.close();
            }
            catch (SQLException e) {
                LOGGER.error("ResultSet Closing Error" , e);
            }
        }

        if(connection != null){
            try {
                connection.close();
            }
            catch (SQLException e) {
                LOGGER.error("Connection Closing Error", e);
            }
        }

        if(statement != null){
            try {
                statement.close();
            }
            catch (SQLException e) {
                LOGGER.error("Prepared Statement Closing Error", e);
            }
        }
    }

    @Override
    public List<User> getAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet;

        ArrayList<User> empty = new ArrayList<>();

        String sql = "select * from user_info";

        try {
            connection = dataSource.getConnection();
            statement  = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            ArrayList<User> users = new ArrayList<>();

            while (resultSet.next()){

                User user = new User();

                user.setUserId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setMobileNumber(resultSet.getLong("mobile_number"));
                user.setEmailId(resultSet.getString("email_id"));
                user.setAddress(resultSet.getString("address"));

                users.add(user);
            }
            return users;
        }
        catch (SQLException e) {
            LOGGER.error("Error on Fetching all Details" , e);
        }
        finally {
            closeConnection(connection , statement , null);
        }
        return empty;
    }

    @Override
    public User getOne(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String sql = "select * from user_info where id=?";

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1,userId);

            resultSet = statement.executeQuery();

            if (resultSet.next()){

                User user = new User();

                user.setUserId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setMobileNumber(resultSet.getLong("mobile_number"));
                user.setEmailId(resultSet.getString("email_id"));
                user.setAddress(resultSet.getString("address"));

                return user;
            }
        }
        catch (SQLException e) {
            LOGGER.error("Error on fetching single user" , e);
        }
        finally {
            closeConnection(connection , statement , resultSet);
        }
        return null;
    }

    @Override
    public int addUser(User user) {
        int count = 0;
        Connection connection = null;
        PreparedStatement statement = null;

        String sql = "insert into user_info ( first_name , last_name , mobile_number, email_id , address ) values (?,?,?,?,?)";

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1,user.getFirstName());
            statement.setString(2,user.getLastName());
            statement.setLong(3,user.getMobileNumber());
            statement.setString(4, user.getEmailId());
            statement.setString(5,user.getAddress());

            count = statement.executeUpdate();
            return count;
        }
        catch (SQLException e) {
            LOGGER.error("Error on Adding an User", e);
        }
        finally {
            closeConnection(connection , statement , null);
        }
        return count;
    }

    @Override
    public int modifyUser(User user, int userId) {
        int count = 0;
        Connection connection = null;
        PreparedStatement statement= null;
        String sql = "update user_info set first_name=? , last_name=? , mobile_number=? , email_id=? , address=? where id=?";

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1,user.getFirstName());
            statement.setString(2,user.getLastName());
            statement.setLong(3,user.getMobileNumber());
            statement.setString(4,user.getEmailId());
            statement.setString(5,user.getAddress());

            statement.setInt(6,userId);

            count = statement.executeUpdate();
            return count;
        }
        catch (SQLException e) {
            LOGGER.error("Error on Updating Details of an User" , e);
        }
        finally {
            closeConnection(connection , statement ,null);
        }
        return count;
    }

    @Override
    public int deleteUser(int userId) {
        int count = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from user_info where id=?";

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1,userId);

            count = statement.executeUpdate();
            return count;
        }
        catch (SQLException e) {
            LOGGER.error("Error on Deleting a User" , e);
        }
        finally {
            closeConnection(connection , statement , null);
        }
        return count;
    }
}
