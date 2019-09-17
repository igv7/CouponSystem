package com.igor.CouponSystemSpringProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igor.CouponSystemSpringProj.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
