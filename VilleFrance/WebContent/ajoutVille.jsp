<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Modifier Ville France</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark"> <a
		class="navbar-brand" href="#">Villes France</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarSupportedContent"
		aria-controls="navbarSupportedContent" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item"><a class="nav-link"
				href="/VilleFrance/">Accueil <span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item">
	        <a class="nav-link" href="VilleUtilisation">Calculer Distance</a>
	      </li>
			<li class="nav-item active"><a class="nav-link" href="ListeVilles">Liste</a></li>
			<li class="nav-item">
	        <a class="nav-link" href="InfosVilles">Informations Par Ville</a>
	      </li>
		</ul>
	</div>
	</nav>

	<form method="POST" action="AjoutVille">
		<div class="container" style="margin-top:25px; width: 80rem;">
		<div class="form-group">
				<label>INSEE</label> 
				<input type="text" class="form-control" name="insee" value="${insee}">
			</div>
			<div class="form-group">
				<label>Nom</label> 
				<input type="text" class="form-control" name="nom" value="${nom}">
			</div>
			<div class="form-group">
				<label>Code Postal</label> 
				<input type="text" class="form-control" name="cp" value="${cp}">
			</div>
			<div class="form-group">
				<label>Latitude</label> 
				<input type="text" class="form-control" name="lati" value="${lati}">
			</div>
			<div class="form-group">
				<label>Longitude</label> 
				<input type="text" class="form-control" name="longi" value="${longi}">
			</div>
			<button type="submit" class="btn btn-primary">Ajouter</button>
		</div>
	</form>

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