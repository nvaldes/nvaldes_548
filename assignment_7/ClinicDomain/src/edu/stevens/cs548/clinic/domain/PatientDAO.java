package edu.stevens.cs548.clinic.domain;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PatientDAO implements IPatientDAO {

	private EntityManager em;
	private TreatmentDAO treatmentDAO;
	
	public PatientDAO(EntityManager em) {
		this.em = em;
		this.treatmentDAO = new TreatmentDAO(em);
	}

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(PatientDAO.class.getCanonicalName());

	@Override
	public long addPatient(Patient patient) throws PatientExn {
		long pid = patient.getPatientId();
		Query query = em.createNamedQuery("CountPatientByPatientID").setParameter("pid", pid);
		Long numExisting = (Long) query.getSingleResult();
		if (numExisting < 1) {
			// DONE add to database (and sync with database to generate primary key)
			// Don't forget to initialize the patient aggregate with a treatment DAO
			em.persist(patient);
			patient.setTreatmentDAO(this.treatmentDAO);
			em.flush();
			return patient.getId();
						
		} else {
			throw new PatientExn("Insertion: Patient with patient id (" + pid + ") already exists.");
		}
	}

	@Override
	public Patient getPatient(long id) throws PatientExn {
		// DONE retrieve patient using primary key
		Patient p = em.find(Patient.class, id);
		if (p == null) {
			throw new PatientExn("Missing patient: id = " + id);
		} else {
			return p;
		}
	}

	@Override
	public Patient getPatientByPatientId(long pid) throws PatientExn {
		// DONE retrieve patient using query on patient id (secondary key)
		Query query = em.createNamedQuery("SearchPatientByPatientID").setParameter("pid", pid);
		Patient p = (Patient) query.getSingleResult();
		if (p == null) {
			throw new PatientExn("Missing patient: patient id = " + pid);
		} else {
			return p;
		}
	}
	
	@Override
	public void deletePatients() {
		Query update = em.createNamedQuery("RemoveAllPatients");
		update.executeUpdate();
	}

}
