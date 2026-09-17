package com.dtorrez.main.common.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class BusinessRuleException  extends RuntimeException {
    private final String entityName;
    private final String rule;
    private final Map<String, Object> values;
    private final String code = "BR-412";
    public final static String NAME = "BUSINESS_RULE_ERROR";

    public BusinessRuleException(String entityName, String rule, Map<String, Object> values) {
        super(buildMessage(entityName, rule));
        this.entityName = entityName;
        this.rule = rule;
        this.values = values;
    }

    private static String buildMessage(String entityName, String rule) {
        return String.format("%s con %s", entityName, rule);
    }
}
