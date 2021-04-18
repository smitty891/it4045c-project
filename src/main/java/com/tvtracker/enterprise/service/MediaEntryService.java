package com.tvtracker.enterprise.service;

import com.tvtracker.enterprise.dao.IMediaEntryDAO;
import com.tvtracker.enterprise.dto.MediaEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class MediaEntryService implements IMediaEntryService {

    @Autowired
    IMediaEntryDAO mediaEntryDAO;

    /**
     * Adds a new MediaEntry record to the database.
     *
     * @param mediaEntry MediaEntry object
     * @return boolean indicating success or failure
     */
    @Override
    @CacheEvict(value="mediaEntries", key="#mediaEntry.username")
    public boolean createMediaEntry(MediaEntry mediaEntry) throws SQLException, IOException, ClassNotFoundException {
        if(mediaEntry == null) {
            return false;
        }
        return mediaEntryDAO.save(mediaEntry);
    }

    /**
     * Updates an existing MediaEntry database record.
     *
     * @param mediaEntry MediaEntry object
     * @return boolean indicating success or failure
     */
    @Override
    @CacheEvict(value="mediaEntries", key="#mediaEntry.username")
    public boolean updateMediaEntry(MediaEntry mediaEntry) throws SQLException, IOException, ClassNotFoundException {
        if(mediaEntry == null){
            return false;
        }

        mediaEntryDAO.update(mediaEntry);

        return true;
    }

    /**
     * Removes a MediaEntry record from the database.
     *
     * @param mediaEntry MediaEntry object
     * @return boolean indicating success or failure
     */
    @Override
    @CacheEvict(value="mediaEntries", key="#mediaEntry.username")
    public boolean deleteMediaEntry(MediaEntry mediaEntry) throws SQLException, IOException, ClassNotFoundException {
        return mediaEntryDAO.delete(mediaEntry.getId());
    }

    /**
     * Retrieves all MediaEntry objects for a given user
     *
     * @param username String uniquely identifying a user
     * @return List of user's MediaEntry objects
     */
    @Override
    @Cacheable(value="mediaEntries", key="#username")
    public List<MediaEntry> fetchMediaEntriesByUsername(String username) throws SQLException, IOException, ClassNotFoundException {
        return mediaEntryDAO.fetchByUsername(username);
    }
}
