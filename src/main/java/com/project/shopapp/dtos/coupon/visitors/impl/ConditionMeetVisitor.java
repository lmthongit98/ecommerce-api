package com.project.shopapp.dtos.coupon.visitors.impl;

import com.project.shopapp.dtos.coupon.conditions.impl.BetweenDateCondition;
import com.project.shopapp.dtos.coupon.conditions.impl.GreaterThanCondition;
import com.project.shopapp.dtos.coupon.conditions.impl.GreaterThanOrEqualCondition;
import com.project.shopapp.dtos.coupon.conditions.impl.InCondition;
import com.project.shopapp.dtos.coupon.visitors.ConditionVisitor;

import java.util.*;

public class ConditionMeetVisitor implements ConditionVisitor {

    public List<String> unsatisfiedMessages = new ArrayList<>();

    @Override
    public void visit(BetweenDateCondition condition) {
        if (condition.getAttributeValue().isEqual(condition.getFromDate()) || condition.getAttributeValue().isEqual(condition.getToDate())) {
            return;
        }
        if (!(condition.getAttributeValue().isAfter(condition.getFromDate()) && condition.getAttributeValue().isBefore(condition.getToDate()))) {
            unsatisfiedMessages.add(String.format("%s must between %s and %s", condition.getAttribute(), condition.getFromDate(), condition.getToDate()));
        }
    }

    @Override
    public void visit(GreaterThanCondition condition) {
        if (!(condition.getAttributeValue() > condition.getRequiredValue())) {
            unsatisfiedMessages.add(String.format("%s must be > %.2f", condition.getAttribute(), condition.getRequiredValue()));
        }
    }

    @Override
    public void visit(GreaterThanOrEqualCondition condition) {
        if (!(condition.getAttributeValue() >= condition.getRequiredValue())) {
            unsatisfiedMessages.add(String.format("%s must be >= %.2f", condition.getAttribute(), condition.getRequiredValue()));
        }
    }

    @Override
    public void visit(InCondition condition) {
        Set<String> requiredValuesSet = new HashSet<>(Arrays.asList(condition.getRequiredValues()));
        if (Arrays.stream(condition.getAttributeValues()).noneMatch(requiredValuesSet::contains)) {
            unsatisfiedMessages.add(String.format("%s must be one of %s", condition.getAttribute(), Arrays.toString(condition.getRequiredValues())));
        }
    }

    public String getUnsatisfiedMessages() {
        return String.join(", ", unsatisfiedMessages);
    }

    public boolean isConditionMeet() {
        return unsatisfiedMessages.isEmpty();
    }

}
