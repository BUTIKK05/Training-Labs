<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Step 3</title></head>
<body>
<h2>Step 3 - Choose Weapon</h2>
<form action="step3" method="post">
    <input type="radio" name="weapon" value="Apple">Sword<br>
    <input type="radio" name="weapon" value="Pen">Staff<br>
    <input type="radio" name="weapon" value="Paper">Bow<br>
    <br>
    <input type="submit" value="Finish">
</form>
<form action="step2.jsp" method="get">
    <input type="submit" value="Back">
</form>
</body>
</html>
