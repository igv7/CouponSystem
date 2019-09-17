package com.igor.CouponSystemSpringProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igor.CouponSystemSpringProj.model.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
