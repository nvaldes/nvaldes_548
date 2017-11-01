package edu.stevens.cs548.clinic.domain;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ProviderDAO implements IProviderDAO{
	
	private EntityManager em;
	private TreatmentDAO treatmentDAO;
	
	public ProviderDAO(EntityManager em) {
		this.em = em;
		this.treatmentDAO = new TreatmentDAO(em);
	}

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(ProviderDAO.class.getCanonicalName());

	@Override
	public long addProvider(Provider provider) throws ProviderExn {
		long npi = provider.getNpi();
		Query query = em.createNamedQuery("CountProviderByNPI").setParameter("npi", npi);
		Long numExisting = (Long) query.getSingleResult();
		if (numExisting < 1) {
			// DONE add to database (and sync with database to generate primary key)
			// Don't forget to initialize the provider aggregate with a treatment DAO
			provider.setTreatmentDAO(this.treatmentDAO);
			em.persist(provider);
			em.flush();
			return provider.getId();
						
		} else {
			throw new ProviderExn("Insertion: Provider with NPI (" + npi + ") already exists.");
		}
	}

	@Override
	public Provider getProvider(long id) throws ProviderExn {
		// DONE retrieve provider using primary key
		Provider p = em.find(Provider.class, id);
		if (p == null) {
			throw new ProviderExn("Missing provider: id = " + id);
		} else {
			return p;
		}
	}

	@Override
	public Provider getProviderByNpi(long npi) throws ProviderExn {
		// DONE retrieve provider using query on NPI (secondary key)
		Query query = em.createNamedQuery("SearchProviderByNPI").setParameter("npi", npi);
		Provider p = (Provider) query.getSingleResult();
		if (p == null) {
			throw new ProviderExn("Missing provider: npi = " + npi);
		} else {
			return p;
		}
	}
	
	@Override
	public void deleteProviders() {
		Query update = em.createNamedQuery("RemoveAllProviders");
		update.executeUpdate();
	}
}
