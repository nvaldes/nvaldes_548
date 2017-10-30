package edu.stevens.cs548.clinic.service.dto.util;

import java.util.List;

import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;

public class ProviderDtoFactory {
	
	ObjectFactory factory;
	
	public ProviderDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public ProviderDto createProviderDto () {
		return factory.createProviderDto();
	}
	
	public ProviderDto createProviderDto (Provider p) {
		ProviderDto d = factory.createProviderDto();
		d.setId(p.getId());
		d.setName(p.getName());
		d.setNpi(p.getNpi());
		List<Long> treatments = d.getTreatments();
		for (Long tid : p.getTreatmentIds()) {
			treatments.add(tid);
		}
		return d;
	}

}
