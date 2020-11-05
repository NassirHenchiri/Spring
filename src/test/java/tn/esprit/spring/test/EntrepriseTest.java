package tn.esprit.spring.test;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.EntrepriseServiceImpl;
import tn.esprit.spring.controller.RestControlEntreprise;




@WithMockUser
@RunWith(SpringRunner.class)
@WebMvcTest(controllers  = RestControlEntreprise.class)
public class EntrepriseTest {
	
	@Autowired                           
    private MockMvc mockMvc;
	@MockBean  
	private EntrepriseServiceImpl entService; 
	@MockBean  
	private EmployeServiceImpl emplService; 
	
	@MockBean
	private EmployeRepository emplRepo ;
	@MockBean
	private EntrepriseRepository etrepRepo ;
	@MockBean
	private DepartementRepository depRepo ;
	@MockBean
	private MissionRepository missionRepo ; 
	@MockBean
	private TimesheetRepository timeRepo ; 
	@MockBean
	private RestControlEntreprise entControl ;
	@Mock
	EntrepriseRepository dao;
	 
	private static ObjectMapper mapper = new ObjectMapper();
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void createNewEntre() throws Exception{
		Entreprise e = new Entreprise();
		Entreprise mockEntre = new Entreprise();
		mockEntre.setName("hhhhh");
		mockEntre.setRaisonSocial("hhhhh");
		mockEntre.setId(0);
	   
		/*Mockito.when(
				emplService.ajouterEmploye(mockEmpl)).thenReturn(mockEmpl.getId());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/ajouterEmployer").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.accept(MediaType.APPLICATION_JSON).content("{\"id\":1,\"nom\":\"kalle44\", \"prenom\":\"khaled\", \"email\":\"Khaled.kallel@ssiiconsulting.tn\", \"isActif\":true, \"role\":\"INGENIEUR\"}")
				.characterEncoding("utf-8");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus()); */
		
		Mockito.when(entService.ajouterEntreprise(ArgumentMatchers.any())).thenReturn(mockEntre.getId());
        String json = mapper.writeValueAsString(mockEntre);
        mockMvc.perform(post("/ajouterEntreprise").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
               .andExpect(jsonPath("$", Matchers.equalTo(0)))  
               .andExpect(forwardedUrl(null)); 
	}
	
	
	@Test
    public void shouldaffecterEntrepriseADepartement() throws Exception{
        Entreprise e = new Entreprise();
        Entreprise mockEmpl = new Entreprise();
        mockEmpl.setName("nassir");
        mockEmpl.setRaisonSocial("com");
        mockEmpl.setId(8);


        doNothing().when(entService).affecterDepartementAEntreprise(ArgumentMatchers.anyInt(),ArgumentMatchers.anyInt());
        String json = mapper.writeValueAsString(mockEmpl);
        mockMvc.perform(put("/affecterDepartementAEntreprise/1/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
               //.andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(forwardedUrl(null));

    }
	
	@Test
    public void shoulddeleteEntrepriseById() throws Exception{

        doNothing().when(entService).deleteEntrepriseById(ArgumentMatchers.anyInt());
        mockMvc.perform(delete("/deleteEntrepriseById/3").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
               //.andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(forwardedUrl(null));

    }
	/*
	@Test 
	public void shouldGetEntrepriseById () throws Exception {
		Entreprise mockEmpl = new Entreprise();
		mockEmpl.setName("nassir");
        mockEmpl.setRaisonSocial("com");
        mockEmpl.setId(8);

        Mockito.when(entService.getEntrepriseById(ArgumentMatchers.anyInt())).thenReturn(mockEmpl);
        mockMvc.perform(get("/getEntrepriseById/8")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(8)))
                .andExpect(forwardedUrl(null));

    }
	*/
}
