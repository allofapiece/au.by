package com.epam.au.service.filter;

import com.epam.au.entity.lot.LotStatus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CriteriaProvider {
    private static volatile CriteriaProvider instance;
    private Map<Rule, Map<String, Criteria>> ruleMap;

    private CriteriaProvider() {
        ruleMap = new HashMap<>();
        initRules();
    }

    public static CriteriaProvider getInstance() {
        if (instance == null)
            synchronized (CriteriaProvider.class) {
                if (instance == null)
                    instance = new CriteriaProvider();
            }
        return instance;
    }

    public Map<String, Criteria> getCriterias(Rule rule) throws IllegalRuleException {
        Map<String, Criteria> criterias = ruleMap.get(rule);

        if (criterias == null) {
            throw new IllegalRuleException();
        }

        return criterias;
    }

    public void addRule(Rule rule, Map<String, Criteria> criterias) {
        if (rule != null && criterias != null) {
            ruleMap.put(rule, criterias);
        }
    }

    public void addRules(Rule rule, Map<String, Criteria> criterias) {
        if (rule != null && criterias != null) {
            ruleMap.put(rule, criterias);
        }
    }

    private void initRules() {
        Criteria criteria = new Criteria();
        List<Object> values = new LinkedList<>();

        values.add(LotStatus.PROPOSED);
        values.add(LotStatus.CLOSED);

        criteria.setValues(values);
        criteria.setMode("!=");

        Map<String, Criteria> criteriaMap = new HashMap<>();
        criteriaMap.put("status", criteria);

        addRule(Rule.ALL_LOT_FILTER, criteriaMap);
    }
}
