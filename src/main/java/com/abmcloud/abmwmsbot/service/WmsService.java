package com.abmcloud.abmwmsbot.service;

import com.abmcloud.abmwmsbot.model.Organization;
import com.abmcloud.abmwmsbot.model.ReportData;
import com.abmcloud.abmwmsbot.model.ReportRow;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WmsService {

    private final RestTemplate restTemplate;

    public List<ReportRow> getReports(Organization organization){
        String uri = organization.getId()+"/api/reports/";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-type", "application/json");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                new HttpEntity("parameters", requestHeaders),
                ReportData.class).getBody().getData();
    }

    public List<ReportRow>  getReport(String uri){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-type", "application/json");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                new HttpEntity("parameters", requestHeaders),
                ReportData.class).getBody().getData();
    }

}
