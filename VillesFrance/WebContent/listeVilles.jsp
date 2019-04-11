<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste Villes France</title>
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
			<li class="nav-item"><a class="nav-link" href="/VilleFrance/">Accueil
					<span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item"><a class="nav-link" href="VilleUtilisation">Calculer
					Distance</a></li>
			<li class="nav-item active"><a class="nav-link"
				href="ListeVilles">Liste</a></li>
			<li class="nav-item"><a class="nav-link" href="InfosVilles">Informations Par Ville</a>
			</li>
		</ul>
	</div>
	</nav>
	<div class="container" style="width: 80rem;">
		<div class="card mt-5">
			<div class="card-header">
				<table>
					<tr>
						<td>
							<h2>TOUTES LES VILLES</h2>
						</td>
						<form method="POST" action=ListeVilles>
							<td><button type="submit" name="ajout" class="btn btn-warning">Ajouter</button></td>
						</form>
					</tr>
				</table>
			</div>
			<div class="table-responsive">
				<table id='villes' class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>CODE INSEE</th>
							<th>LIBELLE</th>
							<th>CODE POSTAL</th>
							<th>LATITUDE</th>
							<th>LONGITUDE</th>
							<th></th>
						</tr>
					</thead>

					<c:forEach var="ville" items="${villes}" varStatus="loop">
						<form method="POST" action=ListeVilles>
							<tr>
								<td><input type="hidden" name="insee"
									value="${ville.getCodeINSEE()}">${ville.getCodeINSEE()}</td>
								<td><input type="hidden" name="nom"
									value="${ville.getNom()}">${ville.getNom()}</td>
								<td><input type="hidden" name="cp" value="${ville.getCp()}">${ville.getCp()}</td>
								<td><input type="hidden" name="lati"
									value="${ville.getLati()}">${ville.getLati()}</td>
								<td><input type="hidden" name="longi"
									value="${ville.getLongi()}">${ville.getLongi()}</td>
								<td><button type="submit" name="edit" class="btn btn-info">Editer</button></td>
							</tr>
						</form>
					</c:forEach>
				</table>
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