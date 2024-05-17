package com.jooq;

import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.SQLDataType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.INTEGER;
import static org.jooq.impl.SQLDataType.VARCHAR;

public class JooqExample {
	public static void main(String[] args) throws SQLException {
		Config config = new Config();

		Connection serverConnection = DriverManager.getConnection(config.getServerUrl(), config.getUser(), config.getPassword());
		DSLContext createServer = DSL.using(new DefaultConfiguration().set(serverConnection).set(SQLDialect.MYSQL));
		createServer.createDatabaseIfNotExists("java_jooq").execute();

		Connection javaJooqConnection = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
		DSLContext context = DSL.using(javaJooqConnection, SQLDialect.MYSQL);

		createTable(context);

		insertData(context);

		selectStatement(context);

		selectStatement1(context);

		deleteData(context);
	}

	private static void createTable(DSLContext context) {
		context
				.createTable("users")
				.column("id", INTEGER.nullable(false))
				.column("name", VARCHAR)
				.column("age", INTEGER)
				.primaryKey("id")
				.execute();
		System.out.println("Created table users" + System.lineSeparator());
	}

	private static void insertData(DSLContext context) {
		Table<Record> tableUsers = table("users");

		context.insertInto(table("users")) // Can be replaced with tableUsers
				.columns(
						field("id", INTEGER),
						field("name", SQLDataType.VARCHAR(255)),
						field("age", INTEGER))
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
		System.out.println("Users successfully added!" + System.lineSeparator());
	}

	private static void selectStatement(DSLContext context) {
		Result<Record> result = context
				.select()
				.from(table("users"))
				.where(
						field("age", INTEGER).greaterThan(35)
								.and(field("id", INTEGER).lessThan(8))
				)
				.orderBy(field("age", INTEGER).desc())
				.fetch();

		for (Record record : result) {
			Integer id = record.get(field("id", INTEGER));
			String name = record.get(field("name", VARCHAR));
			System.out.println("ID: " + id + ", Name: " + name);
		}
		System.out.println();
	}

	private static void selectStatement1(DSLContext context) {
		Table<Record> users = table("users");
		Field<Object> age = field("age");

		Result<Record> result = context
				.select()
				.from(users)
				.where(age.lessThan(50))
				.fetch();

        result.stream()
				.map(record -> record.get(field("name", VARCHAR)))
				.map(name -> "Name: " + name)
				.forEach(System.out::println);
		System.out.println();
	}

	private static void deleteData(DSLContext context) {
		int execute = context
				.delete(table("users"))
				.where(field("id", INTEGER).equal(1))
				.execute();
		if (execute == 1) {
			System.out.println("Deleted User with id 1!");
		} else {
			System.out.println("No User was deleted!");
		}
	}

}
