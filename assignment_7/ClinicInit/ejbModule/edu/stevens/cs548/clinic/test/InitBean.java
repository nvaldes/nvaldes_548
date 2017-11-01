package edu.stevens.cs548.clinic.test;

import java.util.Calendar;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.TreatmentDAO;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.ClinicDomain;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;

/**
 * Session Bean implementation class TestBean
 */
@Singleton
@LocalBean
@Startup
public class InitBean {

	private static Logger logger = Logger.getLogger(InitBean.class.getCanonicalName());

	/**
	 * Default constructor.
	 */
	public InitBean() {
	}
    
	@Inject IPatientServiceLocal patientService;
	
	@Inject IProviderServiceLocal providerService;
	
	@PostConstruct
	private void init() {
		/*
		 * Put your testing logic here. Use the logger to display testing output in the server logs.
		 */
		logger.info("Your name here: Noriel Valdes");
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1906, 3, 6);		
				
		ProviderDtoFactory providerDtoFactory = new ProviderDtoFactory();
		
		ProviderDto abbott = providerDtoFactory.createProviderDto();
		abbott.setName("Abbott");
		abbott.setNpi(4924451329L);
		
		try {
			abbott.setId(providerService.addProvider(abbott));
		} catch (ProviderServiceExn e) {
			logger.severe("Failed to add Abbott as a provider: " + e.toString());
		}
		
		logger.info("Added Abbott as a provider with id: " + abbott.getId());
		
		PatientDtoFactory patientDtoFactory = new PatientDtoFactory();
		
		PatientDto costello = patientDtoFactory.createPatientDto();
		costello.setPatientId(528150187L);
		costello.setName("Costello");
		costello.setDob(calendar.getTime());
		costello.setAge(111);
		
		try {
			costello.setId(patientService.addPatient(costello));
		} catch (PatientServiceExn e) {
			logger.severe("Failed to add Costello as a patient: " + e.toString());
		}
		
		logger.info("Added Costello as a patient with id: " + costello.getId());
		
		TreatmentDtoFactory treatmentDtoFactory = new TreatmentDtoFactory();
		
		TreatmentDto drugTreatment = treatmentDtoFactory.createDrugTreatmentDto();
		drugTreatment.setDiagnosis("Dementia");
		DrugTreatmentType dt = drugTreatment.getDrugTreatment();
		dt.setDrug("Aricept");
		dt.setDosage(2.3f);
		drugTreatment.setDrugTreatment(dt);
		drugTreatment.setPatient(costello.getId());
		drugTreatment.setProvider(abbott.getId());
		
		try {
			drugTreatment.setId(providerService.addTreatment(drugTreatment));
		} catch (ProviderServiceExn e) {
			logger.severe("Failed to add drug treatment: " + e.toString());
		}
		
		logger.info("Added drug treatment from Abbott to Costello with id: " + drugTreatment.getId());
	}

}
