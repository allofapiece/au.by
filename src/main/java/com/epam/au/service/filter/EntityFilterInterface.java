package com.epam.au.service.filter;


import java.util.Collection;
import java.util.Map;

public interface EntityFilterInterface {
    Collection filter(Collection entities, Map<String, Criteria> conditions);
}
