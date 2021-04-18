package com.tvtracker.enterprise.dao;

import com.tvtracker.enterprise.EnterpriseApplication;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Parent DAO class containing all functionality for database communication
 */
public class BaseDAO {

    private String tableName;

    private StringBuffer whereCondition;

    private HashMap<String, Object> columnValues;

    /**
     * Method for getting a connection to the database
     *
     * @return Connection to database
     */
    private static Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Properties properties = new Properties();
            properties.load(EnterpriseApplication.class.getClassLoader().getResourceAsStream("application.properties"));

            return DriverManager.getConnection(properties.getProperty("url"), properties);
    }

    /**
     * Method for Subclasses to set the table name for their corresponding table
     *
     * @param tableName Name of the database table
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * This is a method for setting constraints on sql statements
     *
     * @param column column name
     * @param value String value required in the column
     */
    public void addWhere(String column, String value) {
        if(column == null || value == null) {
            return;
        }

        if(this.whereCondition == null) {
            this.whereCondition = new StringBuffer();
            this.whereCondition.append(" WHERE ");
        } else {
            this.whereCondition.append(" AND ");
        }

        this.whereCondition.append(column).append(" = '").append(value).append("'");
    }

    /**
     * This is a method for setting constraints on sql statements
     *
     * @param column column name
     * @param value integer value required in the column
     */
    public void addWhere(String column, int value) {
        if(column == null) {
            return;
        }

        if(this.whereCondition == null) {
            this.whereCondition = new StringBuffer();
            this.whereCondition.append(" WHERE ");
        } else {
            this.whereCondition.append(" AND ");
        }

        this.whereCondition.append(column).append(" = ").append(value);
    }

    /**
     * This method is used for running select statements against the database
     *
     * @return ArrayList containing key value pairs
     */
    public ArrayList<HashMap<String, Object>> select() throws SQLException, IOException, ClassNotFoundException {
        StringBuffer sql = new StringBuffer();

        sql.append("SELECT * FROM ").append(tableName);
        if (whereCondition != null) {
            sql.append(whereCondition);
            whereCondition = null;
        }

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        statement.execute(sql.toString());

        ArrayList<HashMap<String, Object>> results = getResultList(statement.getResultSet());

        statement.close();
        conn.close();

        return results;
    }


    /**
     * This method is for creating column value pairs for a SQL statement
     *
     * @param column name of column to be updated
     * @param value value to be populated into the given column
     */
    public void setColumnValue(String column, Object value) {
        if(columnValues == null) {
            columnValues = new HashMap<>();
        }

        columnValues.put(column, value);
    }

    /**
     * This method is used for running update statements against the database
     *
     * @return boolean indicating whether update was successful
     */
    public boolean update() throws SQLException, IOException, ClassNotFoundException {
        StringBuffer sql = new StringBuffer();

        sql.append("UPDATE ").append(tableName).append(" SET ");

        int index = 0;
        for(Map.Entry entry: columnValues.entrySet()) {
            index++;
            String column = (String) entry.getKey();
            Object value = entry.getValue();

            sql.append(column).append(" = ");

            if(value instanceof String || value instanceof Timestamp) {
                sql.append("'").append(value.toString()).append("'");
            } else {
                sql.append(value);
            }

            if(index < columnValues.size()) {
                sql.append(", ");
            }
        }

        columnValues = null;

        if(whereCondition != null) {
            sql.append(whereCondition);
            whereCondition = null;
        }

        Connection conn = getConnection();
        Statement statement = conn.createStatement();

        int numOfUpdates = statement.executeUpdate(sql.toString());

        statement.close();
        conn.close();

        return numOfUpdates > 0;
    }

    /**
     * This method is used for running insert statements against the database
     *
     * @return boolean indicating whether insert was successful
     */
    public boolean insert() throws SQLException, IOException, ClassNotFoundException {
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ").append(tableName).append("(");

        int colIndex = 0;
        for(Map.Entry entry: columnValues.entrySet()) {
            colIndex++;
            sql.append(entry.getKey().toString());

            if(colIndex < columnValues.entrySet().size()) {
                sql.append(",");
            }
        }
        sql.append(") VALUES (");
        int valIndex = 0;
        for(Map.Entry entry: columnValues.entrySet()) {
            valIndex++;
            Object value = entry.getValue();

            if(value instanceof String || value instanceof Timestamp) {
                sql.append("'").append(value.toString()).append("'");
            } else {
                sql.append(value);
            }

            if(valIndex < columnValues.entrySet().size()) {
                sql.append(",");
            }
        }
        sql.append(")");

        columnValues = null;

        Connection conn = getConnection();
        Statement statement = conn.createStatement();

        int numOfInserts = statement.executeUpdate(sql.toString());

        statement.close();
        conn.close();

        return numOfInserts > 0;
    }

    /**
     * This method is used for running delete statements against the database
     *
     * @return boolean indicating whether delete was successful
     */
    public boolean delete() throws SQLException, IOException, ClassNotFoundException {
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM ").append(tableName);

        if(whereCondition != null) {
            sql.append(whereCondition);
            whereCondition = null;
        } else {
            return false;
        }

        Connection conn = getConnection();
        Statement statement = conn.createStatement();

        int numOfDeletes = statement.executeUpdate(sql.toString());

        statement.close();
        conn.close();

        return numOfDeletes > 0;
    }


    /**
     * Method for parsing SQL Result sets into an ArrayList
     *
     * @param resultSet returned from the execution of a SQL statement
     * @return ArrayList of key value pairs
     */
    private ArrayList<HashMap<String, Object>> getResultList(ResultSet resultSet) throws SQLException {
        ArrayList<HashMap<String, Object>> results = new ArrayList<>();
        HashMap<String, Object> valuesMap;

            ResultSetMetaData metaData = resultSet.getMetaData();

            while (resultSet.next()) {
                valuesMap = new HashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    int columnType = metaData.getColumnType(i);
                    Object value;
                    if(columnType == 93) {
                        value = resultSet.getTimestamp(i);
                    } else {
                        value = resultSet.getObject(i);
                    }

                    valuesMap.put(metaData.getColumnName(i), value);
                }
                results.add(valuesMap);
            }

            return results;
    }
}
