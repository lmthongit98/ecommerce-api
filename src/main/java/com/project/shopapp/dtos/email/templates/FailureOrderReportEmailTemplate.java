package com.project.shopapp.dtos.email.templates;

import com.project.shopapp.dtos.email.EmailTemplate;

public class FailureOrderReportEmailTemplate extends EmailTemplate {

    @Override
    public String getTemplateName() {
        return "failure_order_report_template";
    }

}
