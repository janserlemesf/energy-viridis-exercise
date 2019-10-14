package energy.viridis.exercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import energy.viridis.exercise.TestUtils;
import energy.viridis.exercise.model.Equipment;
import energy.viridis.exercise.model.MaintenanceOrder;
import energy.viridis.exercise.security.TokenAuthenticationService;
import energy.viridis.exercise.service.EquipmentService;
import energy.viridis.exercise.service.MaintenanceOrderService;
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
@WebMvcTest(MaintenanceOrderController.class)
public class MaintenanceOrderControllerTest {

    @MockBean
    private MaintenanceOrderService maintenanceOrderService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    String token = null;

    @Before
    public void setup(){
        token = TokenAuthenticationService.createToken("admin");
    }

    @Test
    public void mustListAllMaintenanceOrders() throws Exception {
        Mockito.when(maintenanceOrderService.getAll()).thenReturn(TestUtils.getMockListMaintenanceOrders());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/maintenance-order")
                        .header("Authorization","Bearer  " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is(2)));

    }

    @Test
    public void mustGetMaintenanceOrder() throws Exception {
        Mockito.when(maintenanceOrderService.get(Mockito.anyLong())).
                thenReturn(TestUtils.getMockMaintenanceOrder(1l));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/maintenance-order/1")
                        .header("Authorization","Bearer  " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)));

    }

    @Test
    public void mustSaveMaintenanceOrder() throws Exception {
        Mockito.when(maintenanceOrderService.save(Mockito.any(MaintenanceOrder.class))).
                thenReturn(TestUtils.getMockMaintenanceOrder(1l));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/maintenance-order")
                        .header("Authorization","Bearer  " + token)
                        .content(mapper.writeValueAsString(TestUtils.getMockMaintenanceOrder()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)));

    }

    @Test
    public void mustUpdateMaintenanceOrder() throws Exception {
        Mockito.when(maintenanceOrderService.save(Mockito.any(MaintenanceOrder.class))).
                thenReturn(TestUtils.getMockMaintenanceOrder(1l));


        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/maintenance-order/1")
                        .header("Authorization","Bearer  " + token)
                        .content(mapper.writeValueAsString(TestUtils.getMockMaintenanceOrder()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)));

    }

    @Test
    public void mustErrorSaveMaintenanceOrder() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/maintenance-order")
                        .header("Authorization","Bearer  " + token)
                        .content(mapper.writeValueAsString(TestUtils.getMockMaintenanceOrder(1l)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void mustRemoveMaintenanceOrder() throws Exception {

        Mockito.doNothing().when(maintenanceOrderService).remove(Mockito.anyLong());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/maintenance-order/1")
                        .header("Authorization","Bearer  " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
