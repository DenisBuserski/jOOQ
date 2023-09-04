package org.example;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class Main {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/java_jooq";
        String user = "root";
        String password = "denis";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.MYSQL);

            // Define your SQL query using JOOQ's DSL
            // Example: Select all records from a "users" table
            Result<Record> result = context.select()
                    .from(table("user"))
                    .fetch();

            // Process the results
            for (Record record : result) {
                Integer id = record.get(field("id", SQLDataType.INTEGER));
                String name = record.get(field("name", SQLDataType.VARCHAR));
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}







