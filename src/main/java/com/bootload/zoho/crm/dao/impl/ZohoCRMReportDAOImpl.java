package com.bootload.zoho.crm.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bootload.zoho.crm.dao.ZohoCRMReportDAO;
import com.bootload.zoho.crm.entity.OpenDeals;
import com.bootload.zoho.crm.exception.ExecutionException;
import com.bootload.zoho.crm.repo.ReportRepo;

@Component
public class ZohoCRMReportDAOImpl implements ZohoCRMReportDAO {

	public static final Logger log = LogManager.getLogger(ZohoCRMReportDAOImpl.class);

	@Autowired
	ReportRepo reportRepo;

	@Override
	public void saveOpenDealsReport(List<OpenDeals> rprtRsp) throws Exception {
		try {
			List<OpenDeals> jpaRsp = reportRepo.saveAll(rprtRsp);
			if (jpaRsp == null || jpaRsp.isEmpty()) {
				log.error("Exception while persisting records to MySQL DB");
				throw new ExecutionException();
			}
		} catch (Exception e) {
			log.error("Exception while persisting records to MySQL DB: {}", e.getMessage(), e);
			throw new ExecutionException();
		}
	}

}
