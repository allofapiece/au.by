<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/jsp/template/header.jsp"/>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<div id="content-title" style="margin-top: 15px;">
    <h2><fmt:message key='${not empty title ? title : "title.default.home"}'/></h2>
    <hr>
</div>
<div class="row">
    <div class="col-6">
        <form action="<c:url value="/fc?command=signup"/>" id="signup-form" method="post">
            <c:import url="/jsp/template/form-errors.jsp"/>
            <div class="form-group<c:if test="${errors['user.field.name'] ne null}"> invalid</c:if>">
                <label for="name-field"><fmt:message key="user.field.name.label" /></label>
                <input value="<c:if test="${name ne null}">${name}</c:if>" minlength="4" required type="text" name="name" class="form-control" id="name-field" aria-describedby="nameHelp" placeholder="<fmt:message key="user.field.name.placeholder" />">
                <small id="nameHelp" class="form-text text-muted"><fmt:message key="user.field.name.help" /></small>
                <c:if test="${errors['user.field.name'] ne null}">
                    <c:forEach items="${errors['user.field.name']}" var="error">
                        <div class="invalid-feedback"><fmt:message key="user.field.name.${error}.message" /></div>
                    </c:forEach>
                </c:if>
            </div>
            <div class="form-group<c:if test="${errors['user.field.surname'] ne null}"> invalid</c:if>">
                <label for="surname-field"><fmt:message key="user.field.surname.label" /></label>
                <input value="<c:if test="${surname ne null}">${surname}</c:if>" type="text" name="surname" class="form-control" id="surname-field" aria-describedby="surnameHelp" placeholder="<fmt:message key="user.field.surname.placeholder" />">
                <c:if test="${errors['user.field.surname'] ne null}">
                    <c:forEach items="${errors['user.field.surname']}" var="error">
                        <div class="invalid-feedback"><fmt:message key="user.field.surname.${error}.message" /></div>
                    </c:forEach>
                </c:if>
            </div>
            <div class="form-group<c:if test="${errors['user.field.email'] ne null}"> invalid</c:if>">
                <label for="email-field"><fmt:message key="user.field.email.label" /></label>
                <input value="<c:if test="${email ne null}">${email}</c:if>" type="email" name="email" class="form-control" id="email-field" aria-describedby="emailHelp" placeholder="<fmt:message key="user.field.email.placeholder" />">
                <c:if test="${errors['user.field.email'] ne null}">
                        <c:forEach items="${errors['user.field.email']}" var="error">
                            <div class="invalid-feedback"><fmt:message key="user.field.email.${error}.message" /></div>
                        </c:forEach>
                </c:if>
            </div>
            <div class="form-group<c:if test="${errors['user.field.password'] ne null}"> invalid</c:if>">
                <label for="password-field"><fmt:message key="user.field.password.label" /></label>
                <input type="password" name="password" class="form-control" id="password-field" aria-describedby="passwordHelp" placeholder="<fmt:message key="user.field.password.placeholder" />">
                <small id="passwordHelp" class="fo♦rm-text text-muted"><fmt:message key="user.field.password.help" /></small>
                <c:if test="${errors['user.field.password'] ne null}">
                    <c:forEach items="${errors['user.field.password']}" var="error">
                        <div class="invalid-feedback"><fmt:message key="user.field.password.${error}.message" /></div>
                    </c:forEach>
                </c:if>
            </div>
            <div class="form-group<c:if test="${errors['user.field.confirmation'] ne null}"> invalid</c:if>">
                <label for="confirmation-field"><fmt:message key="user.field.confirmation.label" /></label>
                <input type="password" name="confirm-password" class="form-control" id="confirmation-field" aria-describedby="confirmationHelp" placeholder="<fmt:message key="user.field.confirmation.placeholder" />">
                <c:if test="${errors['user.field.confirmation'] ne null}">
                    <c:forEach items="${errors['user.field.confirmation']}" var="error">
                        <div class="invalid-feedback"><fmt:message key="user.field.confirmation.${error}.message" /></div>
                    </c:forEach>
                </c:if>
            </div>
        </form>
    </div>
    <div class="col-6">
        <h4><fmt:message key="user.account.connect.title"/></h4>
        <p style="margin-top: 15px;"><fmt:message key="account.description.text"/></p>
        <div class="form-check">
            <input type="checkbox" form="signup-form" class="form-check-input" name="connect-account" id="accountConnection">
            <label class="form-check-label" for="accountConnection"><fmt:message key="user.account.connect.label" /></label>
        </div>
        <div class="form-group<c:if test="${errors['account.field.number'] ne null}"> invalid</c:if>" style="margin-top: 15px;">
            <label for="account-number-field"><fmt:message key="account.field.number.label" /></label>
            <input type="text" form="signup-form" name="account-number" class="form-control" id="account-number-field" aria-describedby="accountHelp" placeholder="<fmt:message key="account.field.number.placeholder"/>">
            <small id="accountHelp" class="form-text text-muted"><fmt:message key="user.field.name.help" /></small>
            <c:if test="${errors['account.field.number'] ne null}">
                <c:forEach items="${errors['account.field.number']}" var="error">
                    <div class="invalid-feedback"><fmt:message key="account.field.number.${error}.message" /></div>
                </c:forEach>
            </c:if>
        </div>
    </div>
    <button type="submit" form="signup-form" class="btn btn-primary" style="margin-left: 15px;"><fmt:message key="signup.submit.label" /></button>
</div>

<c:import url="/jsp/template/footer.jsp"/>