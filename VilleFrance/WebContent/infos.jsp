<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Météo Villes France</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://unpkg.com/leaflet@1.3.1/dist/leaflet.css"
	integrity="sha512-Rksm5RenBEKSKFjgI3a41vrjkw4EVPlJ3+OiI65vTjIdo9brlAacEuKOiQ5OFh7cOI1bkDwLqdLw3Zg0cRJAAQ=="
	crossorigin="" />
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
			<li class="nav-item"><a class="nav-link" href="ListeVilles">Liste</a>
			</li>
			<li class="nav-item active"><a class="nav-link"
				href="InfosVilles">Informations Par Ville</a></li>
		</ul>
	</div>
	</nav>

	<form method="POST" action="InfosVilles">
		<div class="container" style="margin-top: 100px;">
			<div class="row">
				<div class="col">
					<select name="ville" class="browser-default custom-select">
						<option disabled selected value="none">Sélectionner une
							ville</option>
						<c:forEach var="ville" items="${villes}" varStatus="loop">
							<option
								value="${ville.getLatiString()}/${ville.getLongiString()}/${ville.getNom()}/${ville.getCodeINSEE()}">${ville.getNom()}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col">
					<button type="submit" class="btn btn-info">Afficher</button>
				</div>
			</div>
		</div>
	</form>
	<br>
	<br>
	<div class="container" style="text-align: center;">
		<h2>VILLE</h2>
		<br>
		<div class="row">
			<h5>La ville selectionnée est :</h5>
			<div class="col">
				<input class="form-control" disabled value="${ville}">
			</div>
		</div>
		<br>
		<div class="row">
			<h5>La population de la ville s'élève à :</h5>
			<div class="col">
				<input class="form-control" disabled value="${population}">
			</div>
		</div>
		<br>
		<h2>MÉTÉO</h2>
		<br>
		<div class="row">
			<h5>Le temps est :</h5>
			<div class="col">
				<input class="form-control" disabled value="${w_description}">
			</div>
			<div class="col">
				<img src="icons/${w_icon}.png">
			</div>
		</div>
		<br>
		<div class="row">
			<h5>La température est de :</h5>
			<div class="col">
				<input class="form-control" disabled value="${temp} °C">
			</div>
		</div>
		<br>
		<div class="row">
			<h5>La pression est de :</h5>
			<div class="col">
				<input class="form-control" disabled value="${press} hPa">
			</div>
		</div>
		<br>
		<div class="row">
			<h5>Le taux d'humidité est de :</h5>
			<div class="col">
				<input class="form-control" disabled value="${hum} %">
			</div>
		</div>
		<br>
		<div class="row">
			<h5>Température min :</h5>
			<div class="col">
				<input class="form-control" disabled value="${min} °C">
			</div>
			<h5>Température max :</h5>
			<div class="col">
				<input class="form-control" disabled value="${max} °C">
			</div>
		</div>
	</div>
	<div class="container">
		<div id="map" style="height:400px; margin:25px 0 25px 0; border-radius:10px;"></div>
	</div>

</body>
<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
<script src="https://unpkg.com/leaflet@1.3.1/dist/leaflet.js"
	integrity="sha512-/Nsx9X4HebavoBvEBuyp3I7od5tA0UzAxs+j83KgC8PU0kgB4XiK4Lfe4y4cgBtaRJQEIFCW+oC506aPT2L1zw=="
	crossorigin=""></script>
<script type="text/javascript">
	// On initialise la latitude et la longitude de Paris (centre de la carte)
	var lat = ${lati};
	var lon = ${longi};
	var macarte = null;
	var data = {
			"type" : "Feature",
			"properties" : {
				"style" : {
					weight : 2,
					color : "#999",
					opacity : 1,
					fillColor : "#B0DE5C",
					fillOpacity : 0.8
				}
			},
			"geometry" : {
				"type" : "MultiPolygon",
				"coordinates" : ${poly}
		}
	};
	// Fonction d'initialisation de la carte
	function initMap() {
		// Créer l'objet "macarte" et l'insèrer dans l'élément HTML qui a l'ID "map"
		macarte = L.map('map').setView([ lat, lon ], 7);
		//Ajouter un marker
		var marker = L.marker([lat, lon]).addTo(macarte);
		// Leaflet ne récupère pas les cartes (tiles) sur un serveur par défaut. Nous devons lui préciser où nous souhaitons les récupérer. Ici, openstreetmap.fr
		L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1Ijoicm9tYWluYmVydGhldCIsImEiOiJjanU4dGs0Y3cxNTd4NDVxcjdmbzNuY2o0In0.a5OFqHUFS7zGj2BhBpdgtw', { /* set your personal MapBox Access Token */
			  maxZoom: 20, /* zoom limit of the map */
			  id: 'mapbox.streets' /* mapbox.light / dark / streets / outdoors / satellite */
			}).addTo(macarte);
		L.geoJson(data).addTo(macarte);
	}
	window.onload = function() {
		// Fonction d'initialisation qui s'exécute lorsque le DOM est chargé
		initMap();
	};
</script>
</html>