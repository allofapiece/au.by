<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="localization.local"/>

<c:import url="/jsp/template/header.jsp"/>

<div id="content-title" style="margin-top: 15px;">
    <h2></h2>
    <a class="btn btn-primary" href="<c:url value="/fc?command=lot-add"/>"><fmt:message key="lot.submit.add.label" /></a>
    <hr>
</div>
<div class="row">
    <div class="container-fluid">
        <form class="form-inline lot-search">
            <input type="search" class="form-control mr-lg-2" placeholder="Search for..">
            <button type="submit" class="btn btn-primary">GO</button>
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