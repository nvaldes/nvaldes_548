package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: Provider
 *
 */
/*
 * DONE
 */

@Entity

@NamedQueries({
	@NamedQuery(
		name="SearchProviderByNPI",
		query="select p from Provider p where p.npi = :npi"),
	@NamedQuery(
		name="CountProviderByNPI",
		query="select count(p) from Provider p where p.npi = :npi"),
	@NamedQuery(
		name = "RemoveAllProviders", 
		query = "delete from Provider p")
})

@Table(name = "PROVIDER")

public class Provider implements Serializable {
	
	@Id
	@GeneratedValue
	private long id;
	
	private long npi;
	
	private String name;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNpi() {
		return npi;
	}

	public void setNpi(long npi) {
		this.npi = npi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Transient
	private ITreatmentDAO treatmentDAO;
	
	public void setTreatmentDAO (ITreatmentDAO tdao) {
		this.treatmentDAO = tdao;
	}

}
