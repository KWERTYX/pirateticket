package aiss.model;

public class Event {

	private String id;
	private String artista;
	private Long fecha;
	private Double precio;
	private String lugar;
	
	public Event() {
		// TODO Auto-generated constructor stub
	}
	
	public Event(String artista, Long fecha, String lugar) {
		this.artista = artista;
		this.fecha = fecha;
		this.lugar = lugar;
	}
	
	public Event(String id,String artista, Long fecha, String lugar, Double precio) {
		this.id = id;
		this.artista = artista;
		this.fecha = fecha;
		this.lugar = lugar;
		this.precio = precio;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArtista() {
		return artista;
	}
	public void setArtista(String artista) {
		this.artista = artista;
	}
	public Long getFecha() {
		return fecha;
	}
	public void setFecha(Long fecha) {
		this.fecha = fecha;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	
}
