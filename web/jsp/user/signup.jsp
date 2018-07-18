<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="localization.local" />

<c:import url="/jsp/template/header.jsp"/>

<div id="content-title" style="margin-top: 15px;">
    <h2>${not empty title ? title : "Home"}</h2>
    <hr>
</div>
<div class="row">
    <div class="col-6">
        <form action="<c:url value="/fc?command=signup"/>" id="signup-form" method="post">
            <c:if test="${errors ne null and not empty errors}">
                <div class="alert alert-danger" role="alert">
                    <h5 class="alert-heading"><fmt:message key="signup.error.form.summary.title" /></h5>
                    <c:forEach items="${errors}" var="field">
                        <h6 class="alert-heading"><fmt:message key='${field.key}.label' /></h6>
                        <c:forEach items="${field.value}" var="error">
                            <p><fmt:message key='${field.key}.${error}.message' /></p>
                        </c:forEach>
                    </c:forEach>
                </div>
            </c:if>
            <div class="form-group">
                <label for="name-field"><fmt:message key="user.field.name.label" /></label>
                <input type="text" name="name" class="form-control" id="name-field" aria-describedby="nameHelp" placeholder="Enter your name">
                <small id="nameHelp" class="form-text text-muted"><fmt:message key="user.field.name.help" /></small>
            </div>
            <div class="form-group">
                <label for="surname-field"><fmt:message key="user.field.surname.label" /></label>
                <input type="text" name="surname" class="form-control" id="surname-field" aria-describedby="surnameHelp" placeholder="Enter your surname">
            </div>
            <div class="form-group">
                <c:if test="${errors.email ne null}">
                    <div class="alert alert-danger" role="alert">
                        <c:forEach items="${errors.email}" var="error">
                            ${error}<br>
                        </c:forEach>
                    </div>
                </c:if>
                <label for="email-field"><fmt:message key="user.field.email.label" /></label>
                <input type="email" name="email" class="form-control" id="email-field" aria-describedby="emailHelp" placeholder="Enter email">
            </div>
            <div class="form-group">
                <label for="password-field"><fmt:message key="user.field.password.label" /></label>
                <input type="password" name="password" class="form-control" id="password-field" aria-describedby="passwordHelp" placeholder="Enter password">
                <small id="passwordHelp" class="form-text text-muted"><fmt:message key="user.field.password.help" /></small>
            </div>
            <div class="form-group">
                <label for="confirmation-field"><fmt:message key="user.field.confirmation.label" /></label>
                <input type="password" name="confirm-password" class="form-control" id="confirmation-field" aria-describedby="confirmationHelp" placeholder="Confirm your password">
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="signup.submit.label" /></button>
        </form>
    </div>
    <div class="col-6">
        <h4><fmt:message key="user.account.connect.title"/></h4>
        <p style="margin-top: 15px;"><fmt:message key="account.description.text"/></p>
        <div class="form-check">
            <input type="checkbox" form="signup-form" class="form-check-input" name="connect-account" id="accountConnection">
            <label class="form-check-label" for="accountConnection"><fmt:message key="user.account.connect.label" /></label>
        </div>
        <div class="form-group" style="margin-top: 15px;">
            <label for="account-number-field"><fmt:message key="account.field.number.label" /></label>
            <input type="text" form="signup-form" name="account-number" class="form-control" id="account-number-field" aria-describedby="accountHelp" placeholder="<fmt:message key="account.field.number.placeholder"/>">
            <small id="accountHelp" class="form-text text-muted"><fmt:message key="user.field.name.help" /></small>
        </div>
    </div>
</div>

<c:import url="/jsp/template/footer.jsp"/>