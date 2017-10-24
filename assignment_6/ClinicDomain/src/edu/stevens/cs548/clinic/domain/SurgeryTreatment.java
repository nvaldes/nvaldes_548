package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.CascadeOnDelete;


@Entity
@DiscriminatorValue("SU")
@CascadeOnDelete

public class SurgeryTreatment extends Treatment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	

	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	@Override
	public <T> T export(ITreatmentExporter<T> visitor) {
		return visitor.exportSurgery(this.getId(), this.getDiagnosis(), this.getDate());
	}
	
	public SurgeryTreatment() {
		super();
		this.setTreatmentType(TreatmentType.SURGERY.getTag());
	}

}
