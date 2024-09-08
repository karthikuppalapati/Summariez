package com.better.Summariez.utils;

import com.better.Summariez.constants.ValidationConstants;
import com.better.Summariez.dtos.UserRegistrationDTO;
import com.better.Summariez.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ValidationUtils {

    // Should be handled from frontend
    public Map<String, String> validateRegistrationDTO(UserRegistrationDTO registrationDTO) {
        Map<String, String> errors = new HashMap<>();
        if(registrationDTO != null) {
            if(registrationDTO.getPassword().isEmpty() || registrationDTO.getDob() == null)
                errors.put(ValidationConstants.DOB, ValidationConstants.EMPTY);
            return errors;
        }
        errors.put(ValidationConstants.PAYLOAD, ValidationConstants.EMPTY);
        return errors;
    }
}
