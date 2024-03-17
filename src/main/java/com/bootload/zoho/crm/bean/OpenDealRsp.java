package com.bootload.zoho.crm.bean;

import java.io.Serializable;
import java.util.Objects;

public class OpenDealRsp implements Serializable {

	private static final long serialVersionUID = 1L;

	private String recordId;

	private String recordIdAccntNme;

	private String dealNme;

	private String accntName;

	private String dealOwner;

	private String closingDte;

	private String stage;

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

	@Override
	public int hashCode() {
		return Objects.hash(accntName, amount, closingDte, dealNme, dealOwner, recordId, recordIdAccntNme, stage);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpenDealRsp other = (OpenDealRsp) obj;
		return Objects.equals(accntName, other.accntName) && Objects.equals(amount, other.amount)
				&& Objects.equals(closingDte, other.closingDte) && Objects.equals(dealNme, other.dealNme)
				&& Objects.equals(dealOwner, other.dealOwner) && Objects.equals(recordId, other.recordId)
				&& Objects.equals(recordIdAccntNme, other.recordIdAccntNme) && Objects.equals(stage, other.stage);
	}

}
