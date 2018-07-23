package com.epam.au.controller;

public class ResponseInfo {
    private Page page;
    private boolean isUpdated;
    private int httpError;
    private boolean isAjax;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(boolean updated) {
        isUpdated = updated;
    }

    public String getTitle() {
        return page.getTitle();
    }

    public void setTitle(String title) {
        this.page.setTitle(title);
    }

    public String getPath() {
        return page.getPath();
    }

    public void setPath(String path) {
        page.setPath(path);
    }

    public int getHttpError() {
        return httpError;
    }

    public void setHttpError(int httpError) {
        this.httpError = httpError;
    }

    public boolean isAjax() {
        return isAjax;
    }

    public void setAjax(boolean ajax) {
        isAjax = ajax;
    }
}
