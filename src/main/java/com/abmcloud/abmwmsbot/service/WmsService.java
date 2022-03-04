package com.abmcloud.abmwmsbot.service;

import com.abmcloud.abmwmsbot.dto.ReportData;
import com.abmcloud.abmwmsbot.dto.ReportRow;
import com.abmcloud.abmwmsbot.model.Organization;
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

    public ReportData  getReport(String url){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-type", "application/json");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                new HttpEntity("parameters", requestHeaders),
                ReportData.class).getBody();
    }

}
