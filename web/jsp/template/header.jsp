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
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <link rel="stylesheet" href="${context}/vendor/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${context}/css/style.css">
        <link rel="stylesheet" href="${context}/css/theme.css">
        <link rel="stylesheet" href="${context}/css/adaptivity.css">
        <link rel="stylesheet" href="${context}/css/product.css">
        <link rel="stylesheet" href="${context}/css/lots.css">
        <link rel="stylesheet" href="${context}/css/validation.css">

        <script src="${context}/vendor/jquery/jquery-3.3.1.min.js"></script>
        <script src="${context}/vendor/bootstrap/js/bootstrap.min.js"></script>
        <%--<script src="${context}/vendor/vue.js"></script>--%>
        <script src="${context}/js/timer.js"></script>
        <script src="${context}/js/main.js"></script>
        <script src="${context}/js/style.js"></script>

        <title><fmt:message key="${not empty title ? title : 'title.default.home'}" /></title>
    </head>
    <body>
    <div id="wrapper">
        <nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd;">
            <div class="container">
                <a class="navbar-brand" href="<c:url value="/fc?command=lot-show&scope=all"/>">au.by</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="<c:url value="/fc?command=lot-show&scope=all"/>"><fmt:message key="nav.bar.main" /><span class="sr-only">(current)</span></a>
                        </li>
                        <c:if test="${user ne null}">
                            <li class="nav-item">
                                <a class="nav-link" href="<c:url value="/fc?command=product-show"/>"><fmt:message key="nav.bar.my.products" /></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<c:url value="/fc?command=lot-show&scope=mine"/>"><fmt:message key="nav.bar.my.lots" /></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<c:url value="/fc?command=account-show"/>"><fmt:message key="nav.bar.account.show" /></a>
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
                </div>
            </div>
        </nav>
        <div class="container" id="content" style="margin-top: 15px;">
            <div class="row">
                <div class="col-12">
                    <c:forEach items="${errors}" var="field">
                        <c:if test="${field.key == 'page'}">
                            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                <c:forEach items="${field.value}" var="warn">
                                    <p><fmt:message key='${field.key}.${warn}.message' /></p>
                                </c:forEach>
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <c:set target="${errors}" property="page" value="${null}"/>
                        </c:if>
                    </c:forEach>
                </div>
            </div>