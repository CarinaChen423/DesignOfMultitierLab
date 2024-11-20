package com.example.relationaldataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Customer> rowMapper = (rs, rowNum) -> new Customer(
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("last_name")
    );

    @GetMapping("/customers/{firstName}")
    public List<Customer> getCustomersByFirstName(@PathVariable String firstName) {
        return jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
                new Object[]{firstName},
                rowMapper
        );
    }
    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers",
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")));
    }
}
