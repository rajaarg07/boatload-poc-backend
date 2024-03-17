package com.bootload.zoho.crm.controller.junit;

import static com.bootload.zoho.crm.constant.ZohoCRMConstants.SUCCESS_STATUS_CODE;
import static com.bootload.zoho.crm.constant.ZohoCRMConstants.FAILURE_STATUS_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.bootload.zoho.crm.bean.OpenDealRsp;
import com.bootload.zoho.crm.bean.ZohoReportListInfo;
import com.bootload.zoho.crm.controller.ZohoCRMController;
import com.bootload.zoho.crm.service.ZohoCRMService;
import com.google.gson.Gson;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ZohoCRMController.class)
@EnableWebMvc
public class ZohoCRMControllerTests {
	
	@Autowired
	private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ZohoCRMService zohoCRMService;  
    
    @BeforeEach
    void setUp() {
    	
    	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void testGetControllerPositive() throws Exception {  	
    	ZohoReportListInfo zohoRprtLstInfoLst  = new ZohoReportListInfo();
    	List<OpenDealRsp> openDealLst = new ArrayList<>();
    	OpenDealRsp openDealRsp = new OpenDealRsp();
    	openDealRsp.setRecordId("zcrm_660420000000310350");
    	openDealRsp.setAccntName("King (Sample)");
    	openDealRsp.setDealNme("King");
	    openDealLst.add(openDealRsp);
	    zohoRprtLstInfoLst.setOpenDealsLst(openDealLst);
	    zohoRprtLstInfoLst.setStatus(SUCCESS_STATUS_CODE);
	    
	   when(zohoCRMService.getReports()).thenReturn(zohoRprtLstInfoLst);
	    
	   MockHttpServletRequestBuilder getReq = get("/crm/getOpenDealReport").contentType(MediaType.APPLICATION_JSON_VALUE);
    	
       ResultActions result = mockMvc.perform(getReq);
       
       result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
       
       MockHttpServletResponse mockResponse = result.andReturn().getResponse();
       
       ZohoReportListInfo openDealLstt = new Gson().fromJson(mockResponse.getContentAsString(), ZohoReportListInfo.class);
      
      assertNotNull(openDealLstt.getOpenDealsLst());
      assertEquals(openDealLstt.getStatus(),SUCCESS_STATUS_CODE);
    }
    
    @Test
    public void testGetControllerNegative() throws Exception {  	
    	ZohoReportListInfo zohoRprtLstInfoLst  = new ZohoReportListInfo();
	    zohoRprtLstInfoLst.setOpenDealsLst(null);
	    zohoRprtLstInfoLst.setStatus(FAILURE_STATUS_CODE);
	    
	   when(zohoCRMService.getReports()).thenReturn(zohoRprtLstInfoLst);
	    
	   MockHttpServletRequestBuilder getReq = get("/crm/getOpenDealReport").contentType(MediaType.APPLICATION_JSON_VALUE);
    	
       ResultActions result = mockMvc.perform(getReq);
       
       result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
       
       MockHttpServletResponse mockResponse = result.andReturn().getResponse();
       
       ZohoReportListInfo openDealLstt = new Gson().fromJson(mockResponse.getContentAsString(), ZohoReportListInfo.class);
      
      assertNull(openDealLstt.getOpenDealsLst());
      assertEquals(openDealLstt.getStatus(),FAILURE_STATUS_CODE);
    }


}