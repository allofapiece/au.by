package com.epam.au.service.deleter;

import com.epam.au.service.wrapper.HttpWrapper;

public interface Deleter {
    public HttpWrapper delete(HttpWrapper wrapper);
}
