package edu.stevens.cs548.clinic.service.representations;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.RadiologyTreatmentType;
import edu.stevens.cs548.clinic.service.dto.SurgeryTreatmentType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.web.rest.data.ObjectFactory;
import edu.stevens.cs548.clinic.service.web.rest.data.TreatmentType;
import edu.stevens.cs548.clinic.service.web.rest.data.dap.LinkType;

@XmlRootElement
public class TreatmentRepresentation extends TreatmentType {
	
	private ObjectFactory repFactory = new ObjectFactory();
	
	public LinkType getLinkPatient() {
		return this.getPatient();
	}
	
	public LinkType getLinkProvider() {
		return this.getProvider();
	}
	
	public static LinkType getTreatmentLink(long tid, UriInfo uriInfo) {
		UriBuilder ub = uriInfo.getBaseUriBuilder();
		ub.path("treatment");
		UriBuilder ubTreatment = ub.clone().path("{tid}");
		String treatmentURI = ubTreatment.build(Long.toString(tid)).toString();
	
		LinkType link = new LinkType();
		link.setUrl(treatmentURI);
		link.setRelation(Representation.RELATION_TREATMENT);
		link.setMediaType(Representation.MEDIA_TYPE);
		return link;
	}
	
	private TreatmentDtoFactory treatmentDtoFactory;
	
	public TreatmentRepresentation() {
		super();
		treatmentDtoFactory = new TreatmentDtoFactory();
	}
	
	public TreatmentRepresentation (TreatmentDto dto, UriInfo uriInfo) {
		this();
		this.id = getTreatmentLink(dto.getId(), uriInfo);
		this.patient =  PatientRepresentation.getPatientLink(dto.getPatient(), uriInfo);
		/*
		 * DONE: Need to fill in provider information.
		 */
		this.provider = ProviderRepresentation.getProviderLink(dto.getProvider(), uriInfo);
		
		this.diagnosis = dto.getDiagnosis();
		
		if (dto.getDrugTreatment() != null) {
			this.drugTreatment = new edu.stevens.cs548.clinic.service.web.rest.data.DrugTreatmentType();
			this.drugTreatment.setDrug(dto.getDrugTreatment().getDrug());
			this.drugTreatment.setDosage(dto.getDrugTreatment().getDosage());
		} else if (dto.getSurgeryTreatment() != null) {
			this.surgeryTreatment = new edu.stevens.cs548.clinic.service.web.rest.data.SurgeryTreatmentType();
			this.surgeryTreatment.setDate(dto.getSurgeryTreatment().getDate());
		} else if (dto.getRadiologyTreatment() != null) {
			this.radiologyTreatment = new edu.stevens.cs548.clinic.service.web.rest.data.RadiologyTreatmentType();
			List<Date> dates = this.radiologyTreatment.getDates();
			dates = dto.getRadiologyTreatment().getDates();
		}
	}

	public TreatmentDto getTreatment() {
		TreatmentDto m = null;
		if (this.getDrugTreatment() != null) {
			m = treatmentDtoFactory.createDrugTreatmentDto();
//			m.setId(Representation.getId(id));
			m.setPatient(Representation.getId(patient));
			m.setProvider(Representation.getId(provider));
			m.setDiagnosis(diagnosis);
			DrugTreatmentType dt = new DrugTreatmentType();
			dt.setDrug(drugTreatment.getDrug());
			dt.setDosage(drugTreatment.getDosage());
			m.setDrugTreatment(dt);
		} else if (this.getSurgeryTreatment() != null) {
			m = treatmentDtoFactory.createSurgeryTreatmentDto();
//			m.setId(Representation.getId(id));
			m.setPatient(Representation.getId(patient));
			m.setProvider(Representation.getId(provider));
			m.setDiagnosis(diagnosis);
			SurgeryTreatmentType su = new SurgeryTreatmentType();
			su.setDate(surgeryTreatment.getDate());
			m.setSurgeryTreatment(su);
		} else if (this.getRadiologyTreatment() != null) {
			m = treatmentDtoFactory.createRadiologyTreatmentDto();
//			m.setId(Representation.getId(id));
			m.setPatient(Representation.getId(patient));
			m.setProvider(Representation.getId(provider));
			m.setDiagnosis(diagnosis);
			RadiologyTreatmentType ra = new RadiologyTreatmentType();
			List<Date> dates = ra.getDates();
			dates = radiologyTreatment.getDates();
			m.setRadiologyTreatment(ra);
		}
		return m;
	}

	
}
