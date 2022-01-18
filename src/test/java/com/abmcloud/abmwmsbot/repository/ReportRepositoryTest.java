package com.abmcloud.abmwmsbot.repository;

import com.abmcloud.abmwmsbot.enums.ChartTypes;
import com.abmcloud.abmwmsbot.model.Organization;
import com.abmcloud.abmwmsbot.model.Report;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    @Order(1)
    public void testReportSave() {
        Optional<Organization> org1Optional = organizationRepository.findById(1L);
        assertTrue(org1Optional.isPresent());
        Organization org1 = org1Optional.get();

        Report newReport = new Report(5L, "Waited receipts", "/waited_receipts", "Waited receipts", ChartTypes.LINE,
                            "Receipt list that will come in further", org1);
        reportRepository.save(newReport);
        Optional<Report> report5Optional = reportRepository.findById(5L);
        assertTrue(report5Optional.isPresent());
        assertEquals(newReport, report5Optional.get());
    }

    @Test
    @Order(2)
    public void testReportRead() {
        Optional<Organization> org1Optional = organizationRepository.findById(1L);
        assertTrue(org1Optional.isPresent());
        Organization org1 = org1Optional.get();
        List<Report> reportsByOrg1 = reportRepository.getAllByOrganization(org1);
        assertEquals(5 ,reportsByOrg1.size());

        Optional<Report> report5Optional = reportRepository.findById(5L);
        assertTrue(report5Optional.isPresent());
        Report report5 = report5Optional.get();
        assertEquals("Waited receipts", report5.getName());
        assertEquals("/waited_receipts", report5.getUri());
        assertEquals("Waited receipts", report5.getAlias());
        assertEquals(ChartTypes.LINE, report5.getChartType());
        assertEquals("Receipt list that will come in further", report5.getDescription());
        assertEquals("Receipt list that will come in further", report5.getDescription());
        assertEquals(org1, report5.getOrganization());
    }

    @Test
    @Order(3)
    public void testReportUpdate() {
        Optional<Report> report5OptionalOld = reportRepository.findById(5L);
        assertTrue(report5OptionalOld.isPresent());
        Report report5 = report5OptionalOld.get();
        report5.setName("Simple report");
        report5.setUri("/simple_report");
        report5.setAlias("Simple report");
        report5.setChartType(ChartTypes.PIE);
        report5.setDescription("Report that returns simple data");
        Optional<Organization> org2Optional = organizationRepository.findById(2L);
        assertTrue(org2Optional.isPresent());
        Organization org2 = org2Optional.get();
        report5.setOrganization(org2);
        reportRepository.save(report5);

        Optional<Report> report5Optional = reportRepository.findById(5L);
        assertEquals(report5, report5Optional.get());
    }

    @Test
    @Order(4)
    public void testReportDelete() {
        reportRepository.deleteById(5L);

        Optional<Report> report5 = reportRepository.findById(5L);
        assertTrue(report5.isEmpty());
    }

}