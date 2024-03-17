package com.bootload.zoho.crm.controller;

import static com.bootload.zoho.crm.constant.ZohoCRMConstants.GET_OPEN_DEALS_REPORT_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootload.zoho.crm.bean.ZohoReportListInfo;
import com.bootload.zoho.crm.service.ZohoCRMService;

@RestController
public class ZohoCRMController {

	public static final Logger log = LogManager.getLogger(ZohoCRMController.class);

	@Autowired
	ZohoCRMService zohoCRMService;
	
	/*
	 * Get Open Deals Report from Zoho CRM
	 * 
	 * @return ZohoReportListInfo
	 */
	@GetMapping(value = GET_OPEN_DEALS_REPORT_URL, produces = APPLICATION_JSON_VALUE)
	public ZohoReportListInfo getOpenDealsReport() {
		ZohoReportListInfo rprtRsp = new ZohoReportListInfo();
		try {
			rprtRsp = zohoCRMService.getReports();
			log.info("Get Zoho CRM Open Deals Report Call Completed");
		} catch (Exception e) {
			log.error("Exception while fetching reports from Zoho CRM: {}", e.getMessage(), e);
		}
		return rprtRsp;
	}

}
