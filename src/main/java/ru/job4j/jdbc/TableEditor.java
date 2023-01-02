package ru.job4j.jdbc;

import java.sql.*;
import java.util.Properties;

public class TableEditor implements AutoCloseable {

    private Connection connection;

    private Properties properties;

    public TableEditor(Properties properties) throws Exception {
        this.properties = properties;
        initConnection();
    }

    private void initConnection() throws Exception {
        Class.forName(properties.getProperty("hibernate.connection.driver_class"));
        connection = DriverManager.getConnection(
                properties.getProperty("hibernate.connection.url"),
                properties.getProperty("hibernate.connection.username"),
                properties.getProperty("hibernate.connection.password")
        );
    }

    private void Action(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(String tableName) {
        Action(String.format("create table %s", tableName));
    }

    public void dropTable(String tableName) {
        Action(String.format("drop table %s", tableName));
    }

    public void addColumn(String tableName, String columnName, String type) {
        Action(String.format("alter table %s add column %s", tableName, columnName));
    }

    public void dropColumn(String tableName, String columnName) {
        Action(String.format("alter table %s drop column %s", tableName, columnName));
    }

    public void renameColumn(String tableName, String columnName, String newColumnName) {
        Action(String.format("alter table %s rename column %s", tableName, columnName, newColumnName));
    }


    public String getScheme(String tableName) throws Exception {
        StringBuilder scheme = new StringBuilder();
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet columns = metaData.getColumns(null, null, tableName, null)) {
            scheme.append(String.format("%-15s %-15s%n", "column", "type"));
            while (columns.next()) {
                scheme.append(String.format("%-15s %-15s%n",
                        columns.getString("COLUMN_NAME"),
                        columns.getString("TYPE_NAME"))
                );
            }
        }
        return scheme.toString();
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
