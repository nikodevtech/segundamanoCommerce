package com.market.modelo;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Clase DAO (Data Access Object) que representa una compra.
 * Relación: Una Compra es realizada por un único usuario 
 * 			 y un Usuario puede ser propietario de múltiples compras
 */
@Entity
public class Compra {
	
	//Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCompra;
	
	@ManyToOne
	private Usuario propietarioDeLaCompra; 
	
	//Contructores
	public Compra() {
		
	}

	public Compra(Usuario usarioQueCompra) {
		super();
		propietarioDeLaCompra = usarioQueCompra;
	}

	//Getters y Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public Usuario getPropietarioDeLaCompra() {
		return propietarioDeLaCompra;
	}

	public void setPropietarioDeLaCompra(Usuario usarioQueCompra) {
		propietarioDeLaCompra = usarioQueCompra;
	}

	
	//Metodos
	@Override
	public int hashCode() {
		return Objects.hash(fechaCompra, id, propietarioDeLaCompra);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compra other = (Compra) obj;
		return Objects.equals(fechaCompra, other.fechaCompra) && id == other.id
				&& Objects.equals(propietarioDeLaCompra, other.propietarioDeLaCompra);
	}

	@Override
	public String toString() {
		return "Compra [id=" + id + ", fechaCompra=" + fechaCompra + ", propietario=" + propietarioDeLaCompra + "]";
	}
	

}
