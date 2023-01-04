package ru.job4j.jdbc;

import java.io.BufferedReader;
import java.io.FileReader;
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

    private void action(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(String tableName, String column1) {
        action(String.format("create table %s (%s text)", tableName, column1));
    }

    public void dropTable(String tableName) {
        action(String.format("drop table %s", tableName));
    }

    public void addColumn(String tableName, String columnName, String type) {
        action(String.format("alter table %s add column %s %s", tableName, columnName, type));
    }

    public void dropColumn(String tableName, String columnName) {
        action(String.format("alter table %s drop column %s", tableName, columnName));
    }

    public void renameColumn(String tableName, String columnName, String newColumnName) {
        action(String.format("alter table %s rename column %s to %s", tableName, columnName, newColumnName));
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

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new BufferedReader(new FileReader("app.properties")));
        TableEditor tableEditor = new TableEditor(properties);
        tableEditor.createTable("example_table_1", "name");
        System.out.println(tableEditor.getScheme("example_table_1"));
        tableEditor.addColumn("example_table_1", "first", "text");
        tableEditor.addColumn("example_table_1", "second", "varchar(256)");
        System.out.println(tableEditor.getScheme("example_table_1"));
        tableEditor.renameColumn("example_table_1", "second", "column_from_delete");
        System.out.println(tableEditor.getScheme("example_table_1"));
        tableEditor.dropColumn("example_table_1", "column_from_delete");
        System.out.println(tableEditor.getScheme("example_table_1"));
        tableEditor.dropTable("example_table_1");
    }


    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
