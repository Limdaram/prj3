package com.soprj.sharedone_prj.controller.report;

import com.soprj.sharedone_prj.domain.report.ReportHeaderDto;
import com.soprj.sharedone_prj.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("orderReport")
    public void report(Model model) {
//        List<ReportHeaderDto> report = reportService.getOrderHeader();
        List<ReportHeaderDto> report = reportService.reportList();
        model.addAttribute("report", report);

        List<Map<String, String>> sortedReport = reportService.sortedReport();
        model.addAttribute("sortedReport", sortedReport);

        List<Map<String, String>> buyerReport = reportService.buyerReport();
        model.addAttribute("buyerReport", buyerReport);
    }

    @RequestMapping("orderReport/{m_order_id}")
    @ResponseBody
    public ReportHeaderDto reportDetail(@PathVariable int m_order_id) {
        ReportHeaderDto result = reportService.reportDetail(m_order_id);

        return result;
    }
}
