<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/jsp/template/header.jsp"/>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<div id="content-title" style="margin-top: 15px;">
    <h2><fmt:message key='${not empty title ? title : "title.default.home"}' /></h2>
    <hr>
</div>
<div class="row">
    <div class="col-6">
        <form action="<c:url value="/fc?command=account-connect"/>" method="post">
            <c:import url="/jsp/template/form-errors.jsp"/>
            <div class="form-group" style="margin-top: 15px;">
                <label for="account-number-field"><fmt:message key="account.field.number.label" /></label>
                <input type="text" name="account-number" class="form-control" id="account-number-field" aria-describedby="accountHelp" placeholder="<fmt:message key="account.field.number.placeholder"/>">
                <small id="accountHelp" class="form-text text-muted"><fmt:message key="account.field.number.help" /></small>
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="account.connect.submit.label" /></button>
        </form>
    </div>
</div>

<c:import url="/jsp/template/footer.jsp"/>