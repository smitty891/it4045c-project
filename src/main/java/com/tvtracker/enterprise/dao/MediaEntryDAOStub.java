package com.tvtracker.enterprise.dao;

import com.tvtracker.enterprise.dto.MediaEntry;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Data Access Object for Media Entries
 * <p>
 *     This class allows access to MediaEntry records in our underlying database.
 * </p>
 */
@Repository
@Profile("test")
public class MediaEntryDAOStub implements  IMediaEntryDAO {
    HashMap<Integer, MediaEntry> entriesByID = new HashMap<>();
    HashMap<String, HashMap<Integer, MediaEntry>> entriesByUsername = new HashMap<>();

    /**
     * Method for creating a new MediaEntry record in the database
     *
     * @param mediaEntry MediaEntry object to be saved as a record in the database
     * @return boolean indicating a successful save
     */
    @Override
    public boolean save(MediaEntry mediaEntry) {
        mediaEntry.setId(entriesByID.size());
        entriesByID.put(mediaEntry.getId(), mediaEntry);

        if(entriesByUsername.containsKey(mediaEntry.getUsername())){
            entriesByUsername.get(mediaEntry.getUsername()).put(mediaEntry.getId(), mediaEntry);
        } else {
            HashMap<Integer, MediaEntry> entries = new HashMap<Integer, MediaEntry>();
            entries.put(mediaEntry.getId(), mediaEntry);
            entriesByUsername.put(mediaEntry.getUsername(), entries);
        }

        return true;
    }

    /**
     * Method for fetching all MediaEntry records for a given user
     *
     * @param username String uniquely identify a UserAccount record
     * @return List of MediaEntry objects belonging to the given user
     */
    @Override
    public List<MediaEntry> fetchByUsername(String username) {
        return new ArrayList(entriesByUsername.get(username).values());
    }

    /**
     * Method for fetching a distinct record in the database
     *
     * @param id integer uniquely identifying a MediaEntry record
     * @return MediaEntry object representation of corresponding database record
     */
    @Override
    public MediaEntry fetchByID(int id) {
        return entriesByID.get(id);
    }

    /**
     * Method for deleting a single MediaEntry record in the database
     *
     * @param id integer uniquely identifying a MediaEntry record
     * @return boolean indicating a successful delete
     */
    @Override
    public boolean delete(int id) {
        MediaEntry entry = entriesByID.remove(id);

        if(entry != null) {
            entriesByUsername.get(entry.getUsername()).remove(id);
            return true;
        }

        return false;
    }

    /**
     * Method for updating a MediaEntry record in the database
     *
     * @param mediaEntry MediaEntry object to be used for updating a database record
     * @return boolean indicating a successful update
     * @return
     */
    @Override
    public boolean update(MediaEntry mediaEntry) {
        MediaEntry oldEntry = entriesByID.remove(mediaEntry.getId());

        if(oldEntry != null) {
            entriesByID.put(mediaEntry.getId(), mediaEntry);
            entriesByUsername.get(oldEntry.getUsername()).put(mediaEntry.getId(), mediaEntry);
            return  true;
        }

        return false;
    }
}
