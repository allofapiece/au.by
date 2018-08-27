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
        <form action="<c:url value="/fc?command=signin"/>" method="post">
            <c:import url="/jsp/template/form-errors.jsp"/>
            <div class="form-group">
                <c:if test="${errors.email ne null}">
                    <div class="alert alert-danger" role="alert">
                        <c:forEach items="${errors.email}" var="error">
                            ${error}<br>
                        </c:forEach>
                    </div>
                </c:if>
                <label for="email-field"><fmt:message key="user.field.email.label" /></label>
                <input type="email" name="email" class="form-control" id="email-field" aria-describedby="emailHelp" placeholder="<fmt:message key="user.field.email.placeholder" />">
            </div>
            <div class="form-group">
                <label for="password-field"><fmt:message key="user.field.password.label" /></label>
                <input type="password" name="password" class="form-control" id="password-field" aria-describedby="passwordHelp" placeholder="<fmt:message key="user.field.password.placeholder" />">
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="signin.submit.label" /></button>
        </form>
    </div>
</div>
<c:import url="/jsp/template/footer.jsp"/>