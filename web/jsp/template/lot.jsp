<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<div class="card lot prototype lot-prototype border-teal" style="/*max-width: 18rem;*/ display: none;">
    <%--<div class="card-header proposed-status bg-info text-white"><small><fmt:message key="lot.show.inscription.status.proposed" /></small></div>--%>
    <div class="card-header lot-open bg-cyan"><small><fmt:message key="lot.show.inscription.status.open" /></small></div>
    <div class="card-header lot-started bg-primary"><small><fmt:message key="lot.show.inscription.status.started" /></small></div>
    <div class="card-header lot-closed bg-danger"><small><fmt:message key="lot.show.inscription.status.closed" /></small></div>
    <div class="card-header lot-proposed bg-secondary"><small><fmt:message key="lot.show.inscription.status.proposed" /></small></div>
    <div class="card-header lot-completed bg-teal"><small><fmt:message key="lot.show.inscription.status.completed" /></small></div>
    <div class="card-body">
        <h5 class="card-title"></h5>
        <p class="card-text lot-type" data-label="<fmt:message key="lot.field.type.label" />"></p>
        <p class="card-text lot-amount" data-label="<fmt:message key="lot.field.amount.label" />"></p>
        <p class="card-text lot-begin-price" data-label="<fmt:message key="lot.field.begin-price.label" />"></p>
        <p class="card-text lot-description" data-label="<fmt:message key="lot.field.description.label" />"></p>
        <p class="card-text"><a class="lot-seller-name" data-label="<fmt:message key="lot.field.seller-name.label" />"></a></p>

        <%--<a class="btn btn-outline-danger action-take-back lot-action lot-closed" href="<c:url value="/fc?command=lot-take-back&id=#"/> " ><fmt:message key="lot.inscription.action.take-back" /></a>--%>
        <a class="btn btn-outline-primary lot-action lot-started" href="<c:url value="/fc?command=lot-show-one&id=#"/> " ><fmt:message key="lot.inscription.action.watch" /></a>
        <a class="btn btn-outline-cyan lot-action lot-open" href="<c:url value="/fc?command=lot-show-one&id=#"/> " ><fmt:message key="lot.inscription.action.watch" /></a>
        <a class="btn btn-outline-cyan action-play lot-action lot-open" href="<c:url value="/fc?command=bieter-add&id=#"/> " ><fmt:message key="lot.inscription.action.play" /></a>
        <a class="btn btn-outline-cyan action-cancel lot-action lot-open" href="<c:url value="/fc?command=lot-cancel&id=#"/> "><fmt:message key="lot.inscription.action.cancel" /></a>
        <a class="btn btn-outline-secondary action-cancel lot-action lot-proposed" href="<c:url value="/fc?command=lot-cancel&id=#"/> "><fmt:message key="lot.inscription.action.cancel" /></a>
        <a class="btn btn-outline-cyan action-accept lot-action lot-proposed" href="<c:url value="/fc?command=lot-accept&id=#"/> "><fmt:message key="lot.inscription.action.accept" /></a>
        <a class="btn btn-outline-danger action-reject lot-action lot-proposed" href="<c:url value="/fc?command=lot-cancel&id=#"/> "><fmt:message key="lot.inscription.action.reject" /></a>
        <a class="btn btn-outline-teal action-take-winnings lot-action lot-completed" href="<c:url value="/fc?command=lot-take-winnings&id=#"/> "><fmt:message key="lot.inscription.action.take-winnings" /></a>
        <%--TODO accept, reject buttons for proposed lots for admin--%>
        <input type="hidden" name="lot-id">
    </div>
    <div class="card-footer bg-transparent lot-open"
         data-label-full="<fmt:message key="lot.show.inscription.timer.open.full" />"
         data-label-short="<fmt:message key="lot.show.inscription.timer.open.short" />">
        <c:import url="/jsp/template/timer.jsp"/>
    </div>
        <div class="card-footer bg-transparent lot-closed"
             data-label-full="<fmt:message key="lot.show.inscription.timer.closed.full" />"
             data-label-short="<fmt:message key="lot.show.inscription.timer.closed.short" />">
        <c:import url="/jsp/template/timer.jsp"/>
    </div>
        <div class="card-footer bg-transparent lot-started"
             data-label-full="<fmt:message key="lot.show.inscription.timer.started.full" />"
             data-label-short="<fmt:message key="lot.show.inscription.timer.started.short" />">
            <c:import url="/jsp/template/timer.jsp"/>
        </div>
        <div class="card-footer bg-transparent lot-completed"
             data-label-full="<fmt:message key="lot.show.inscription.timer.open.full" />"
             data-label-short="<fmt:message key="lot.show.inscription.timer.open.short" />">
            <c:import url="/jsp/template/timer.jsp"/>
        </div>
        <div class="card-footer bg-transparent lot-proposed"
             data-label-full="<fmt:message key="lot.show.inscription.timer.proposed.full" />"
             data-label-short="<fmt:message key="lot.show.inscription.timer.proposed.short" />">
            <c:import url="/jsp/template/timer.jsp"/>
        </div>
</div>