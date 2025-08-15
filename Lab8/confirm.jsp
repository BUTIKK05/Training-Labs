<%@ taglib uri="/WEB-INF/tlds/characterinfo" prefix="ci" %>
<html>
<head>
    <title>Character Confirmation</title>
</head>
<body>
    <h1>Character created!</h1>

    <%
        String name = (String) session.getAttribute("name");
        String clazz = (String) session.getAttribute("class");
        String weapon = (String) session.getAttribute("weapon");
        String createdAt = (String) session.getAttribute("createdAt");
    %>

    <!-- Использование кастомного тега -->
    <ci:characterInfo name="<%= name %>" characterClass="<%= clazz %>" weapon="<%= weapon %>" createdAt="<%= createdAt %>" />

    <hr>
    <form action="step1.jsp" method="get">
        <input type="submit" value="Create Another Character">
    </form>
</body>
</html>
