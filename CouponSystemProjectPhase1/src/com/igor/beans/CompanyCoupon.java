package com.igor.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CompanyCoupon {
	
	private long compId;
	private long couponId;
	
	
	public CompanyCoupon(long compId, long couponId) {
//		super();
		setCompId(compId);
		setCouponId(couponId);
	}
	
	public CompanyCoupon() {
		
	}

	public long getCompId() {
		return compId;
	}

	public void setCompId(long compId) {
		this.compId = compId;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}
	

	@Override
	public String toString() {
		return "CompanyCoupon [compId=" + compId + ", couponId=" + couponId + "]";
	}
	
	

}
