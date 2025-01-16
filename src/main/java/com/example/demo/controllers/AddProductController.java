package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.PartService;
import com.example.demo.service.PartServiceImpl;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Controller
public class AddProductController {
    @Autowired
    private ApplicationContext context;
    private PartService partService;
    private List<Part> theParts;
    private static Product product1;
    private Product product;

    @GetMapping("/form/product")
    public String showFormAddPart(Model theModel) {
        theModel.addAttribute("parts", partService.findAll());
        product = new Product();
        product1 = product;
        theModel.addAttribute("product", product);

        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!product.getParts().contains(p)) availParts.add(p);
        }
        theModel.addAttribute("availparts", availParts);
        theModel.addAttribute("assparts", product.getParts());
        theModel.addAttribute("title", "Add Product");
        theModel.addAttribute("content", "productForm");
        return "layout";
    }

    @PostMapping("/form/product")
    public String submitForm(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model theModel) {
        theModel.addAttribute("product", product);

        if (bindingResult.hasErrors()) {
            ProductService productService = context.getBean(ProductServiceImpl.class);
            Product product2 = new Product();
            try {
                product2 = productService.findById((int) product.getId());
            } catch (Exception e) {
                System.out.println("Error Message " + e.getMessage());
            }
            theModel.addAttribute("parts", partService.findAll());
            List<Part> availParts = new ArrayList<>();
            for (Part p : partService.findAll()) {
                if (!product2.getParts().contains(p)) availParts.add(p);
            }
            theModel.addAttribute("availparts", availParts);
            theModel.addAttribute("assparts", product2.getParts());
            theModel.addAttribute("title", "Add Product");
            theModel.addAttribute("content", "productForm");
            return "layout";
        }
        //       theModel.addAttribute("assparts", assparts);
        //       this.product=product;
//        product.getParts().addAll(assparts);
        else {
            ProductService repo = context.getBean(ProductServiceImpl.class);
            if (product.getId() != 0) {
                Product product2 = repo.findById((int) product.getId());
                PartService partService1 = context.getBean(PartServiceImpl.class);
                if (product.getInv() - product2.getInv() > 0) {
                    for (Part p : product2.getParts()) {
                        int inv = p.getInv();
                        p.setInv(inv - (product.getInv() - product2.getInv()));
                        partService1.save(p);
                    }
                }
            } else {
                product.setInv(0);
            }
            repo.save(product);
            theModel.addAttribute("title", "Added Product");
            theModel.addAttribute("content", "confirmationaddproduct");
            return "layout";
        }
    }

    @GetMapping("/form/update-product")
    public String showProductFormForUpdate(@RequestParam("productID") int theId, Model theModel) {
        theModel.addAttribute("parts", partService.findAll());
        ProductService repo = context.getBean(ProductServiceImpl.class);
        Product theProduct = repo.findById(theId);
        product1 = theProduct;
        //    this.product=product;
        //set the employ as a model attibute to prepopulate the form
        theModel.addAttribute("product", theProduct);
        theModel.addAttribute("assparts", theProduct.getParts());
        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!theProduct.getParts().contains(p)) availParts.add(p);
        }
        theModel.addAttribute("availparts", availParts);
        //send over to our form
        theModel.addAttribute("title", "Update Product");
        theModel.addAttribute("content", "productForm");
        return "layout";
    }

    @GetMapping("/delete-product")
    public String deleteProduct(@RequestParam("productID") int theId, Model theModel) {
        try {
            ProductService productService = context.getBean(ProductServiceImpl.class);
            Product product2 = productService.findById(theId);
            for (Part part : product2.getParts()) {
                part.getProducts().remove(product2);
                partService.save(part);
            }
            product2.getParts().removeAll(product2.getParts());
            productService.save(product2);
            productService.deleteById(theId);
            theModel.addAttribute("title", "Deleted Product");
            theModel.addAttribute("content", "confirmationdeleteproduct");
            return "layout";
        } catch (Exception e) {
            theModel.addAttribute("title", "Error Deleting Product");
            theModel.addAttribute("errorMessage", "Please remove parts from product first!");
            theModel.addAttribute("content", "confirmationerrormessage");
            return "layout";
        }
    }

    public AddProductController(PartService partService) {
        this.partService = partService;
    }
// make the add and remove buttons work

    @GetMapping("/form/associate-part")
    public String associatePart(@Valid @RequestParam("partID") int theID, Model theModel) {
        //    theModel.addAttribute("product", product);
        //    Product product1=new Product();
        if (product1.getName() == null) {
            return "saveproductscreen";
        } else {
            product1.getParts().add(partService.findById(theID));
            partService.findById(theID).getProducts().add(product1);
            ProductService productService = context.getBean(ProductServiceImpl.class);
            productService.save(product1);
            partService.save(partService.findById(theID));
            theModel.addAttribute("product", product1);
            theModel.addAttribute("assparts", product1.getParts());
            List<Part> availParts = new ArrayList<>();
            for (Part p : partService.findAll()) {
                if (!product1.getParts().contains(p)) availParts.add(p);
            }
            theModel.addAttribute("availparts", availParts);
            theModel.addAttribute("title", "Associate Part");
            theModel.addAttribute("content", "productForm");
            return "layout";
        }
        //        return "confirmationassocpart";
    }

    @GetMapping("/form/remove-part")
    public String removePart(@RequestParam("partID") int theID, Model theModel) {
        theModel.addAttribute("product", product);
        //  Product product1=new Product();
        product1.getParts().remove(partService.findById(theID));
        partService.findById(theID).getProducts().remove(product1);
        ProductService productService = context.getBean(ProductServiceImpl.class);
        productService.save(product1);
        partService.save(partService.findById(theID));
        theModel.addAttribute("product", product1);
        theModel.addAttribute("assparts", product1.getParts());
        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!product1.getParts().contains(p)) availParts.add(p);
        }
        theModel.addAttribute("availparts", availParts);
        theModel.addAttribute("title", "Remove Associated Part");
        theModel.addAttribute("content", "productForm");
        return "layout";
    }
}
