package com.bootload.zoho.crm.service;

import com.bootload.zoho.crm.bean.ZohoReportListInfo;
import com.bootload.zoho.crm.exception.ExecutionException;

public interface ZohoCRMService {

	public ZohoReportListInfo getReports() throws ExecutionException;
	
	
}
