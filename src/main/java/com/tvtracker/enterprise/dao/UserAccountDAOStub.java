package com.tvtracker.enterprise.dao;

import com.tvtracker.enterprise.dto.UserAccount;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * Data Access Object for UserAccounts
 * <p>
 *     This class allows access to UserAccount records in our underlying database.
 * </p>
 */
@Repository
@Profile("test")
public class UserAccountDAOStub implements IUserAccountDAO {
    HashMap<String, UserAccount> userAccounts = new HashMap<>();

    /**
     * Method for creating a new UserAccount record in the database
     *
     * @param userAccount UserAccount object to be saved as a record in the database
     * @return boolean indicating a successful save
     * @throws Exception when database fails to save UserAccount
     */
    @Override
    public boolean save(UserAccount userAccount) {
        if(userAccounts.containsKey(userAccount.getUsername())) {
            return false;
        }

        userAccounts.put(userAccount.getUsername(), userAccount);

        return true;
    }

    /**
     * Method for fetching a distinct UserAccount record from the database
     *
     * @param username String uniquely identifying a UserAccount record
     * @return UserAccount representation of the corresponding UserAccount database record
     */
    @Override
    public UserAccount fetch(String username) {
        return userAccounts.get(username);
    }

    /**
     * Method for checking whether a record exists for the given username
     *
     * @param username String uniquely identifying a UserAccount record
     * @return boolean indicating whether a record exists for this username
     */
    @Override
    public boolean existsBy(String username) {
        return userAccounts.containsKey(username);
    }

    /**
     * Method for deleting a distinct UserAccount record from the database
     *
     * @param username String uniquely identifying a UserAccount record
     * @return boolean indicating a successful delete
     */
    @Override
    public boolean delete(String username) {
        if(existsBy(username)){
            userAccounts.remove(username);
            return true;
        }
        return false;
    }

    /**
     * Method for updating an existing UserAccount record in the database
     *
     * @param userAccount UserAccount object to be used for updating a database record
     * @return boolean indicating a successful update
     */
    @Override
    public boolean update(UserAccount userAccount) {
        if(userAccounts.containsKey(userAccount.getUsername())){
            userAccounts.put(userAccount.getUsername(), userAccount);
            return true;
        }
        return false;
    }
}
