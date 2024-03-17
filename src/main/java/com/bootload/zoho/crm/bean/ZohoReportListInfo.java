package com.bootload.zoho.crm.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ZohoReportListInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<OpenDealRsp> openDealsLst = new ArrayList<>();
	
	private String status;
	
	private String message;

	public List<OpenDealRsp> getOpenDealsLst() {
		return openDealsLst;
	}

	public void setOpenDealsLst(List<OpenDealRsp> openDealsLst) {
		this.openDealsLst = openDealsLst;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
