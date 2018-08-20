<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="localization.local" />

<c:import url="/jsp/template/header.jsp"/>

<div class="row">
    <div class="col-12">
        <c:forEach items="${errors}" var="field">
            <c:if test="${field.key == 'account.page'}">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <c:forEach items="${field.value}" var="error">
                        <p><fmt:message key='${field.key}.${error}.message' /></p>
                    </c:forEach>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <c:set target="${errors}" property="account.page" value="${null}"/>
            </c:if>
        </c:forEach>
    </div>
</div>
<div id="content-title" style="margin-top: 15px;">
    <h2><fmt:message key='${not empty title ? title : "title.default.home"}' /></h2>
    <hr>
</div>
<div class="row">
    <div class="col-6">
        <form action="<c:url value="/fc?command=account-connect"/>" method="post">
            <c:if test="${errors ne null and not empty errors}">
                <div class="alert alert-danger" role="alert">
                    <h4 class="alert-heading"><fmt:message key="signup.error.form.summary.title" /></h4>
                    <c:forEach items="${errors}" var="field">
                        <h6 class="alert-heading"><fmt:message key='${field.key}.label' /></h6>
                        <c:forEach items="${field.value}" var="error">
                            <p><fmt:message key='${field.key}.${error}.message' /></p>
                        </c:forEach>
                    </c:forEach>
                </div>
            </c:if>
            <div class="form-group" style="margin-top: 15px;">
                <label for="account-number-field"><fmt:message key="account.field.number.label" /></label>
                <input type="text" name="account-number" class="form-control" id="account-number-field" aria-describedby="accountHelp" placeholder="<fmt:message key="account.field.number.placeholder"/>">
                <small id="accountHelp" class="form-text text-muted"><fmt:message key="account.field.number.help" /></small>
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="account.connect.submit.label" /></button>
        </form>
    </div>
    <div class="col-6">
        <h4><fmt:message key="user.account.connect.title"/></h4>
        <p style="margin-top: 15px;"><fmt:message key="account.description.text"/></p>
    </div>
</div>

<c:import url="/jsp/template/footer.jsp"/>