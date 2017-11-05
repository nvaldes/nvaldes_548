package edu.stevens.cs548.clinic.service.web.rest.resources;

import java.net.URI;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;
import edu.stevens.cs548.clinic.service.representations.ProviderRepresentation;
import edu.stevens.cs548.clinic.service.representations.TreatmentRepresentation;

@Path("/provider")
@RequestScoped
public class ProviderResource {
	
	final static Logger logger = Logger.getLogger(ProviderResource.class.getCanonicalName());
	
	/*
	 * DONE inject using HK2 (not CDI)
	 */
	@Context
    private UriInfo uriInfo;
    
    private ProviderDtoFactory providerDtoFactory;
    private TreatmentDtoFactory treatmentDtoFactory;

    /**
     * Default constructor. 
     */
    public ProviderResource() {
		/*
		 * TODO finish this
		 */
    }
    
	/*
	 * DONE inject using CDI
	 */
    @Inject
    private IProviderServiceLocal providerService;
    
    @GET
    @Path("site")
    @Produces("text/plain")
    public String getSiteInfo() {
    	return providerService.siteInfo();
    }

	/*
	 * DONE input should be application/xml
	 */
    @POST
    @Consumes("application/xml")
    public Response addProvider(ProviderRepresentation providerRep) {
    	try {
    		ProviderDto dto = providerDtoFactory.createProviderDto();
    		dto.setNpi(providerRep.getNpi());
    		dto.setName(providerRep.getName());
    		long id = providerService.addProvider(dto);
    		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
    		URI url = ub.build(Long.toString(id));
    		return Response.created(url).build();
    	} catch (ProviderServiceExn e) {
    		throw new WebApplicationException();
    	}
    }
    
    /*
	 * DONE input should be application/xml
	 */
    @POST
    @Path("{id}/treatments")
    @Consumes("application/xml")
    public Response addTreatment(@PathParam("id") String id, TreatmentRepresentation treatmentRep) {
    	try {
    		TreatmentDto dto = treatmentRep.getTreatment();
    		if (id != Long.toString(dto.getProvider())) {
    			throw new WebApplicationException("Provider in path does not match provider in treatment.");
    		}
    		long tid = providerService.addTreatment(dto);
    		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{tid}");
    		URI url = ub.build(Long.toString(tid));
    		return Response.created(url).build();
    	} catch (ProviderServiceExn e) {
    		throw new WebApplicationException(e);
    	}
    }
    
	/**
	 * Query methods for provider resources.
	 */
	/*
	 * DONE output should be application/xml
	 */
    @GET
    @Path("{id}")
    @Produces("application/xml")
	public ProviderRepresentation getProvider(@PathParam("id") String id) {
		try {
			long key = Long.parseLong(id);
			ProviderDto providerDTO = providerService.getProvider(key);
			ProviderRepresentation providerRep = new ProviderRepresentation(providerDTO, uriInfo);
			return providerRep;
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException(e);
		}
	}
    
    /*
	 * DONE output should be application/xml
	 */
    @GET
    @Path("byNpi")
    @Produces("application/xml")
	public ProviderRepresentation getProviderByNpi(@QueryParam("npi") String npi) {
		try {
			long key = Long.parseLong(npi);
			ProviderDto providerDTO = providerService.getProviderByNpi(key);
			ProviderRepresentation providerRep = new ProviderRepresentation(providerDTO, uriInfo);
			return providerRep;
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException();
		}
	}
    
    /*
	 * DONE output should be application/xml
	 */
    @GET
    @Path("{id}/treatments/{tid}")
    @Produces("application/xml")
    public TreatmentRepresentation getProviderTreatment(@PathParam("id") String id, @PathParam("tid") String tid) {
    	try {
    		TreatmentDto treatment = providerService.getTreatment(Long.parseLong(id), Long.parseLong(tid)); 
    		TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, uriInfo);
    		return treatmentRep;
    	} catch (ProviderServiceExn e) {
    		throw new WebApplicationException();
    	}
    }

}