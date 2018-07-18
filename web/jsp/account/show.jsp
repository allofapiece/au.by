<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="localization.local" />

<c:import url="/jsp/template/header.jsp"/>

<div id="content-title" style="margin-top: 15px;">
    <h2 class="col-6"><fmt:message key='${not empty title ? title : "title.default.home"}' /></h2>
    <hr>
</div>
<div class="row">
    <div class="col-6">
        <div class="row">
            <div class="col">
                <p><fmt:message key='account.field.number.label' /></p>
            </div>
            <div class="col">
                <p>${user.account.number}</p>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <p><fmt:message key='account.field.status.label' /></p>
            </div>
            <div class="col">
                <p>${user.account.status}</p>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <p><fmt:message key='account.field.money.label' /></p>
            </div>
            <div class="col">
                <p>${user.account.money}</p>
            </div>
        </div>
    </div>
    <div class="col-6">
    </div>
</div>

<c:import url="/jsp/template/footer.jsp"/>