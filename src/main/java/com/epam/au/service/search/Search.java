package com.epam.au.service.search;

import com.epam.au.service.wrapper.HttpWrapper;

import java.util.List;

public interface Search {
    Object search(Criteria condition, HttpWrapper wrapper);
}
