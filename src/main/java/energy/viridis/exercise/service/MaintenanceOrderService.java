package energy.viridis.exercise.service;

import energy.viridis.exercise.model.Equipment;
import energy.viridis.exercise.model.MaintenanceOrder;

import java.util.List;

public interface MaintenanceOrderService {

	MaintenanceOrder get(Long id);

	List<MaintenanceOrder> getAll();

	MaintenanceOrder save(MaintenanceOrder maintenanceOrder);

	void remove(Long id);

}
