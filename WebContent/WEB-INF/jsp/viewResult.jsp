<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div id="result">
</div>
	<!-- <table id="result">
		<tr>
			<th>id</th>
			<th>name</th>
			<th>country</th>
			<th>city</th>
			<th>street</th>
			<th>bld</th>
			<th>app</th>
		</tr>
	</table> -->
	<br><br><br>
	<a href="home">back to home</a>
	
	<script type="text/javascript">
		var respons = '${json}';
		/* for (var i = 0; i < persons.length; i++) { */
			var table = document.getElementById("result");
			table.innerHTML = table;
/* 			var row = table.insertRow(i + 1);
 */			/* var cellId = row.insertCell(0);
			var cellName = row.insertCell(1);
			var cellCountry = row.insertCell(2);
			var cellCity = row.insertCell(3);
			var cellStreet = row.insertCell(4);
			var cellBld = row.insertCell(5);
			var cellApp = row.insertCell(6);
			cellId.innerHTML = persons[i].id;
			cellName.innerHTML = persons[i].name;
			cellStreet.innerHTML = persons[i].address.street;
			cellApp.innerHTML = persons[i].address.app;
			cellCity.innerHTML = persons[i].address.city;
			cellCountry.innerHTML = persons[i].address.country;
			cellBld.innerHTML = persons[i].address.bld;} */
		
	</script>
</body>
</html>