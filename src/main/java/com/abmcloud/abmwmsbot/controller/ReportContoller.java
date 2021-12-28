package com.abmcloud.abmwmsbot.controller;

import com.abmcloud.abmwmsbot.model.Report;
import com.abmcloud.abmwmsbot.repository.UserRepository;
import com.abmcloud.abmwmsbot.service.ReportService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reports")
public class ReportContoller {

    private final ReportService reportService;
    private final UserRepository userRepository;

    @GetMapping("/")
    @ApiOperation(value = "Получить все отчеты")
    public List<Report> getReports() {
        return reportService.getAll();
    }

}