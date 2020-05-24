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
<title>Buscando: ${busqueda}</title>
</head>
<body>
<nav class="navbar navbar-toggleable-md navbar-inverse bg-inverse">
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="#">Buscando: <b>${busqueda}</b></a>
            </li>
            </ul>
        </div>
    </nav>
	<div class="container">
		<div class="row" id="ticketmaster">
			<ul class="list-group">
				<c:forEach items="${requestScope.events}" var="event">
					<li class="list-group-item">
						<div class="container tm-event">
							<div class="row">
								<div class="tk-event-date col-md-2 col-lg-2">
									<div class="row">
										<div class="col-md-12 col-lg-12">
											<c:out value="${event.dates.start.localDate}" />
										</div>
										<div class="col-md-12 col-lg-12">
											<c:out value="${event.dates.start.localTime}" />
										</div>
									</div>
								</div>
								<div class="tk-event-general col-md-8 col-lg-8">
									<div class="row">
										<div class="col-md-12 col-lg-12">
											<c:out value="${event.name}" />
										</div>
										<c:if test="${event._embedded.venues != null}">
											<div class="col-md-12 col-lg-12">
												<c:out value="${event._embedded.venues[0].name}" /> - <c:out value="${event._embedded.venues[0].city.name}" />
											</div>
											<div class="col-md-12 col-lg-12">
												<c:out value="${event._embedded.venues[0].state.name}" /> - <c:out value="${event._embedded.venues[0].country.name}" />
											</div>										
											<div class="col-md-12 col-lg-12">
												<c:out value="${event._embedded.venues[0].address.line1}" />
											</div>
										</c:if>								
									</div>
								</div>
								<div class="tk-event-button col-md-2 col-lg-2">
									<a href=<c:out value="/event?id=${event.id}"/>><button type="button"
											class="btn btn-primary">Detalles</button></a>
								</div>
							</div>
						</div>
					</li>
				</c:forEach>
			</ul>
			<nav aria-label="Page navigation example">
				<ul class="pagination">
					<li class="page-item">
					<form id="searchForm" action="TicketMasterPaginationController" method="post">
                            <input type="hidden" name="url" value="${requestScope.links.prev.href}" >
       						<button type="submit" class="page-link" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
							<span class="sr-only">Previous</span>
						</button>
                    </form>
					</li>
					<li class="page-item">
					<form id="searchForm" action="event_list_pag" method="post">
						<input type="hidden" name="url" value="${requestScope.links.next.href}" >
						<button type="submit" class="page-link" aria-label="Next">
							<span aria-hidden="true">&raquo;</span> 
							<span class="sr-only">Next</span>
						</button>
					 </form>
					</li>
				</ul>
			</nav>
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

	<!-- jQuery first, then Bootstrap JS. -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb"crossorigin="anonymous"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>