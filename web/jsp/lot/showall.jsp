<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/jsp/template/header.jsp"/>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<div id="content-title" style="margin-top: 15px;">
    <h2></h2>
    <hr>
</div>
<div class="row">
    <div class="container-fluid">
        <form class="form-inline lot-search">
            <input type="search" class="form-control mr-lg-2" placeholder="<fmt:message key="summary.search" />">
            <a class="btn search-button btn-primary"><fmt:message key="summary.search.button" /></a>
            <c:import url="/jsp/lot/lot-filter.jsp"/>
        </form>

    </div>
</div>
<div class="row" style="margin-top: 15px;">
    <div class="container-fluid">
        <div class="lots card-columns">
            <c:import url="/jsp/template/lot.jsp"/>
        </div>
    </div>
</div>

<script src="${context}/js/lots.js"></script>

<c:import url="/jsp/template/footer.jsp"/>