<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        </div> <!--content div close hear-->
        <footer class="footer">
            <div class="container">
                <span class="text-muted">Created by <a href="https://github.com/allofapiece">allofapiece</a></span>
            </div>
        </footer>
    </div> <!--wrapper div close hear-->

    <script>
        var userId = ${sessionScope.user.id};
    </script>

    <script src="${context}/vendor/jquery/jquery-3.3.1.min.js"></script>
    <script src="${context}/vendor/bootstrap/js/bootstrap.min.js"></script>
    <%--<script src="${context}/vendor/vue.js"></script>--%>
    <script src="${context}/js/main.js"></script>
    <script src="${context}/js/style.js"></script>

    <c:if test="${param.dependency ne null}">
        <script src="${param.dependency}"></script>
    </c:if>

    <script src="${context}/js/product.js"></script>
    <script src="${context}/js/lots.js"></script>
    </body>
</html>
