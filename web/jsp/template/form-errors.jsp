<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

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