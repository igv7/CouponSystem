package com.igor.CouponSystemSpringProj.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.igor.CouponSystemSpringProj.enums.IncomeType;

//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;


@Entity
@Table
//@Data
//@NoArgsConstructor
//@RequiredArgsConstructor
public class Income {
	
	private long incomeId;
	private String clientName;
	private long clientId;
	private Date operationDate;
	private IncomeType description;
	private double amount;

	public Income() {
		
	}

	public Income(long incomeId, String clientName, long clientId, Date operationDate,
			IncomeType description, double amount) {
		this.incomeId = incomeId;
		this.clientName = clientName;
		this.clientId = clientId;
		this.operationDate = operationDate;
		this.description = description;
		this.amount = amount;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "incomeId", updatable = false, nullable = false)
	public long getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(long incomeId) {
		this.incomeId = incomeId;
	}

	@Column(name = "clientName", updatable = true, nullable = false)
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Column(name = "clientId", updatable = true, nullable = false)
	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	@Column(name = "operationDate", updatable = true, nullable = false)
	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "description", updatable = true, nullable = false)
	public IncomeType getDescription() {
		return description;
	}

	public void setDescription(IncomeType description) {
		this.description = description;
	}

	@Column(name = "amount", updatable = true, nullable = false)
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Income [incomeId=" + incomeId + ", clientName=" + clientName + ", clientId=" + clientId
				+ ", operationDate=" + operationDate + ", description=" + description + ", amount=" + amount + "]";
	}
	

}
