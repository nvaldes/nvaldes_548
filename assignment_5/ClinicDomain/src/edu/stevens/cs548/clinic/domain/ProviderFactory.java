package edu.stevens.cs548.clinic.domain;

import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;

public class ProviderFactory implements IProviderFactory {

	@Override
	public Provider createProvider(long npi, String name) throws ProviderExn {
		Provider p = new Provider();
		p.setNpi(npi);
		p.setName(name);
		return p;
	}

}
