<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<c:set var="context" value="${pageContext.request.contextPath}" />

<fmt:setLocale value="${language}" />
<fmt:setBundle basename="localization.local" />

<!DOCTYPE html>
<html lang="${language}">
    <head>
        <link rel="stylesheet" href="${context}/vendor/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${context}/css/style.css">
        <title>efs</title>
    </head>
    <body>
    <nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd;">
        <div class="container">
            <a class="navbar-brand" href="<c:url value="/fc?command=lots"/>">au.by</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="<c:url value="/fc?command=lots"/>"><fmt:message key="nav.bar.main" /><span class="sr-only">(current)</span></a>
                    </li>
                    <c:if test="${user ne null}">
                        <li class="nav-item">
                            <a class="nav-link" href="#"><fmt:message key="nav.bar.my.products" /></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#"><fmt:message key="nav.bar.my.lots" /></a>
                        </li>
                    </c:if>
                </ul>
                <c:choose>
                    <c:when  test="${user ne null}">
                        <span class="navbar-text nav-user-info">
                            <fmt:message key="nav.bar.user.info" /> <p class="user-name">${user.name}</p>
                        </span>
                        <a class="btn btn-primary" href="<c:url value="/fc?command=logout"/>" role="button">
                            <fmt:message key="nav.bar.user.action.logout" />
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn btn-primary" href="<c:url value="/fc?command=signin"/>" role="button" style="margin-right: 5px;">
                            <fmt:message key="nav.bar.user.action.signin" />
                        </a>
                        <a class="btn btn-primary" href="<c:url value="/fc?command=signup"/>" role="button">
                            <fmt:message key="nav.bar.user.action.signup" />
                        </a>
                    </c:otherwise>
                </c:choose>

                <%--<form class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-2" type="search" placeholder="<fmt:message key="summary.search"/>" aria-label="Search">
                    <button class="btn btn-outline-primary my-2 my-sm-0" type="submit"><fmt:message key="summary.search"/></button>
                </form>--%>
            </div>
        </div>

    </nav>
    <div class="container" id="content">