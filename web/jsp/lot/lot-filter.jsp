<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<div class="lot-filter">
    <a href="#" data-filter-term="open" role="button" aria-pressed="true" class="btn btn-outline-cyan"><fmt:message key="lot.show.inscription.status.open" /></a>
    <a href="#" data-filter-term="started" role="button" aria-pressed="true" class="btn btn-outline-primary"><fmt:message key="lot.show.inscription.status.started" /></a>
    <a href="#" data-filter-term="completed" role="button" aria-pressed="true" class="btn btn-outline-teal"><fmt:message key="lot.show.inscription.status.completed" /></a>
    <a href="#" data-filter-term="proposed" role="button" aria-pressed="true" class="btn btn-outline-secondary"><fmt:message key="lot.show.inscription.status.proposed" /></a>
    <a href="#" data-filter-term="closed" role="button" aria-pressed="true" class="btn btn-outline-danger"><fmt:message key="lot.show.inscription.status.closed" /></a>
</div>
