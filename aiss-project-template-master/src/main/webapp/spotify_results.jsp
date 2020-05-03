<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="es">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Buscar en Spotify</title>
  </head>
  <body>
    <h1>Resultados en Spotify!</h1>
    
    <ul>
    	<c:forEach items="${search_results.playlists.items}" var="r">
            <li>
            	<c:out value="${r.name}"/>
           	</li>
        </c:forEach>	
    </ul>
    
    <p>Aquí se ofrece la búsqueda de canciones, artistas y playlists relacionadas con los eventos. En una ejecución del proyecto conjunto, estos resultados aparecerán sin tener que buscarlos manualmente. Serán los datos que devuelva TicketMaster los que utilizaremos para buscar este contenido.</p>
	<form action="" method="GET">
		<label>
			Nombre del festival, artista, o evento:
			<input type="text" name="spotify_search" />
		</label>
		<button>Buscar los resultados</button>
	</form>
	
	<h2>Añadir canciones seleccionadas a una playlist</h2>
	<p>Tienes que haber <a href="/AuthController/Spotify">iniciado sesión</a> antes. Playlists en tu cuenta:</p>
	<ul>
		<c:forEach items="${user_playlists.items}" var="p">
		<li data-id="${p.id }">
			<c:out value="${p.name}" />
		</li>
		</c:forEach>
	</ul>
	
	<h2>Vídeos de Youtube</h2>
	<div id="results">
	
	</div>
	
	<script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
	<script src="http://apis.google.com/js/client.js?onload=init"></script>
	
	<script>
	function tplawesome(e,t){res=e;for(var n=0;n<t.length;n++){res=res.replace(/\{\{(.*?)\}\}/g,function(e,r){return t[n][r]})}return res}

	$(function() {
	    $("form").on("submit", function(e) {
	       e.preventDefault();
	       // prepare the request
	       var request = gapi.client.youtube.search.list({
	            part: "snippet",
	            type: "video",
	            q: encodeURIComponent($("#spotify_search").val()).replace(/%20/g, "+"),
	            maxResults: 5,
	            order: "relevance",
	            publishedAfter: "2018-01-01T00:00:00Z"
	       }); 

	       // execute the request
	       request.execute(function(response) {
	          var results = response.result;
	          $("#results").html("");
	          $.each(results.items, function(index, item) {
	              //Ruta para encontrar cada item aparecido en la busqueda 
	            $.get("tpl/item.html", function(data) {
	                $("#results").append(tplawesome(data, [{"title":item.snippet.title, "videoid":item.id.videoId}]));
	            });
	          });
	          resetVideoHeight();
	       });
	    });
	    
	    $(window).on("resize", resetVideoHeight);
	});

	function resetVideoHeight() {
	    $(".video").css("height", $("#results").width() * 9/16);
	}

	// Youtube API Key load
	function init() {
	    gapi.client.setApiKey("AIzaSyA1BAe4DlVnhZEVEmO7H4wNNp7MBO1qrkQ");
	    gapi.client.load("youtube", "v3", function() {
	        
	    });
	}
	
	init();
	</script>
  </body>
</html>
