package tn.esprit.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;
import org.apache.log4j.Logger;

@RestController
public class RestControlEntreprise {

	private static final Logger logger = Logger.getLogger(RestControlEntreprise.class);
	@Autowired
	IEmployeService iemployeservice;
	@Autowired
	IEntrepriseService ientrepriseservice;
	@Autowired
	ITimesheetService itimesheetservice;

	// Ajouter Entreprise :
	// http://localhost:8081/SpringMVC/servlet/ajouterEntreprise
	// {"id":1,"name":"SSII Consulting","raisonSocial":"Cite El Ghazela"}

	@PostMapping("/ajouterEntreprise")
	@ResponseBody

	public int ajouterEntreprise(@RequestBody Entreprise ssiiConsulting) {
		if (logger.isDebugEnabled()) {
			logger.debug("java logging level is DEBUG Enabled");
		}
		logger.info("ajouterEntreprise() method is started ");
		try {

			ientrepriseservice.ajouterEntreprise(ssiiConsulting);
		} catch (Exception e) {
			logger.error("Erreur dans ajouterEntreprise() : " + e);
		}
		return ssiiConsulting.getId();
	}

	// http://localhost:8081/SpringMVC/servlet/affecterDepartementAEntreprise/1/1
	@PutMapping(value = "/affecterDepartementAEntreprise/{iddept}/{identreprise}")
	public void affecterDepartementAEntreprise(@PathVariable("iddept") int depId,
			@PathVariable("identreprise") int entrepriseId) {
		if (logger.isDebugEnabled()) {
			logger.debug("java logging level is DEBUG Enabled");
		}
		logger.info("affecterDepartementAEntreprise() method is started");
		try {

			ientrepriseservice.affecterDepartementAEntreprise(depId, entrepriseId);
		} catch (Exception e) {
			logger.error("Erreur dans affecterDepartementAEntreprise() : " + e);
		}
	}

	// http://localhost:8081/SpringMVC/servlet/deleteEntrepriseById/1
	@DeleteMapping("/deleteEntrepriseById/{identreprise}")
	@ResponseBody
	public void deleteEntrepriseById(@PathVariable("identreprise") int entrepriseId) {
		if (logger.isDebugEnabled()) {
			logger.debug("java logging level is DEBUG Enabled");
		}
		logger.info("deleteEntrepriseById() method is started ");
		try {
			ientrepriseservice.deleteEntrepriseById(entrepriseId);
		} catch (Exception e) {
			logger.error("Erreur dans deleteEntrepriseById() : " + e);
		}

	}

	// http://localhost:8081/SpringMVC/servlet/getEntrepriseById/1
	@GetMapping(value = "getEntrepriseById/{identreprise}")
	@ResponseBody

	public Entreprise getEntrepriseById(@PathVariable("identreprise") int entrepriseId) {
		if (logger.isDebugEnabled()) {
			logger.debug("java logging level is DEBUG Enabled");
		}
		logger.info("getEntrepriseById() method is started ");
		try {
			if (ientrepriseservice.getEntrepriseById(entrepriseId).getId() == 0) {
				logger.warn("Invalid Id");
				logger.debug("java logging level is WARN Enabled");
			} else {
				logger.info("Success : " + entrepriseId);
			}

		} catch (Exception e) {
			logger.error("Erreur dans getEntrepriseById() : " + e);
		}
		return ientrepriseservice.getEntrepriseById(entrepriseId);
	}

	// http://localhost:8081/SpringMVC/servlet/ajouterDepartement
	// {"id":1,"name":"Telecom"}

	@PostMapping("/ajouterDepartement")
	@ResponseBody
	public int ajouterDepartement(@RequestBody Departement dep) {
		return ientrepriseservice.ajouterDepartement(dep);
	}

	// http://localhost:8081/SpringMVC/servlet/getAllDepartementsNamesByEntreprise/1
	@GetMapping(value = "getAllDepartementsNamesByEntreprise/{identreprise}")
	@ResponseBody
	public List<String> getAllDepartementsNamesByEntreprise(@PathVariable("identreprise") int entrepriseId) {
		return ientrepriseservice.getAllDepartementsNamesByEntreprise(entrepriseId);
	}

	// URL : http://localhost:8081/SpringMVC/servlet/deleteDepartementById/3
	@DeleteMapping("/deleteDepartementById/{iddept}")
	@ResponseBody
	public void deleteDepartementById(@PathVariable("iddept") int depId) {
		ientrepriseservice.deleteDepartementById(depId);

	}
}
