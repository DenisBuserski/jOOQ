package org.example;

import org.jooq.Record;
import org.jooq.*;
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

            insertData(context);

            selectStatement(context);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void selectStatement(DSLContext context) {
        Result<Record> result = context
                .select()
                .from(table("users"))
                .where(
                        field("age", SQLDataType.INTEGER).greaterThan(35)
                                .and(field("id", SQLDataType.INTEGER).lessThan(8))
                )
                .orderBy(field("age", SQLDataType.INTEGER).desc())
                .fetch();

        for (Record record : result) {
            Integer id = record.get(field("id", SQLDataType.INTEGER));
            String name = record.get(field("name", SQLDataType.VARCHAR));
            System.out.println("ID: " + id + ", Name: " + name);
        }
    }

    private static void insertData(DSLContext context) {
        Table<Record> tableUsers = table("users");

        context.insertInto(table("users")) // Can be replaced with tableUsers
                .columns(
                        field("id", SQLDataType.INTEGER),
                        field("name", SQLDataType.VARCHAR(255)),
                        field("age", SQLDataType.INTEGER))
                .values(1, "User-1", 20)
                .values(2, "User-2", 25)
                .values(3, "User-3", 30)
                .values(4, "User-4", 35)
                .values(5, "User-5", 40)
                .values(6, "User-6", 45)
                .values(7, "User-7", 50)
                .values(8, "User-8", 55)
                .values(9, "User-9", 60)
                .values(10, "User-10", 65)
                .execute();
        System.out.println("Users successfully added!");
    }
}







