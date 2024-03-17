package com.bootload.zoho.crm.dao;

import java.util.List;

import com.bootload.zoho.crm.entity.OpenDeals;

public interface ZohoCRMReportDAO {

	public void saveOpenDealsReport(List<OpenDeals> rprtRsp) throws Exception;

}
