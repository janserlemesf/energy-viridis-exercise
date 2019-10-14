package energy.viridis.exercise.controller;

import java.util.List;

import energy.viridis.exercise.exception.BadRequestException;
import energy.viridis.exercise.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import energy.viridis.exercise.model.Equipment;
import energy.viridis.exercise.service.EquipmentService;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

	@Autowired
	private EquipmentService equipmentService;

	@GetMapping
	public ResponseEntity<List<Equipment>> getAll() {
		return ResponseEntity.ok().body(equipmentService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Equipment> get(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(equipmentService.get(id));
	}

	@PostMapping
	public ResponseEntity<Equipment> save(@RequestBody Equipment equipment) {
		if(equipment.getId() != null){
			throw new BadRequestException(Constants.MSG_ERROR_SAVING_RECORD);
		}
		return ResponseEntity.ok().body(equipmentService.save(equipment));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Equipment> update(@PathVariable("id") Long id, @RequestBody Equipment equipment) {
		equipment.setId(id);
		return ResponseEntity.ok().body(equipmentService.save(equipment));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable("id") Long id) {
		equipmentService.remove(id);
		return ResponseEntity.ok().body(Constants.MSG_SUCESSFULY_REMOVED);
	}

}
