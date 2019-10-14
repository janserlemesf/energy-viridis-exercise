package energy.viridis.exercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import energy.viridis.exercise.TestUtils;
import energy.viridis.exercise.model.Equipment;
import energy.viridis.exercise.security.TokenAuthenticationService;
import energy.viridis.exercise.service.EquipmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(EquipmentController.class)
public class EquipmentControllerTest {

    @MockBean
    private EquipmentService equipmentService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    String token = null;

    @Before
    public void setup(){
        token = TokenAuthenticationService.createToken("admin");
    }

    @Test
    public void mustListAllEquipaments() throws Exception {
        Mockito.when(equipmentService.getAll()).thenReturn(TestUtils.getMockListEquipaments());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/equipment")
                        .header("Authorization","Bearer  " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("Test 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", is("Test 2")));

    }

    @Test
    public void mustGetEquipament() throws Exception {
        Mockito.when(equipmentService.get(Mockito.anyLong())).
                thenReturn(TestUtils.getMockEquipament());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/equipment/1")
                        .header("Authorization","Bearer  " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Test 1")));

    }

    @Test
    public void mustSaveEquipament() throws Exception {
        Mockito.when(equipmentService.save(Mockito.any(Equipment.class))).
                thenReturn(TestUtils.getMockEquipament());

        Equipment equipment = new Equipment().withName("Test 1");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/equipment")
                        .header("Authorization","Bearer  " + token)
                        .content(mapper.writeValueAsString(equipment))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Test 1")));

    }

    @Test
    public void mustUpdateEquipament() throws Exception {
        Mockito.when(equipmentService.save(Mockito.any(Equipment.class))).
                thenReturn(TestUtils.getMockEquipament());

        Equipment equipment = new Equipment().withName("Test 1");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/equipment/1")
                        .header("Authorization","Bearer  " + token)
                        .content(mapper.writeValueAsString(equipment))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Test 1")));

    }

    @Test
    public void mustErrorSaveEquipament() throws Exception {

        Equipment equipment = new Equipment().withName("Test 1").withId(1l);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/equipment")
                        .header("Authorization","Bearer  " + token)
                        .content(mapper.writeValueAsString(equipment))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void mustRemoveEquipament() throws Exception {

        Mockito.doNothing().when(equipmentService).remove(Mockito.anyLong());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/equipment/1")
                        .header("Authorization","Bearer  " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
