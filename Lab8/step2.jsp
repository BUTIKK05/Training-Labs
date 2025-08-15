<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Step 2</title></head>
<body>
<h2>Step 2 - Choose Class</h2>
<form action="step2" method="post">
    <select name="characterClass">
        <option>Car</option>
        <option>Glass</option>
        <option>Laptop</option>
    </select>
    <br><br>
    <input type="submit" value="Next">
</form>
<form action="step1.jsp" method="get">
    <input type="submit" value="Back">
</form>
</body>
</html>
