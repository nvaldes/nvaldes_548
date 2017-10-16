package edu.stevens.cs548.clinic.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.TreatmentExporter;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;

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
	
	// DONE inject an EM
	@PersistenceContext(unitName = "ClinicDomain")
	EntityManager em;

	@PostConstruct
	private void init() {
		/*
		 * Put your testing logic here. Use the logger to display testing output in the server logs.
		 */
		logger.info("Your name here: Noriel Valdes");

		try {

			Calendar calendar = Calendar.getInstance();
			
			IPatientDAO patientDAO = new PatientDAO(em);
			IProviderDAO providerDAO = new ProviderDAO(em);

			PatientFactory patientFactory = new PatientFactory();
			ProviderFactory providerFactory = new ProviderFactory();
			TreatmentFactory treatmentFactory = new TreatmentFactory();
			
			TreatmentExporter treatmentExporter = new TreatmentExporter();
			
			/*
			 * Clear the database and populate with fresh data.
			 * 
			 * If we ensure that deletion of patients cascades deletes of treatments,
			 * then we only need to delete patients.
			 */
			
			patientDAO.deletePatients();
			
			calendar.set(1897, 6, 19);
			Patient moe = patientFactory.createPatient(445829539L, "Moe Howard", calendar.getTime(), 120);
			patientDAO.addPatient(moe);
			
			logger.info("Added "+moe.getName()+" with id "+moe.getId());
			
			calendar.set(1902, 10, 5);
			Patient larry = patientFactory.createPatient(520843229L, "Larry Fine", calendar.getTime(), 114);
			patientDAO.addPatient(larry);
			
			logger.info("Added "+larry.getName()+" with id "+larry.getId());
			
			
			calendar.set(1895, 3, 11);
			Patient shemp = patientFactory.createPatient(694074239L, "Shemp Howard", calendar.getTime(), 122);
			patientDAO.addPatient(shemp);
			
			logger.info("Added "+shemp.getName()+" with id "+shemp.getId());
			
			//PROVIDERS
			
			providerDAO.deleteProviders();
			
			Provider batman = providerFactory.createProvider(8693725428L, "Bruce Wayne");
			providerDAO.addProvider(batman);
			
			logger.info("Added "+batman.getName()+" with id "+batman.getId());
			
			Provider superman = providerFactory.createProvider(7190851008L, "Clark Kent");
			providerDAO.addProvider(superman);
			
			logger.info("Added "+superman.getName()+" with id "+superman.getId());
			
			Provider flash = providerFactory.createProvider(3394230013L, "Bartholomew Allen");
			providerDAO.addProvider(flash);
			
			logger.info("Added "+flash.getName()+" with id "+flash.getId());
			
			//Treatments
			
			long shemps_meds = batman.addDrugTreatment(treatmentFactory.createDrugTreatment("Heart Attack", "Benazepril", 40f), shemp);
			logger.info(shemp.exportTreatment(shemps_meds, treatmentExporter));
			
			long larrys_meds = batman.addDrugTreatment(treatmentFactory.createDrugTreatment("Stroke", "Warfarin", 5f), larry);
			logger.info(larry.exportTreatment(larrys_meds, treatmentExporter));
			
			long moes_meds = batman.addDrugTreatment(treatmentFactory.createDrugTreatment("Lung Cancer", "Methotrexate", 22.5f), moe);
			logger.info(moe.exportTreatment(moes_meds, treatmentExporter));
			
			List<Date> dates = new ArrayList<Date>();
			calendar.set(2017, 11, 2);
			dates.add(calendar.getTime());
			calendar.set(2017, 12, 2);
			dates.add(calendar.getTime());
			calendar.set(2018, 1, 2);
			dates.add(calendar.getTime());
			long moes_xrays = superman.addRadiologyTreatment(treatmentFactory.createRadiologyTreatment("Lung Cancer", dates), moe);
			logger.info(moe.exportTreatment(moes_xrays, treatmentExporter));
			
			calendar.set(2017, 12, 26);
			long shemps_surgery = flash.addSurgeryTreatment(treatmentFactory.createSurgeryTreatment("Heart Attack", calendar.getTime()), shemp);
			logger.info(shemp.exportTreatment(shemps_surgery, treatmentExporter));
						
			// DONE add more testing
			
		} catch (PatientExn e) {

			IllegalStateException ex = new IllegalStateException("Failed to add patient record.");
			ex.initCause(e);
			throw ex;
			
		} catch (ProviderExn e) {

			IllegalStateException ex = new IllegalStateException("Failed to add provider record.");
			ex.initCause(e);
			throw ex;
		} catch (TreatmentExn e) {

			IllegalStateException ex = new IllegalStateException("Failed to add treatment record.");
			ex.initCause(e);
			throw ex;
		} 
			
	}

}
