package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.repositories.PartRepository;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *
 */
@Controller
public class AddPartController {
    @Autowired
    private ApplicationContext context;

    @GetMapping("/form/update-part")
    public String showPartFormForUpdate(@RequestParam("partID") int theId, Model theModel) {

        PartService repo = context.getBean(PartServiceImpl.class);
        OutsourcedPartService outsourcedrepo = context.getBean(OutsourcedPartServiceImpl.class);
        InhousePartService inhouserepo = context.getBean(InhousePartServiceImpl.class);

        boolean inhouse = true;
        List<OutsourcedPart> outsourcedParts = outsourcedrepo.findAll();
        for (OutsourcedPart outsourcedPart : outsourcedParts) {
            if (outsourcedPart.getId() == theId) inhouse = false;
        }
        String formtype;
        if (inhouse) {
            InhousePart inhousePart = inhouserepo.findById(theId);
            theModel.addAttribute("inhousepart", inhousePart);
            formtype = "InhousePartForm";
            theModel.addAttribute("title", "Update Inhouse Part");
            theModel.addAttribute("content", "InhousePartForm");
            return "layout";
        } else {
            OutsourcedPart outsourcedPart = outsourcedrepo.findById(theId);
            theModel.addAttribute("outsourcedpart", outsourcedPart);
            formtype = "OutsourcedPartForm";
            theModel.addAttribute("title", "Update Outsourced Part");
            theModel.addAttribute("content", "OutsourcedPartForm");
            return "layout";
        }

    }

    @GetMapping("/delete-part")
    public String deletePart(@Valid @RequestParam("partID") int theId, Model theModel) {
        PartService repo = context.getBean(PartServiceImpl.class);
        Part part = repo.findById(theId);
        if (part.getProducts().isEmpty()) {
            repo.deleteById(theId);
            theModel.addAttribute("title", "Delete Part");
            theModel.addAttribute("content", "confirmationdeletepart");
            return "layout";
        } else {
            return "negativeerror";
        }
    }

}
