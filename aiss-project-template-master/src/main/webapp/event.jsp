

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <link rel="stylesheet" type="text/css" href="css/ticketMasterListStyle.css">
      <link rel="stylesheet" href="css/bootstrap.css">
      <link rel="stylesheet" href="css/estilos.css">
      <title>Evento ${event.name} en PirateTicket</title>
   </head>
   <body>
      <header>
         <div class="jumbotron jumbotron-fluid" style="background-image: url('${event.images[0].url}')">
            <div class="container">
               <h1 class="display-4">${event.name}</h1>
               <p>El evento se celebra el día ${event.dates.start.localDate}.</p>
            </div>
         </div>
      </header>
      <div class="container">
         <div class="row">
            <div class="col-md-8">
               <h2>Información</h2>
               <p>${event.info}</p>
               <h3>
               Información adicional</h2>
               <p>${event.aditionalInfo}</p>
               <h3>Descripción</h3>
               <p>${event.description}</p>
               <div id="youtube">
	               <h2>Vídeos de Youtube</h2>
	               <div id="results">
	               </div>
               </div>
            </div>
            <div class="col-md-4" id="">
               <div class="widget">
                  <h3>Playlists en Spotify!</h3>
                  <c:choose>
                     <c:when test="${spotify_connected==false}">
                        <p>
                           Debes iniciar sesión en Spotify para poder ver playlists de este evento
                           <a href="/AuthController/Spotify"><button type="button" class="btn btn-primary">Iniciar sesión en Spotify</button></a>
                     </c:when>
                     <c:otherwise>
                     <ul id="spotify_playlists">
                     <c:forEach items="${search_results.playlists.items}" var="r">
                     <li data-id="${r.id}">
                     <c:out value="${r.name}"/>
                     </li>
                     </c:forEach>
                     </ul>
                     <button id="add_spotify">Añadir a Spotify</button>
                     </c:otherwise>
                  </c:choose>
               </div>
               <div class="widget">
                  <h3>Mis playlists!</h3>
                  <c:choose>
                     <c:when test="${spotify_connected==false}">
                        <p>
                           Debes iniciar sesión en Spotify para poder ver playlists de este evento
                           <a href="/AuthController/Spotify"><button type="button" class="btn btn-primary">Iniciar sesión en Spotify</button></a>
                     </c:when>
                     <c:otherwise>
                     <ul id="my_spotify_playlists">
                     <c:forEach items="${user_playlists.items}" var="r">
                     <li data-id="${r.id}">
                     <c:out value="${r.name}"/>
                     </li>
                     </c:forEach>
                     </ul>
                     <button id="remove_spotify">Eliminar de spotify</button>
                     </c:otherwise>
                  </c:choose>
               </div>
            </div>
         </div>
      </div>
      <footer id="footer-main">
         <div class="container">
            <div class="row">
               <div class="col-sm-3">
                  <p>Creado por Pirate Ticket</p>
                  <p>
                     desarrollo <a href="#">Pirate Ticket</a>
                  </p>
               </div>
               <div class="col-sm-3">
                  <ul class="list-unstyled">
                     <li><a href="">Inicio</a></li>
                     <li><a href="">Acerca de</a></li>
                     <li><a href="">Pirate Ticket</a></li>
                     <li><a href="">Últimas noticias</a></li>
                  </ul>
               </div>
               <div class="col-sm-3">
                  <ul class="list-unstyled">
                     <li><a href="">facebook</a></li>
                     <li><a href="">twitter</a></li>
                     <li><a href="">youtube</a></li>
                     <li><a href="">linkedin</a></li>
                  </ul>
               </div>
               <div class="col-sm-3">
                  <h6>Info</h6>
                  <p>La web está en desarrollo</p>
               </div>
            </div>
         </div>
      </footer>
      <div class="loading" style="z-index: 9999; display: none; top: 0; left: 0; position:fixed; height: 100%; width: 100%; background: #ffffff80; color: #000;">
         <div style="display: table-cell; vertical-align: middle;">
            <div style="margin: auto; width: 80%; text-align: center;">Cargando...</div>
         </div>
      </div>
      
            <style>
         #footer-main {
         position: initial;
         margin-top: 100px;
         }
         #spotify_playlists > li.selected, #my_spotify_playlists > li.selected {
         background: green;
         color: #fff;
         }
         #youtube .item h2 {
         font-size: 18px;
         }
      </style>
      <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
      <script>
         $('#spotify_playlists > li, #my_spotify_playlists > li').click(function() {
         	$(this).toggleClass("selected");
         });
         $('#add_spotify').click(() => {
          	let selected = $('#spotify_playlists > li.selected');
          	$('.loading').css("display", "table");
          	if(selected.length) {
          		let playlists = [];
          		console.log(selected);
          		for(let s=0;s<selected.length;s++) {
          			playlists.push($(selected[s]).attr('data-id'));
          		}
          		playlists = playlists.join(",");
          		console.log(playlists);
          		$.ajax({
          			url: '/spotify',
          			data: {
          				action: 'addPlaylists',
          				playlists: playlists
          			},
          			method: 'post',
          			success: function(res) {
          				let myPlaylists = $('#my_spotify_playlists');
          				myPlaylists.html('');
          				res.data = JSON.parse(res.data);
          				console.log(res);
          				for(let i=0;i<res.data.length;i++) {
          					$('<li>'+res.data[i]+'</li>').appendTo(myPlaylists).click(function() {
          			         	$(this).toggleClass("selected");
          			         });
          				}
          				console.log(res["ok"]);
          				$('.loading').hide();
          				alert(res.ok==="true" ? 'Las listas se han importado correctamente' : 'Las listas no se han podido importar');
          			},
          			error: function(err) {
          				$('.loading').hide();
          				console.log(err);
          			}
          		});
          	} else {
          		alert("Tienes que seleccionar al menos una playlist");
          	}
          });
         $('#remove_spotify').click(() => {
         	let selected = $('#my_spotify_playlists > li.selected');
         	$('.loading').css("display", "table");
         	if(selected.length) {
         		let playlists = [];
         		for(let s=0;s<selected.length;s++) {
         			playlists.push($(selected[s]).attr('data-id'));
         		}
         		playlists = playlists.join(",");
         		$.ajax({
         			url: '/spotify',
         			data: {
         				action: 'removePlaylists',
         				playlists: playlists
         			},
         			method: 'post',
         			success: function(res) {
         				let myPlaylists = $('#my_spotify_playlists');
         				myPlaylists.html('');
         				res.data = JSON.parse(res.data);
         				for(let i=0;i<res.data.length;i++) {
         					$('<li>'+res.data[i]+'</li>').appendTo(myPlaylists).click(function() {
         			         	$(this).toggleClass("selected");
         			         });
         				}
         				console.log(res["ok"]);
         				$('.loading').hide();
         				alert(res.ok==="true" ? 'Las listas se han eliminado correctamente' : 'Las listas no se han podido eliminar');
         			},
         			error: function(err) {
         				$('.loading').hide();
         				console.log(err);
         			}
         		});
         	} else {
         		alert("Tienes que seleccionar al menos una playlist");
         	}
         });
      </script>
        
               <script>
                  function tplawesome(e,t){res=e;for(var n=0;n<t.length;n++){res=res.replace(/\{\{(.*?)\}\}/g,function(e,r){return t[n][r]})}return res}
                  
                  function resetVideoHeight() {
                      $(".video").css("height", $("#results").width() * 9/16);
                  }
                  
                  function init() {
	                  gapi.client.setApiKey("AIzaSyC3CAyKg75Mfb4PAIld6QjKA5qAgQ1gkNg");
	                  gapi.client.load("youtube", "v3", function() {
	                      // yt api is ready
	                      
	                	// prepare the request
	                         var request = gapi.client.youtube.search.list({
	                              part: "snippet",
	                              type: "video",
	                              q: encodeURIComponent($(".jumbotron h1").text()).replace(/%20/g, "+"),
	                              maxResults: 5,
	                              order: "relevance"
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
	                      
	                      $(window).on("resize", resetVideoHeight);
	                  });
                  }
               </script>
               <script src="https://apis.google.com/js/client.js?onload=init"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb"crossorigin="anonymous"></script>
      <script src="js/bootstrap.min.js"></script>
   </body>
</html>

