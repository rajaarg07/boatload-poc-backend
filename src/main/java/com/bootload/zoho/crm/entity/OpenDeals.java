package com.bootload.zoho.crm.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="OPEN_DEALS")
public class OpenDeals implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Record_Id")
	private String recordId;
	
	@Column(name= "Record_Id_Account_Name")
	private String recordIdAccntNme;
	
	@Column(name= "Deal_Name")
	private String dealNme;
	
	@Column(name= "Account_Name")
	private String accntName;
	
	@Column(name= "Deal_Owner")
	private String dealOwner;
	
	@Column(name= "Closing_Date")
	private String closingDte;
	
	@Column(name= "Stage")
	private String stage;
	
	@Column(name= "Amount")
	private Double amount;
	
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getRecordIdAccntNme() {
		return recordIdAccntNme;
	}

	public void setRecordIdAccntNme(String recordIdAccntNme) {
		this.recordIdAccntNme = recordIdAccntNme;
	}

	public String getDealNme() {
		return dealNme;
	}

	public void setDealNme(String dealNme) {
		this.dealNme = dealNme;
	}

	public String getAccntName() {
		return accntName;
	}

	public void setAccntName(String accntName) {
		this.accntName = accntName;
	}

	public String getDealOwner() {
		return dealOwner;
	}

	public void setDealOwner(String dealOwner) {
		this.dealOwner = dealOwner;
	}

	public String getClosingDte() {
		return closingDte;
	}

	public void setClosingDte(String closingDte) {
		this.closingDte = closingDte;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
