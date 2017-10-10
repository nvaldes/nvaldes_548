package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@DiscriminatorValue("RA")
public class RadiologyTreatment extends Treatment {
	
	private static final long serialVersionUID = 1L;
	
	@ElementCollection
	@Temporal(TemporalType.DATE)
	public List<Date> dates;

	public List<Date> getDates() {
		return dates;
	}

	public void setDates(List<Date> dates) {
		this.dates = dates;
	}

	@Override
	public <T> T export(ITreatmentExporter<T> visitor) {
		// DONE Auto-generated method stub
		return visitor.exportRadiology(this.getId(), this.getDiagnosis(), this.getDates());
	}
	
	public RadiologyTreatment() {
		super();
		this.setTreatmentType(TreatmentType.RADIOLOGY.getTag());
	}

}
