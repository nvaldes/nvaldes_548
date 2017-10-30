package edu.stevens.cs548.clinic.service.web.soap;

import javax.ejb.EJB;
import javax.jws.WebService;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.TreatmentNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;

// Use JSR-181 annotations to specify Web service.
//Specify: endpoint interface (FQN), target namespace, service name, port name

@WebService(endpointInterface = "edu.stevens.cs548.clinic.service.web.soap.IProviderWebService", targetNamespace = "http://cs548.stevens.edu/clinic/service/web/soap", serviceName = "ProviderWebService", portName = "ProviderWebPort")

public class ProviderWebService implements IProviderWebService {

	@EJB(beanName = "ProviderServiceBean")
	IProviderServiceLocal service;

	@Override
	public long addProvider(ProviderDto dto) throws ProviderServiceExn {
		return service.addProvider(dto);
	}

	@Override
	public ProviderDto getProvider(long id) throws ProviderServiceExn {
		return service.getProvider(id);
	}

	@Override
	public ProviderDto getProviderByNpi(long pid) throws ProviderServiceExn {
		return service.getProviderByNpi(pid);
	}

	@Override
	public TreatmentDto getTreatment(long id, long tid)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
		return service.getTreatment(id, tid);
	}
	
	@Override
	public long addTreatment(TreatmentDto dto) throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
		return service.addTreatment(dto);
	}

	@Override
	public String siteInfo() {
		return service.siteInfo();
	}
}
