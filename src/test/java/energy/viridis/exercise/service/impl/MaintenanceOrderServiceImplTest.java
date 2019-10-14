package energy.viridis.exercise.service.impl;

import energy.viridis.exercise.TestUtils;
import energy.viridis.exercise.model.Equipment;
import energy.viridis.exercise.model.MaintenanceOrder;
import energy.viridis.exercise.repository.EquipmentRepository;
import energy.viridis.exercise.repository.MaintenanceOrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class MaintenanceOrderServiceImplTest {

    @Mock
    MaintenanceOrderRepository maintenanceOrderRepository;

    @InjectMocks
    MaintenanceOrderServiceImpl maintenanceOrderService;

    @Test
    public void mustListAllMaintenanceOrders() {
        Mockito.when(maintenanceOrderRepository.findAll()).thenReturn(TestUtils.getMockListMaintenanceOrders());
        Assert.assertTrue(maintenanceOrderService.getAll().size() == 2);

    }

    @Test
    public void mustGetMaintenanceOrder() {
        Mockito.when(maintenanceOrderRepository.findById(Mockito.anyLong())).
                thenReturn(Optional.of(TestUtils.getMockMaintenanceOrder(1l)));

        Assert.assertEquals(maintenanceOrderService.get(1l).getId(), TestUtils.getMockMaintenanceOrder(1l).getId());

    }

    @Test
    public void mustSaveMaintenanceOrder() {
        Mockito.when(maintenanceOrderRepository.save(Mockito.any(MaintenanceOrder.class))).
                thenReturn(TestUtils.getMockMaintenanceOrder(1l));

        Assert.assertEquals(maintenanceOrderService.save(TestUtils.getMockMaintenanceOrder()).getId(), TestUtils.getMockMaintenanceOrder(1l).getId());

    }

    @Test
    public void mustUpdateMaintenanceOrder() {
        Mockito.when(maintenanceOrderRepository.findById(Mockito.anyLong())).
                thenReturn(Optional.of(TestUtils.getMockMaintenanceOrder(1l)));

        Mockito.when(maintenanceOrderRepository.save(Mockito.any(MaintenanceOrder.class))).
                thenReturn(TestUtils.getMockMaintenanceOrder(1l));

        Assert.assertEquals(maintenanceOrderService.save(TestUtils.getMockMaintenanceOrder(1l)).getId(), TestUtils.getMockMaintenanceOrder(1l).getId());

    }


    @Test(expected = NoSuchElementException.class)
    public void mustMaintenanceOrderNotFound() {
        maintenanceOrderService.save(TestUtils.getMockMaintenanceOrder(1l));
    }


    @Test
    public void mustMaintenanceOrder() {
        Mockito.doNothing().when(maintenanceOrderRepository).deleteById(Mockito.anyLong());
        maintenanceOrderService.remove(1l);
    }

}
