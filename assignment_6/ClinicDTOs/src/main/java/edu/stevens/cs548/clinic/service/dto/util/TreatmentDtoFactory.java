package edu.stevens.cs548.clinic.service.dto.util;

import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs548.clinic.domain.RadiologyTreatment;
import edu.stevens.cs548.clinic.domain.SurgeryTreatment;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.RadiologyTreatmentType;
import edu.stevens.cs548.clinic.service.dto.SurgeryTreatmentType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

public class TreatmentDtoFactory {
	
	ObjectFactory factory;
	
	public TreatmentDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public TreatmentDto createDrugTreatmentDto () {
		TreatmentDto dto = factory.createTreatmentDto();
		DrugTreatmentType drugInfo = factory.createDrugTreatmentType();
		dto.setDrugTreatment(drugInfo);
		return dto;
	}
	
	public TreatmentDto createSurgeryTreatmentDto() {
		TreatmentDto dto = factory.createTreatmentDto();
		SurgeryTreatmentType surgeryInfo = factory.createSurgeryTreatmentType();
		dto.setSurgeryTreatment(surgeryInfo);
		return dto;
	}
	
	public TreatmentDto createRadiologyTreatmentDto() {
		TreatmentDto dto = factory.createTreatmentDto();
		RadiologyTreatmentType radiologyInfo = factory.createRadiologyTreatmentType();
		dto.setRadiologyTreatment(radiologyInfo);
		return dto;
	}
	
	public TreatmentDto createTreatmentDto (DrugTreatment t) {
		TreatmentDto dto = factory.createTreatmentDto();
		dto.setDiagnosis(t.getDiagnosis());
		DrugTreatmentType drugInfo = factory.createDrugTreatmentType();
		drugInfo.setDosage(t.getDosage());
		drugInfo.setDrug(t.getDrug());
		dto.setDrugTreatment(drugInfo);
		return dto;
	}
	
	public TreatmentDto createTreatmentDto (SurgeryTreatment t) {
		TreatmentDto dto = factory.createTreatmentDto();
		dto.setDiagnosis(t.getDiagnosis());
		SurgeryTreatmentType surgeryInfo = factory.createSurgeryTreatmentType();
		surgeryInfo.setDate(t.getDate());
		dto.setSurgeryTreatment(surgeryInfo);
		return dto;
	}
	
	public TreatmentDto createTreatmentDto (RadiologyTreatment t) {
		TreatmentDto dto = factory.createTreatmentDto();
		dto.setDiagnosis(t.getDiagnosis());
		RadiologyTreatmentType radiologyInfo = factory.createRadiologyTreatmentType();
		List<Date> datesList = radiologyInfo.getDates();
		for (Date d : t.getDates()) {
			datesList.add(d);
		}
		dto.setRadiologyTreatment(radiologyInfo);
		return dto;
	}

}
