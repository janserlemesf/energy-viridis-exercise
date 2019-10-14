package energy.viridis.exercise.controller;

import energy.viridis.exercise.exception.BadRequestException;
import energy.viridis.exercise.model.MaintenanceOrder;
import energy.viridis.exercise.service.MaintenanceOrderService;
import energy.viridis.exercise.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance-order")
public class MaintenanceOrderController {

	@Autowired
	private MaintenanceOrderService maintenanceOrderService;

	@GetMapping
	public ResponseEntity<List<MaintenanceOrder>> getAll() {
		return ResponseEntity.ok().body(maintenanceOrderService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<MaintenanceOrder> get(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(maintenanceOrderService.get(id));
	}

	@PostMapping
	public ResponseEntity<MaintenanceOrder> save(@RequestBody MaintenanceOrder maintenanceOrder) {
		if(maintenanceOrder.getId() != null){
			throw new BadRequestException(Constants.MSG_ERROR_SAVING_RECORD);
		}
		return ResponseEntity.ok().body(maintenanceOrderService.save(maintenanceOrder));
	}

	@PutMapping("/{id}")
	public ResponseEntity<MaintenanceOrder> update(@PathVariable("id") Long id, @RequestBody MaintenanceOrder maintenanceOrder) {
		maintenanceOrder.setId(id);
		return ResponseEntity.ok().body(maintenanceOrderService.save(maintenanceOrder));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable("id") Long id) {
		maintenanceOrderService.remove(id);
		return ResponseEntity.ok().body(Constants.MSG_SUCESSFULY_REMOVED);
	}
}
