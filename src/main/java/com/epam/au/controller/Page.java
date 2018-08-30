package com.epam.au.controller;

/**
 * Contains title and path for http page. Uses in {@link ResponseInfo} class
 * for describing behavior of real page.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class Page {
    private String title;
    private String path;

    /**
     * Default constructor
     */
    public Page() {
    }

    /**
     * Constructor for setting all data of object.
     *
     * @param title page title.
     * @param path  page path in project directories.
     */
    public Page(String title, String path) {
        this.title = title;
        this.path = path;
    }

    /**
     * Gets title of page.
     *
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title of page.
     *
     * @param title title of page.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets path of page.
     *
     * @return String
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets path of page.
     *
     * @param path path of page.
     */
    public void setPath(String path) {
        this.path = path;
    }
}
