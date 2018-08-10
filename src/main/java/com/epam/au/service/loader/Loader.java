package com.epam.au.service.loader;

import com.epam.au.service.wrapper.HttpWrapper;

import java.util.List;

public interface Loader {
    List<?> loadAll(HttpWrapper wrapper);
    List<?> loadByUser(HttpWrapper wrapper);
    Object load(long id);
}
