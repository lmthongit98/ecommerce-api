package com.project.shopapp.dtos.requests;

import com.project.shopapp.enums.Operator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponConditionDto {
    private String attribute;
    private Operator operator;
    private String value;
}
