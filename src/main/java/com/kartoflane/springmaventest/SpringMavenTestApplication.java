package com.kartoflane.springmaventest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class SpringMavenTestApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(SpringMavenTestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringMavenTestApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbc;

	@RequestMapping("/")
	public String home() {
		String t = "Hello Docker World! made by kartoFlane<br/><br/>Customer records where first_name = 'Josh':<br/>";

		List<Customer> customers = new ArrayList<>();

		jdbc.query(
				"SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
				new Object[]{"Josh"},
				(rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
		).forEach(customer -> customers.add(customer));

		for (Customer c : customers) {
			t = t + c.toString() + "<br/>";
		}

		return t;
	}

	@Bean
	public DataSource dataSource() {
		DataSourceBuilder builder = DataSourceBuilder.create();
		builder.url("mysql://localhost:2376");
		// This is not smart. Move it elsewhere.
		builder.username("dbuser");
		builder.password("dbuser");
		return builder.build();
	}

	@Override
	public void run(String... strings) throws Exception {
		log.info("Creating tables.");

		jdbc.execute("DROP TABLE customers IF EXISTS");
		jdbc.execute("CREATE TABLE customers(" +
				"id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

		String[] names = {"John Woo", "Jeff Dean", "Josh Bloch", "Josh Long"};
		List<Object[]> splitNames = Arrays.asList(names).stream()
				.map(name -> name.split(" "))
				.collect(Collectors.toList());

		splitNames.forEach(name -> log.info(String.format(
				"Inserting customer record for %s %s",
				name[0], name[1]
		)));

		jdbc.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES(?,?)", splitNames);

		log.info("Querying for customer records where first_name = 'Josh':");
		jdbc.query(
				"SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
				new Object[]{"Josh"},
				(rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
		).forEach(customer -> log.info(customer.toString()));


	}
}
