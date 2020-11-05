package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import tn.esprit.spring.entities.Departement;

import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {
	private static final Logger logger = Logger.getLogger(EntrepriseServiceImpl.class);
	@Autowired
	EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;

	public int ajouterEntreprise(Entreprise entreprise) {
		try {
			entrepriseRepoistory.save(entreprise);
		} catch (Exception e) {
			logger.error("Erreur dans ajouterEntreprise() : " + e);
		}
		logger.info("Entreprise created succefully");
		return entreprise.getId();
	}

	public int ajouterDepartement(Departement dep) {
		deptRepoistory.save(dep);
		return dep.getId();
	}

	@Transactional
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		if (logger.isDebugEnabled()) {
			logger.debug("java logging level is DEBUG Enabled");
		}
		try {
			Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
			Departement depManagedEntity = deptRepoistory.findById(depId).get();

			depManagedEntity.setEntreprise(entrepriseManagedEntity);
			deptRepoistory.save(depManagedEntity);
			logger.info("succes affectation : ");
			logger.info("entrepriseId: " + entrepriseId);
			logger.info("depId: " + depId);
		} catch (Exception e) {
			logger.error("affectation failed");
		}

	}

	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
		List<String> depNames = new ArrayList<>();
		for (Departement dep : entrepriseManagedEntity.getDepartements()) {
			depNames.add(dep.getName());
		}

		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		logger.debug("EntrepriseId: " + entrepriseId);
		try {
			if (entrepriseRepoistory.findById(entrepriseId).get() == null) {
				Entreprise x = entrepriseRepoistory.findById(entrepriseId).get();
				entrepriseRepoistory.delete(x);
				logger.info("ACTION CONFIRMED");
			} else {
				logger.debug("Invalid ID !!!");
			}
		} catch (Exception e) {
			logger.error("Erreur dans deleteEntrepriseById() : " + e);
		}
		logger.info("Delete Entreprise id :" + entrepriseId + " Successfully");
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		deptRepoistory.delete(deptRepoistory.findById(depId).get());
	}

	public Entreprise getEntrepriseById(int entrepriseId) {
		Entreprise x = new Entreprise();
		if (logger.isDebugEnabled()) {
			logger.debug("java logging level is DEBUG Enabled");
		}
		logger.info("getEntrepriseById() method is started ");
		try {
			x = entrepriseRepoistory.findById(entrepriseId).get();

		} catch (Exception e) {
			logger.error("Erreur in getEntrepriseById() : " + e);
		}

		return x;
	}

}
