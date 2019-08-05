package com.igor.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerCoupon {
	
	private long custId;
	private long couponId;
	
	public CustomerCoupon(long custId, long couponId) {
//		super();
		setCustId(custId);
		setCouponId(couponId);
	}
	
	public CustomerCoupon() {
		
	}

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	@Override
	public String toString() {
		return "CustomerCoupon [custId=" + custId + ", couponId=" + couponId + "]";
	}
	
	
}
