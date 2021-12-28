package com.abmcloud.abmwmsbot.service;

import com.abmcloud.abmwmsbot.model.BotUser;
import com.abmcloud.abmwmsbot.model.Organization;
import com.abmcloud.abmwmsbot.model.Report;
import com.abmcloud.abmwmsbot.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public List<Report> getReportsByUser(BotUser user){
        Organization userOrganization = user.getOrganization();
        return reportRepository.getAllByOrganization(userOrganization);
    }

    public List<Report> getAll() {
        return reportRepository.findAll();
    }
}
