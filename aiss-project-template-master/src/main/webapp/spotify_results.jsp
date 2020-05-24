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
	
	
  </body>
</html>
