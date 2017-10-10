package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

public interface ITreatmentFactory {
	
	public Treatment createDrugTreatment (String diagnosis, String drug, float dosage);
	
	public Treatment createSurgeryTreatment (String diagnosis, Date date);
	
	public Treatment createRadiologyTreatment(String diagnosis, List<Date> dates);
	
	/*
	 * DONE add methods for Radiology, Surgery
	 */

}
