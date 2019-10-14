package energy.viridis.exercise.service.impl;

import energy.viridis.exercise.TestUtils;
import energy.viridis.exercise.model.Equipment;
import energy.viridis.exercise.repository.EquipmentRepository;
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
public class EquipmentServiceImplTest {

    @Mock
    EquipmentRepository equipmentRepository;

    @InjectMocks
    EquipmentServiceImpl equipmentService;

    @Test
    public void mustListAllEquipaments() {
        Mockito.when(equipmentRepository.findAll()).thenReturn(TestUtils.getMockListEquipaments());
        Assert.assertTrue(equipmentService.getAll().size() == 2);

    }

    @Test
    public void mustGetEquipament()  {
        Mockito.when(equipmentRepository.findById(Mockito.anyLong())).
                thenReturn(Optional.of(TestUtils.getMockEquipament()));

        Assert.assertEquals(equipmentService.get(1l),TestUtils.getMockEquipament());

    }

    @Test
    public void mustSaveEquipament()  {
        Mockito.when(equipmentRepository.save(Mockito.any(Equipment.class))).
                thenReturn(TestUtils.getMockEquipament());

        Assert.assertEquals(equipmentService.save(TestUtils.getMockEquipament().withId(null)),TestUtils.getMockEquipament());

    }

    @Test
    public void mustUpdateEquipament()  {
        Mockito.when(equipmentRepository.findById(Mockito.anyLong())).
                thenReturn(Optional.of(TestUtils.getMockEquipament()));

        Mockito.when(equipmentRepository.save(Mockito.any(Equipment.class))).
                thenReturn(TestUtils.getMockEquipament());

        Assert.assertEquals(equipmentService.save(TestUtils.getMockEquipament().withId(1l)),TestUtils.getMockEquipament());

    }


    @Test(expected = NoSuchElementException.class)
    public void mustEquipamentNotFound()  {
        equipmentService.save(TestUtils.getMockEquipament().withId(1l));
    }


    @Test
    public void mustRemoveEquipament() {
        Mockito.doNothing().when(equipmentRepository).deleteById(Mockito.anyLong());
        equipmentService.remove(1l);
    }

}
