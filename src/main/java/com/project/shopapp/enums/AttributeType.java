package com.project.shopapp.enums;

import com.project.shopapp.utils.ValidateUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public enum AttributeType {
    NUMBER {
        @Override
        public boolean isValid(String requiredValue, Object actualValue) {
            return ValidateUtils.isNumeric(requiredValue) && ValidateUtils.canParseToDouble(requiredValue) && ValidateUtils.canParseToDouble(actualValue);
        }
    },
    STRING {
        @Override
        public boolean isValid(String requiredValue, Object actualValue) {
            return StringUtils.hasText(requiredValue) && StringUtils.hasText(actualValue.toString());
        }
    },
    DATE {
        @Override
        public boolean isValid(String requiredValue, Object actualValue) {
            return ValidateUtils.isValidDate(requiredValue) && actualValue instanceof LocalDate;
        }
    };

    public abstract boolean isValid(String requiredValue, Object actualValue);

}
