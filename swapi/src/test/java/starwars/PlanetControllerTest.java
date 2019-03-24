package starwars;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.adpguima.starwars.StarwarsApplication;
import com.adpguima.starwars.model.Planets;
import com.adpguima.starwars.model.SwapiPlanet;
import com.adpguima.starwars.service.PlanetsService;
import com.adpguima.starwars.service.SwapiService;

/**
 * @author André Guimarães <andrepaivaguimaraes@gmail.com>
 *
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = StarwarsApplication.class)
public class PlanetControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private PlanetsService planetService;

	@Autowired
	private SwapiService swapiService;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	private Planets getPlanetForTest() {
		Planets testValidPlanet = planetService.findByNameIgnoreCase("Dagobah");

		if (testValidPlanet == null) {
			testValidPlanet = planetService.add(new Planets("Dagobah", "climate x", "terrain y"));
		}

		SwapiPlanet apiPlanet = swapiService.getSwapiPlanetByName(testValidPlanet.getName());
		testValidPlanet.setFilmsCount(apiPlanet.getFilmsCount());
		
		return testValidPlanet;
	}

	@Test
	public void getPlanetByIdTest() throws Exception {

		this.mockMvc.perform(get("/planet/" + this.getPlanetForTest().getPlanetId()).contentType(contentType))
				.andExpect(status().isOk());
	}

	@Test
	public void getPlanetByIdNotFoundTest() throws Exception {

		this.mockMvc.perform(get("/planet/" + 12345).contentType(contentType)).andExpect(status().isNotFound());
	}

	@Test
	public void findByNameTest() throws Exception {

		this.mockMvc.perform(get("/planets/search?name=" + this.getPlanetForTest().getName()).contentType(contentType))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteByIdTest() throws Exception {

		this.mockMvc.perform(delete("/planet/" + this.getPlanetForTest().getPlanetId()).contentType(contentType))
				.andExpect(status().isOk());

	}

	@Test
	public void getSWAPIById() throws Exception {

		this.mockMvc.perform(get("/planets/swapi/" + 3).contentType(contentType)).andExpect(status().isOk());
	}
}
