package com.example.demo.validators;

import com.example.demo.domain.Part;
import com.example.demo.service.PartService;
import com.example.demo.service.PartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 *
 *
 *
 */
public class PartBoundsValidator implements ConstraintValidator<ValidPartBounds, Part> {
    @Autowired
    private ApplicationContext context;
    public static  ApplicationContext myContext;
    @Override
    public void initialize(ValidPartBounds constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Part part, ConstraintValidatorContext constraintValidatorContext) {
        if(context==null) return true;
        if(context!=null)myContext=context;

        int minimum = part.getInvMin();
        int maximum = part.getInvMax();
        int currentStock = part.getInv();

        if(currentStock < minimum) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Current stock is lower than minimum!")
                    .addPropertyNode("inv")
                    .addConstraintViolation();
            return false;
        }

        if(currentStock > maximum) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Current stock is greater than maximum!")
                    .addPropertyNode("inv")
                    .addConstraintViolation();
            return false;
        }

        return true;
        }
    }
