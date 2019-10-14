package energy.viridis.exercise.service;

import java.util.List;

import energy.viridis.exercise.model.Equipment;

public interface EquipmentService {

	Equipment get(Long id);

	List<Equipment> getAll();

	Equipment save(Equipment equipment);

	void remove(Long id);

}
