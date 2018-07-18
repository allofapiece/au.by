package com.epam.au.controller;

public class ResponseInfo {
    private String page;
    private boolean isUpdated;
    private String title;
    private int httpError;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(boolean updated) {
        isUpdated = updated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHttpError() {
        return httpError;
    }

    public void setHttpError(int httpError) {
        this.httpError = httpError;
    }
}
