<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/jsp/template/header.jsp"/>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<div id="content-title" style="margin-top: 15px;">
    <h2>${lot.name}</h2>
    <hr>
</div>
<div class="row">
    <div class="container-fluid">

    </div>
</div>

<div class="row" style="margin-top: 15px;">
    <div class="col-5 lot-main">
        <p class="card-text lot-type"><fmt:message key="lot.field.type.label" />: ${lot.auctionType}</p>
        <p class="card-text lot-amount"><fmt:message key="lot.field.amount.label" />: ${lot.productAmount}</p>
        <p class="card-text lot-begin-price"><fmt:message key="lot.field.begin-price.label" />: ${lot.beginPrice}$</p>
        <c:if test="${lot.description ne null}">
            <p class="card-text lot-description"><fmt:message key="lot.field.description.label" />: ${lot.description}</p>
        </c:if>
        <p class="card-text lot-start-time"><fmt:message key="lot.field.start-time.label" />: ${lot.startTime}</p>
        <c:if test="${lot.endTime ne null}">
            <p class="card-text lot-end-time"><fmt:message key="lot.field.end-time.label" />: ${lot.endTime}</p>
        </c:if>
        <p class="card-text"><fmt:message key="lot.field.seller-name.label" />: <a class="lot-seller-name">${seller.name}</a></p>
        <p class="card-text"><fmt:message key="lot.field.status.label" />: <a class="lot-seller-name">${lot.status}</a></p>

        <c:choose>
            <c:when test="${lot.auctionType == 'BLITZ'}">
                <p class="card-text lot-round-amount"><fmt:message key="lot.field.round.amount.label" />: ${lot.roundAmount}</p>
                <p class="card-text lot-round-time"><fmt:message key="lot.field.round.time.label" />: ${lot.roundTime} <fmt:message key="unit.time.seconds.short" /></p>
                <p class="card-text lot-outgoing"><fmt:message key="lot.field.outgoing.label" />: ${lot.outgoingAmount}</p>
            </c:when>
        </c:choose>
        <div class="lot-actions">
            <c:choose>
                <c:when test="${lot.status == 'OPEN'}">
                    <c:if test="${sessionScope.user.id == lot.sellerId}">
                        <a class="btn btn-outline-cyan action-cancel lot-action lot-open" href="#"><fmt:message key="lot.inscription.action.cancel" /></a>
                    </c:if>
                    <c:if test="${sessionScope.user.id != lot.sellerId and isBieter != true}">
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
    <div class="col-4 lot-side-section lot-bets">
        <div class="container-fluid side-title"><fmt:message key="lot.show.inscription.title.bets" /></div>
        <c:if test="${lot.status == 'STARTED'}">
            <p class="bet-time" data-label-short="<fmt:message key="lot.show.inscription.timer.bet-time.short" />" style="margin-top: 10px;"></p>
        </c:if>
        <ul class="list-group bets-list">
            <c:import url="/jsp/template/bet.jsp"/>
        </ul>

        <c:if test="${sessionScope.user.id != lot.sellerId and isBieter and lot.status == 'STARTED'}">
            <form action="#" id="do-bet-form" style="margin-top: 15px;">
                <div class="form-group" style="margin-bottom:5px;">
                    <label for="bet-field"><fmt:message key="lot.show.inscription.bet.title" /></label>
                    <input value="1" type="number" name="bet" class="form-control" id="bet-field" placeholder="<fmt:message key="lot.field.description.placeholder" />">
                </div>
                <div style="float: right;">
                    <a href="#" id="make-bet" role="button" aria-pressed="true" class="btn btn-outline-teal"><fmt:message key="lot.show.inscription.bet.make" /></a>
                    <c:if test="${lot.auctionType != 'ENGLISH' and lot.blitzPrice ne null and lot.status == 'STARTED'}">
                        <a href="#" id="redeem" role="button" aria-pressed="true" class="btn btn-outline-teal"><fmt:message key="lot.show.inscription.bet.redeem" /> ${lot.blitzPrice}$</a>
                    </c:if>

                </div>
            </form>
        </c:if>
    </div>
    <div class="col-3 lot-side-section lot-members">
        <div class="container-fluid side-title"><fmt:message key="lot.show.inscription.title.members" /></div>
        <ul class="list-group members-list">
            <c:import url="/jsp/template/bieter.jsp"/>
        </ul>
    </div>
</div>

<script>
    var lotStatus = '${lot.status}';
    var lotType = '${lot.auctionType}';
    var lotStartTime = '${lot.startTime}';
    var lotUpdateTime = '${lot.updateAt}';
    var lotBetTime = ${lot.betTime};
</script>

<script src="${context}/js/bets.js"></script>

<c:import url="/jsp/template/footer.jsp">
    <c:param name="dependency" value="${context}/js/bets.js"/>
</c:import>