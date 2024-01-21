package com.project.shopapp.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ValidateUtils {

    public boolean isNumeric(String value) {
        if (value == null) {
            return false;
        }
        // Sử dụng regex để kiểm tra chuỗi có chứa chỉ số và dấu thập phân (nếu có)
        return value.matches("^\\d+(\\.\\d+)?$");
    }

    public boolean canParseToDouble(Object obj) {
        try {
            if (obj instanceof String) {
                // Nếu obj là một chuỗi, thử chuyển đổi thành double
                Double.parseDouble((String) obj);
                return true;
            } else if (obj instanceof Number) {
                // Nếu obj là một số, thử lấy giá trị double
                ((Number) obj).doubleValue();
                return true;
            }
            // Nếu obj không phải là chuỗi hoặc số, không thể chuyển đổi thành double
            return false;
        } catch (NumberFormatException e) {
            // Nếu có lỗi NumberFormatException, không thể chuyển đổi thành double
            return false;
        }
    }

    public boolean isValidDate(String input) {
        String regex = "\\d{4}-\\d{2}-\\d{2};\\d{4}-\\d{2}-\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }


}
