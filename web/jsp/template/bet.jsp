<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<li class="list-group-item bet prototype bet-prototype">
    <span class="bet-bieter-name" data-label="<fmt:message key="lot.show.inscription.info.owner" />"></span>
    <p class="bet-price"></p>
    <input type="hidden" name="entity-id">
</li>