package com.project.shopapp.enums;

import lombok.Getter;

@Getter
public enum Attribute {

    TOTAL_AMOUNT("Total amount", AttributeType.NUMBER),
    PURCHASE_DATE("Purchase date", AttributeType.DATE),
    CATEGORIES("Categories", AttributeType.STRING);


    private final String name;
    private final AttributeType type;

    Attribute(String name, AttributeType type) {
        this.name = name;
        this.type = type;
    }

}
