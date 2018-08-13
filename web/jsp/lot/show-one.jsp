<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="localization.local"/>

<c:import url="/jsp/template/header.jsp"/>

<div id="content-title" style="margin-top: 15px;">
    <h2>${lot.name}</h2>
    <hr>
</div>
<div class="row">
    <div class="container-fluid">

    </div>
</div>

<div class="row" style="margin-top: 15px;">
    <div class="col-6 lot-main">
        <p class="card-text lot-type"><fmt:message key="lot.field.type.label" />: ${lot.auctionType}</p>
        <p class="card-text lot-amount"><fmt:message key="lot.field.amount.label" />: ${lot.productAmount}</p>
        <p class="card-text lot-begin-price"><fmt:message key="lot.field.begin-price.label" />: ${lot.beginPrice}</p>
        <p class="card-text lot-description"><fmt:message key="lot.field.description.label" />: ${lot.description}</p>
        <p class="card-text"><fmt:message key="lot.field.seller-name.label" />: <a class="lot-seller-name">Stas</a></p>

        <c:choose>
            <c:when test="${lot.auctionType == 'BLITZ'}">
                <p class="card-text lot-round-amount"><fmt:message key="lot.field.round.amount.label" />: ${lot.roundAmount}</p>
                <p class="card-text lot-round-time"><fmt:message key="lot.field.round.time.label" />: ${lot.roundTime}</p>
                <p class="card-text lot-outgoing"><fmt:message key="lot.field.outgoing.label" />: ${lot.outgoingAmount}</p>
            </c:when>
        </c:choose>
        <div class="lot-actions">
            <c:choose>
                <c:when test="${lot.status == 'OPEN'}">
                    <c:if test="${sessionScope.user.id == lot.sellerId}">
                        <a class="btn btn-outline-cyan action-cancel lot-action lot-open" href="#"><fmt:message key="lot.inscription.action.cancel" /></a>
                    </c:if>
                    <c:if test="${sessionScope.user.id != lot.sellerId}">
                        <a class="btn btn-outline-cyan action-play lot-action lot-open" href="<c:url value=""/> " ><fmt:message key="lot.inscription.action.play" /></a>
                    </c:if>
                </c:when>

                <c:when test="${lot.status == 'COMPLETED'}">
                    <c:if test="${sessionScope.user.id == lot.sellerId}">
                        <a class="btn btn-outline-teal action-take-winnings lot-action lot-completed" href="#"><fmt:message key="lot.inscription.action.take-winnings" /></a>
                    </c:if>
                </c:when>

                <c:when test="${lot.status == 'PROPOSED'}">
                    <c:if test="${sessionScope.user.id == lot.sellerId}">
                        <a class="btn btn-outline-cyan action-cancel lot-action lot-proposed" href="#"><fmt:message key="lot.inscription.action.cancel" /></a>
                    </c:if>
                </c:when>

                <c:when test="${lot.status == 'CLOSED'}">
                    <c:if test="${sessionScope.user.id == lot.sellerId}">
                        <a class="btn btn-outline-danger lot-action lot-closed" href="<c:url value=""/> " ><fmt:message key="lot.inscription.action.take-back" /></a>
                    </c:if>
                </c:when>

                <c:when test="${lot.status == 'STARTED'}">
                    <c:if test="${sessionScope.user.id != lot.sellerId}">

                    </c:if>
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="col-3 lot-side-section lot-bets">
        <div class="container-fluid side-title">Bets</div>
    </div>
    <div class="col-3 lot-side-section lot-members">
        <div class="container-fluid side-title">Members</div>
    </div>
</div>

<script src="${context}/js/bets.js"></script>

<c:import url="/jsp/template/footer.jsp">
    <c:param name="dependency" value="${context}/js/bets.js"/>
</c:import>