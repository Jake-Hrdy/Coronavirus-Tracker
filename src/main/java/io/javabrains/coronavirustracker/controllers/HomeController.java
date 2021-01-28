package io.javabrains.coronavirustracker.controllers;

import io.javabrains.coronavirustracker.models.LocationStats;
import io.javabrains.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        int totalReportedCases = allStats.stream().mapToInt(LocationStats::getLatestTotalCases).sum();
        String totalReportedCasesWithCommas = numberFormat.format(totalReportedCases);

        int totalNewCases = allStats.stream().mapToInt(LocationStats::getDiffFromPrevDay).sum();
        String totalNewCasesWithCommas = numberFormat.format(totalNewCases);

        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCasesWithCommas);
        model.addAttribute("totalNewCases", totalNewCasesWithCommas);

        return "home";
    }
}
