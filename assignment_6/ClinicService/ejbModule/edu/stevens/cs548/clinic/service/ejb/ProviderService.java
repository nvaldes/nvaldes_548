package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.IProviderFactory;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.ITreatmentExporter;
import edu.stevens.cs548.clinic.domain.ITreatmentFactory;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;
import edu.stevens.cs548.clinic.service.dto.*;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;

/**
 * Session Bean implementation class ProviderService
 */
@Stateless(name="ProviderServiceBean")
public class ProviderService implements IProviderService,
		IProviderServiceLocal,
		IProviderServiceRemote {
	
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(ProviderService.class.getCanonicalName());

	private IProviderFactory providerFactory;
	
	private ITreatmentFactory treatmentFactory;
	
	private ProviderDtoFactory providerDtoFactory;

	private IProviderDAO providerDAO;
	
	private IPatientDAO patientDAO;

	/**
	 * Default constructor.
	 */
	public ProviderService() {
		this.providerDtoFactory = new ProviderDtoFactory();
		this.providerFactory = new ProviderFactory();
		this.treatmentFactory = new TreatmentFactory();
	}
		
	@PersistenceContext(unitName="ClinicDomain")
	private EntityManager em;
	
	@PostConstruct
	private void initialize() {
		this.providerDAO = new ProviderDAO(em);
		this.patientDAO = new PatientDAO(em);
	}

	/**
	 * @see IProviderService#addProvider(String, Date, long)
	 */
	@Override
	public long addProvider(ProviderDto dto) throws ProviderServiceExn {
		// Use factory to create provider entity, and persist with DAO
		try {
			Provider provider = providerFactory.createProvider(dto.getNpi(), dto.getName());
			providerDAO.addProvider(provider);
			return provider.getId();
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

	/**
	 * @see IProviderService#getProvider(long)
	 */
	@Override
	public ProviderDto getProvider(long id) throws ProviderServiceExn {
		try {
			return this.providerDtoFactory.createProviderDto(this.providerDAO.getProvider(id));
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.getMessage());
		}
	}

	/**
	 * @see IProviderService#getProviderByNpi(long)
	 */
	@Override
	public ProviderDto getProviderByNpi(long npi) throws ProviderServiceExn {
		try {
			return this.providerDtoFactory.createProviderDto(this.providerDAO.getProviderByNpi(npi));
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.getMessage());
		}
	}

	public class TreatmentExporter implements ITreatmentExporter<TreatmentDto> {
		
		private ObjectFactory factory = new ObjectFactory();
		
		@Override
		public TreatmentDto exportDrugTreatment(long tid, String diagnosis, String drug,
				float dosage) {
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setDiagnosis(diagnosis);
			DrugTreatmentType drugInfo = factory.createDrugTreatmentType();
			drugInfo.setDosage(dosage);
			drugInfo.setDrug(drug);
			dto.setDrugTreatment(drugInfo);
			return dto;
		}

		@Override
		public TreatmentDto exportRadiology(long tid, String diagnosis, List<Date> dates) {
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setDiagnosis(diagnosis);
			RadiologyTreatmentType radiologyInfo = factory.createRadiologyTreatmentType();
			List<Date> datesList = radiologyInfo.getDates();
			for (Date d : dates) {
				datesList.add(d);
			}
			dto.setRadiologyTreatment(radiologyInfo);
			return dto;
		}

		@Override
		public TreatmentDto exportSurgery(long tid, String diagnosis, Date date) {
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setDiagnosis(diagnosis);
			SurgeryTreatmentType surgeryInfo = factory.createSurgeryTreatmentType();
			surgeryInfo.setDate(date);
			dto.setSurgeryTreatment(surgeryInfo);
			return dto;
		}
		
	}
	
	@Override
	public TreatmentDto getTreatment(long id, long tid)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
		// Export treatment DTO from provider aggregate
		try {
			Provider provider = providerDAO.getProvider(id);
			TreatmentExporter visitor = new TreatmentExporter();
			return provider.exportTreatment(tid, visitor);
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}
	
	@Override
	public long addTreatment(TreatmentDto dto) throws ProviderServiceExn {
		try {
			Provider provider = this.providerDAO.getProvider(dto.getProvider());
			DrugTreatmentType dt  = dto.getDrugTreatment();
			if (dt != null) {
				return provider.addDrugTreatment(treatmentFactory.createDrugTreatment(dto.getDiagnosis(), dt.getDrug(), dt.getDosage()), patientDAO.getPatient(dto.getPatient()));
			}
			SurgeryTreatmentType su  = dto.getSurgeryTreatment();
			if (su != null) {
				return provider.addSurgeryTreatment(treatmentFactory.createSurgeryTreatment(dto.getDiagnosis(), su.getDate()), patientDAO.getPatient(dto.getPatient()));
			}
			RadiologyTreatmentType ra  = dto.getRadiologyTreatment();
			if (ra != null) {
				return provider.addRadiologyTreatment(treatmentFactory.createRadiologyTreatment(dto.getDiagnosis(), ra.getDates()), patientDAO.getPatient(dto.getPatient()));
			}
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		} catch (PatientExn e) {
			throw new ProviderServiceExn(e.toString());
		}
		throw new ProviderServiceExn("should literally never get here");
	}

	@Resource(name = "SiteInfo")
	private String siteInformation;
	

	@Override
	public String siteInfo() {
		return siteInformation;
	}

}
