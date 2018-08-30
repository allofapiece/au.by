package com.epam.au.controller;

/**
 * Little wrapper for response, that renders in {@link com.epam.au.service.wrapper.HttpWrapper}
 * for ejecting page, httpErrors and other meta-data.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class ResponseInfo {
    private Page page;

    /**
     * Meta-data, that describes whether is need to redirect or forward in
     * {@link com.epam.au.service.wrapper.HttpWrapper}.
     */
    private boolean isUpdated;

    private int httpError;

    /**
     * Meta-data, that describes whether is ajax request.
     */
    private boolean isAjax;

    /**
     * Gets {@link Page page} object.
     *
     * @return Page page object.
     */
    public Page getPage() {
        return page;
    }

    /**
     * Sets {@link Page page} object.
     *
     * @param page page object.
     */
    public void setPage(Page page) {
        this.page = page;
    }

    /**
     * Gets meta-data isUpdated.
     *
     * @return boolean
     */
    public boolean isUpdated() {
        return isUpdated;
    }

    /**
     * Sets meta-data isUpdated.
     *
     * @param updated isUpdated value.
     */
    public void setIsUpdated(boolean updated) {
        isUpdated = updated;
    }

    /**
     * Gets title of the page object.
     *
     * @return String title.
     */
    public String getTitle() {
        return page.getTitle();
    }

    /**
     * Sets title of the page.
     *
     * @param title title of the page.
     */
    public void setTitle(String title) {
        this.page.setTitle(title);
    }

    /**
     * Gets path of the page object.
     *
     * @return String title.
     */
    public String getPath() {
        return page.getPath();
    }

    /**
     * Sets path of the page object.
     *
     * @param path path of the page.
     */
    public void setPath(String path) {
        page.setPath(path);
    }

    /**
     * Gets code of the http error if it has been set.
     *
     * @return int code of http error.
     */
    public int getHttpError() {
        return httpError;
    }

    /**
     * Sets code of the http error.
     *
     * @param httpError code of error.
     */
    public void setHttpError(int httpError) {
        this.httpError = httpError;
    }

    /**
     * Gets meta-data isAjax.
     *
     * @return boolean
     */
    public boolean isAjax() {
        return isAjax;
    }

    /**
     * Sets meta-data isAjax.
     *
     * @return boolean whether is an ajax.
     */
    public void setAjax(boolean ajax) {
        isAjax = ajax;
    }
}
