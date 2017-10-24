package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

public class TreatmentFactory implements ITreatmentFactory {

	@Override
	public DrugTreatment createDrugTreatment(String diagnosis, String drug, float dosage) {
		DrugTreatment treatment = new DrugTreatment();
		treatment.setDiagnosis(diagnosis);
		treatment.setDrug(drug);
		treatment.setDosage(dosage);
		return treatment;
	}
	
	@Override
	public SurgeryTreatment createSurgeryTreatment(String diagnosis, Date date) {
		SurgeryTreatment treatment = new SurgeryTreatment();
		treatment.setDiagnosis(diagnosis);
		treatment.setDate(date);
		return treatment;
	}
	
	@Override
	public RadiologyTreatment createRadiologyTreatment(String diagnosis, List<Date> dates) {
		RadiologyTreatment treatment = new RadiologyTreatment();
		treatment.setDiagnosis(diagnosis);
		treatment.setDates(dates);
		return treatment;
	}


}
