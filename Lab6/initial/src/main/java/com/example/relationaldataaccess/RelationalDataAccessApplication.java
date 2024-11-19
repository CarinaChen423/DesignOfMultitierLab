package com.example.relationaldataaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class RelationalDataAccessApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(RelationalDataAccessApplication.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	public static void main(String args[]) {
		SpringApplication.run(RelationalDataAccessApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		log.info("Creating tables");
		jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE customers(id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

		List<Object[]> splitUpNames = List.of("Zhe Chen", "Alice Barber", "Roy Watson", "Moss Moris", "Zhe Wang")
				.stream()
				.map(name -> name.split(" "))
				.map(name -> new Object[]{name[0], name[1]}) // Convert String[] to Object[]
				.collect(Collectors.toList());

		jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);

		log.info("Querying for customer records where first_name = 'Zhe':");
		List<Customer> customersNamedZhe = jdbcTemplate.query(
				"SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
				new Object[]{"Zhe"},
				(rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
		);

		customersNamedZhe.forEach(customer -> log.info(customer.toString()));
	}

	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		return jdbcTemplate.query(
				"SELECT id, first_name, last_name FROM customers",
				(rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")));
	}
}
