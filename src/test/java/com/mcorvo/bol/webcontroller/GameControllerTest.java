package com.mcorvo.bol.webcontroller;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import com.mcorvo.bol.TestContext;
import com.mcorvo.bol.logic.GameLogic;
import com.mcorvo.bol.mvc.config.WebAppContext;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
//@ContextConfiguration(locations = {"classpath:testContext.xml", "classpath:exampleApplicationContext-web.xml"})
@WebAppConfiguration
public class GameControllerTest {
	private MockMvc mockMvc;
	
	@InjectMocks
	MockHttpSession session;
	 
    @Autowired
    private WebApplicationContext webApplicationContext;
 
    @Before
    public void setUp() {
    	//Reseting the mocks
    	MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
	public void testHome() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("page"));
	}
    
    @Test
	public void testInit() throws Exception {
		mockMvc.perform(get("/init")).andExpect(status().isOk()).andExpect(view().name("init"));
	}
    
    @Test
	public void testMove() throws Exception {
    	GameLogic gc = new GameLogic();
    	gc.newGame();

    	MvcResult result = (MvcResult) mockMvc.perform(get("/move/{position}",1).sessionAttr("GameSession", gc)).andExpect(status().isOk()).andExpect(model().size(1)).andReturn();
    	
    	Object obResult = result.getModelAndView().getModel().get("gameLogic");
    	assertTrue(obResult instanceof GameLogic);
	}
    
    @Test(expected = NestedServletException.class)
	public void testExceptionHandler() throws Exception {
    	mockMvc.perform(get("/move/{position}",10)).andExpect(status().isOk()).andExpect(model().size(1)).andReturn();
    	mockMvc.perform(get("/move/{position}",1)).andExpect(status().isOk()).andExpect(model().size(1)).andReturn();
    	mockMvc.perform(get("/move/{position}",10)).andReturn();

	}
    
}
