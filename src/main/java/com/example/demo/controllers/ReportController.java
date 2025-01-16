package com.example.demo.controllers;

import com.example.demo.repositories.PurchaseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class ReportController {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final PurchaseRepository purchaseRepository;

    public ReportController(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    private List<Object[]> generatePurchases(int days) {
        Calendar calendar = Calendar.getInstance();
        Date endDate = calendar.getTime();
        calendar.add(Calendar.DATE, -days);
        Date startDate = calendar.getTime();

//        System.out.println("Generating from " + startDate + " to " + endDate);
        List<Object[]> purchases = purchaseRepository.findPurchasesBetweenDates(startDate, endDate);

        for (Object[] purchase : purchases) {
            System.out.println(Arrays.toString(purchase));
            purchase[3] = dateFormat.format(purchase[3]);
        }

        return purchases;
    }

    @GetMapping("/report/purchases")
    public String generatePurchasesReport(@RequestParam(name = "timeframe", required = false, defaultValue = "7") int timeframe, Model theModel) {
        System.out.println("Days -> " + timeframe);

        List<Object[]> purchases = generatePurchases(timeframe);

        theModel.addAttribute("purchases", purchases);
        theModel.addAttribute("timeframe", timeframe);
        theModel.addAttribute("title", "Purchases Report");
        theModel.addAttribute("content", "report");

        return "layout";
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/report/purchases/download")
    public List<Object[]> downloadPurchasesReport(@RequestParam(name = "timeframe", required = true) int timeframe) {
        System.out.println("request hit!");
        //        theModel.addAttribute("timeframe", timeframe);
        return generatePurchases(timeframe);
    }
}
