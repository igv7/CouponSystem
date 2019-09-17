package com.igor.CouponSystemSpringProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igor.CouponSystemSpringProj.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
