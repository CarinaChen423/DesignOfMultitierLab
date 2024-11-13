package com.example.relationaldataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Spring Data JPA provides basic CRUD methods by default
}
