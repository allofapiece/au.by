<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="localization.local" />

<c:import url="/jsp/template/header.jsp"/>

<div class="row">
    <div class="col-12">
        <c:forEach items="${errors}" var="field">
            <c:if test="${field.key == 'signin.page'}">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <c:forEach items="${field.value}" var="warn">
                        <p><fmt:message key='${field.key}.${warn}.message' /></p>
                    </c:forEach>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <c:set target="${errors}" property="signup.page" value="${null}"/>
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
        <form action="<c:url value="/fc?command=signin"/>" method="post">
            <c:if test="${errors ne null and not empty errors}">
                <div class="alert alert-danger" role="alert">
                    <h5 class="alert-heading"><fmt:message key="signup.error.form.summary.title" /></h5>
                    <c:forEach items="${errors}" var="field">
                        <h6 class="alert-heading"><fmt:message key='${field.key}.label' /></h6>
                        <c:forEach items="${field.value}" var="error">
                            <p>${error}</p>
                        </c:forEach>
                    </c:forEach>
                </div>
            </c:if>
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
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="signin.submit.label" /></button>
        </form>
    </div>
</div>
<c:import url="/jsp/template/footer.jsp"/>