package com.tvtracker.enterprise.dao;

import com.tvtracker.enterprise.dto.MediaEntry;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object for Media Entries
 * <p>
 *     This class allows access to MediaEntry records in our underlying database.
 * </p>
 */
public interface IMediaEntryDAO {
        /**
         * Method for creating a new MediaEntry record in the database
         *
         * @param mediaEntry MediaEntry object to be saved as a record in the database
         * @return MediaEntry object representation of new database record
         */
        boolean save(MediaEntry mediaEntry) throws SQLException, IOException, ClassNotFoundException;


        /**
         * Method for fetching all MediaEntry records for a given user
         *
         * @param username String uniquely identify a UserAccount record
         * @return List of MediaEntry objects belonging to the given user
         */
        List<MediaEntry>  fetchByUsername(String username) throws SQLException, IOException, ClassNotFoundException;


        /**
         * Method for fetching a distinct record in the database
         *
         * @param id integer uniquely identifying a MediaEntry record
         * @return MediaEntry object representation of corresponding database record
         */
        MediaEntry fetchByID(int id) throws SQLException, IOException, ClassNotFoundException;

        /**
         * Method for deleting a single MediaEntry record in the database
         *
         * @param id integer uniquely identifying a MediaEntry record
         * @return boolean indicating MediaEntry was successfully deleted
         */
        boolean delete(int id) throws SQLException, IOException, ClassNotFoundException;


        /**
         * Method for updating a MediaEntry record in the database
         *
         * @param mediaEntry MediaEntry object to be used for updating a database record
         * @return boolean indicating MediaEntry was successfully updated
         */
        boolean update(MediaEntry mediaEntry) throws SQLException, IOException, ClassNotFoundException;
}
