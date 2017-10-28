package edu.stevens.cs548.clinic.service.dto.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;

public class PatientDtoFactory {
	
	ObjectFactory factory;
	
	public PatientDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public PatientDto createPatientDto () {
		return factory.createPatientDto();
	}
	
	public PatientDto createPatientDto (Patient p) {
		PatientDto d = factory.createPatientDto();
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		d.setId(p.getId());
		d.setDob(p.getBirthDate());
		d.setAge(thisYear - p.getBirthDate().getYear());
		d.setName(p.getName());
		d.setPatientId(p.getPatientId());
		List<Long> treatments = d.getTreatments();
		// TODO how to get list of treatment IDs?
		return d;
	}

}
