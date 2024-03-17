package com.bootload.zoho.crm.service.impl;

import static com.bootload.zoho.crm.constant.ZohoCRMConstants.ACCEPT_HEADER_KEY;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.ACCEPT_HEADER_VALUE;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.FIVE;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.FOUR;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.LOCAL_FILE_WRITE_AND_READ_PATH;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.ONE;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.OPEN_DEASLS_POST_RQST;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.SEVEN;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.SIX;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.THREE;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.TWO;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.ZERO;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.SUCCESS_STATUS_CODE;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.SUCCESS_MSG;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.FAILURE_STATUS_CODE;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.FAILURE_MSG;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bootload.zoho.crm.bean.OpenDealRsp;
import com.bootload.zoho.crm.bean.ZohoReportListInfo;
import com.bootload.zoho.crm.dao.ZohoCRMReportDAO;
import com.bootload.zoho.crm.entity.OpenDeals;
import com.bootload.zoho.crm.exception.ExecutionException;
import com.bootload.zoho.crm.service.ZohoCRMService;

import jakarta.transaction.Transactional;

@Service
public class ZohoCRMServiceImpl implements ZohoCRMService {

	public static final Logger log = LogManager.getLogger(ZohoCRMServiceImpl.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ZohoCRMReportDAO zohoCRMReportDAO;

	@Value("${zoho.crm.report.url}")
	private String crmUrl;

	@Value("${zoho.crm.access.token}")
	private String crmAccessToken;

	/*
	 * Connect to Zoho CRM, Retreive xlsx report and save to a local path, and read
	 * the local saved file, persist to DB and sent the response back to UI
	 * 
	 * @return ZohoReportListInfo
	 */
	@Override
	@Transactional
	public ZohoReportListInfo getReports() throws ExecutionException {
		ZohoReportListInfo zohoRprtLstInfo = new ZohoReportListInfo();
		List<OpenDealRsp> openDealRspLst = null;
		FileInputStream fis = null;
		List<OpenDeals> rprtEntityLst = null;
		try {
			getOpenDealsRprt(crmUrl, crmAccessToken); // Connect ZOHO CRM and get Report
			openDealRspLst = new ArrayList<>();
			fis = new FileInputStream(new File(LOCAL_FILE_WRITE_AND_READ_PATH)); // Read saved Zoho CRM file from local path
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(ZERO);
			rprtEntityLst = new ArrayList<>();
			for (Row row : sheet) {
				int rowNum = row.getRowNum();
				if (!(rowNum == ZERO)) { // Filtering the first row as it has table column names which is required to be persisted
					initializeRspAndEntityBean(row, rprtEntityLst, openDealRspLst);
				}
			}
			zohoCRMReportDAO.saveOpenDealsReport(rprtEntityLst); // Save Open Report to DB
			zohoRprtLstInfo.setOpenDealsLst(openDealRspLst);
			zohoRprtLstInfo.setStatus(SUCCESS_STATUS_CODE);
			zohoRprtLstInfo.setMessage(SUCCESS_MSG);
			fis.close();
			wb.close();
		} catch (IOException e) {
			log.error("Exception while reading crm xslx data from Zoho CRM: {}", e.getMessage(), e);
			zohoRprtLstInfo.setOpenDealsLst(null);
			zohoRprtLstInfo.setStatus(FAILURE_STATUS_CODE);
			zohoRprtLstInfo.setMessage(FAILURE_MSG);
		} catch (Exception e) {
			log.error("Exception while fetching crm data or while reports from Zoho CRM: {}", e.getMessage(), e);
			zohoRprtLstInfo.setOpenDealsLst(null);
			zohoRprtLstInfo.setStatus(FAILURE_STATUS_CODE);
			zohoRprtLstInfo.setMessage(FAILURE_MSG);
		}
		return zohoRprtLstInfo;
	}

	/*
	 * Initialize Report Response Bean and Entity bean
	 * 
	 * @param row  - Excel sheet row
	 * 
	 * @param rprtRsp - Report Response Bean
	 * 
	 * @param openDealRspLst - Entity Bean
	 */
	private void initializeRspAndEntityBean(Row row,  List<OpenDeals> rprtRsp, List<OpenDealRsp> openDealRspLst) {
		
	  // Initialize Entity Bean
		OpenDeals openDeals = new OpenDeals();
		openDeals.setRecordId(row.getCell(ZERO).toString());
		openDeals.setRecordIdAccntNme(row.getCell(ONE).toString());
		openDeals.setDealNme(row.getCell(TWO).toString());
		openDeals.setAccntName(row.getCell(THREE).toString());
		openDeals.setDealOwner(row.getCell(FOUR).toString());
		openDeals.setClosingDte(row.getCell(FIVE).toString());
		openDeals.setStage(row.getCell(SIX).toString());
		openDeals.setAmount(Double.parseDouble(row.getCell(SEVEN).toString()));
		rprtRsp.add(openDeals);
		
	 // Initialize Response Bean
		OpenDealRsp openDealRsp = new OpenDealRsp();
		openDealRsp.setRecordId(row.getCell(ZERO).toString());
		openDealRsp.setRecordIdAccntNme(row.getCell(ONE).toString());
		openDealRsp.setDealNme(row.getCell(TWO).toString());
		openDealRsp.setAccntName(row.getCell(THREE).toString());
		openDealRsp.setDealOwner(row.getCell(FOUR).toString());
		openDealRsp.setClosingDte(row.getCell(FIVE).toString());
		openDealRsp.setStage(row.getCell(SIX).toString());
		openDealRsp.setAmount(Double.parseDouble(row.getCell(SEVEN).toString()));
		openDealRspLst.add(openDealRsp);
		
	}

	/*
	 * Connect Zoho CRM using accesstoken and save xls report file to local
	 * 
	 * @param - url - Zoho CRM API URL
	 * 
	 * @param - accessToken - Zoho accesstoken retrieved thorough Zoho Accounts API
	 * [Access token need to changed every one hour]
	 * 
	 * @return void
	 */
	private void getOpenDealsRprt(String url, String accessToken) throws ExecutionException {
		try {
			HttpHeaders hdrs = new HttpHeaders();
			hdrs.setBearerAuth(accessToken);
			hdrs.setContentType(MediaType.APPLICATION_JSON);
			hdrs.add(ACCEPT_HEADER_KEY, ACCEPT_HEADER_VALUE);
			HttpEntity<String> rqst = new HttpEntity<>(OPEN_DEASLS_POST_RQST, hdrs);
			byte[] bytObj = restTemplate.postForObject(url, rqst, byte[].class); // Spring RESTTemplate call to connect and retrieve data from Zoho CRM
			FileOutputStream outStream = new FileOutputStream(LOCAL_FILE_WRITE_AND_READ_PATH); // Write retrieved file from Zoho to local to read it later
			outStream.write(bytObj);
			outStream.close();
			log.info("Retrieved Open Deals Report and saved to a local path");
		} catch (Exception e) {
			log.error("Exception while reading crm xslx data from Zoho CRM: {}", e.getMessage(), e);
			throw new ExecutionException();
		}
	}
}
