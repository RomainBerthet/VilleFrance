<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Distance Villes France</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	  <a class="navbar-brand" href="#">Villes France</a>
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	
	  <div class="collapse navbar-collapse" id="navbarSupportedContent">
	    <ul class="navbar-nav mr-auto">
	      <li class="nav-item">
	        <a class="nav-link" href="/VilleFrance/">Accueil <span class="sr-only">(current)</span></a>
	      </li>
	      <li class="nav-item active">
	        <a class="nav-link" href="VilleUtilisation">Calculer Distance</a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="ListeVilles">Liste</a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="InfosVilles">Informations Par Ville</a>
	      </li>
	    </ul>
	  </div>
	</nav>
	
	<form method="POST" action="VilleUtilisation">
		<div class="container" style="margin-top:100px;">
		${villes}
			<div class="row">
				<div class="col">
					<select name="ville1" class="browser-default custom-select">
						<option disabled selected value="none">Sélectionner une ville</option>
						<c:forEach var="ville" items="${villes}" varStatus="loop">
							<option value="${ville.getLatiString()}/${ville.getLongiString()}/${ville.getNom()}">${ville.getNom()}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col">
					<select name="ville2" class="browser-default custom-select">
					<option disabled selected value="none">Sélectionner une ville</option>
						<c:forEach var="ville" items="${villes}" varStatus="loop">
							<option value="${ville.getLatiString()}/${ville.getLongiString()}/${ville.getNom()}">${ville.getNom()}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col">
					<button type="submit" class="btn btn-info">Calculer</button>
				</div>
			</div>
		</div>
	</form>
	<br><br>
	<div class="container" style="text-align:center;">
		<div class="row">
				<h5>La distance entre</h5>
			<div class="col">
				<input class="form-control" disabled value="${ville1}">
			</div>
				<h4>&</h4>
			<div class="col">
				<input class="form-control" disabled value="${ville2}">
			</div>
				<h4>=</h4>
			<div class="col-2">
				<input class="form-control" disabled value="${distance} KM"> 
			</div>
		</div>
	</div>
	
	
</body>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
</html>