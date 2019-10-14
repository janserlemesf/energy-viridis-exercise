package energy.viridis.exercise;

import energy.viridis.exercise.model.Equipment;
import energy.viridis.exercise.model.MaintenanceOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestUtils {

    public static List<Equipment> getMockListEquipaments() {
        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(new Equipment().withId(1l).withName("Test 1"));
        equipmentList.add(new Equipment().withId(2l).withName("Test 2"));
        return equipmentList;
    }

    public static Equipment getMockEquipament() {
        return new Equipment().withId(1l).withName("Test 1");
    }

    public static MaintenanceOrder getMockMaintenanceOrder() {
        return  getMockMaintenanceOrder(null);
    }

    public static MaintenanceOrder getMockMaintenanceOrder (Long id) {
        MaintenanceOrder maintenanceOrder = new MaintenanceOrder();
        maintenanceOrder.setId(id);
        maintenanceOrder.setEquipment(getMockEquipament());
        maintenanceOrder.setScheduledDate(new Date());
        return  maintenanceOrder;
    }

    public static List<MaintenanceOrder> getMockListMaintenanceOrders() {
        List<MaintenanceOrder> maintenanceOrderList = new ArrayList<>();

        maintenanceOrderList.add(getMockMaintenanceOrder(1l));
        maintenanceOrderList.add(getMockMaintenanceOrder(2l));

        return maintenanceOrderList;
    }
}
