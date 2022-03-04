package com.abmcloud.abmwmsbot.service;

import com.abmcloud.abmwmsbot.dto.ReportData;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
//@RestClientTest
@PropertySource("classpath:test.properties")
//@ImportAutoConfiguration(RestTemplate.class)
//@AutoConfigureWebClient(registerRestTemplate = true)
class WmsServiceTest {

    private final String reportUrl = "http://127.0.0.1:8081/AbmWms/api/reports/turnover";

    @InjectMocks
    private WmsService wmsService;

//    @MockBean
//    private RestTemplate restTemplate;

    @Test
    public void testGettingReportData(){
//        mockMvc.perform().andExpect(status().isOk());
        ReportData report = wmsService.getReport(reportUrl);
        assertNotNull(report);

    }


}