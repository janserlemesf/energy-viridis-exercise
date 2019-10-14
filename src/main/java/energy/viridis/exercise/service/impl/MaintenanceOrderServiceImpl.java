package energy.viridis.exercise.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import energy.viridis.exercise.model.MaintenanceOrder;
import energy.viridis.exercise.repository.MaintenanceOrderRepository;
import energy.viridis.exercise.service.MaintenanceOrderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@CacheConfig(cacheNames={"maintenace-orders"})
public class MaintenanceOrderServiceImpl implements MaintenanceOrderService {

	@Autowired
	private MaintenanceOrderRepository maintenanceOrderRepository;

	@Override
	@Cacheable
	public MaintenanceOrder get(Long id) {

		log.info("Retrieving Maintenance Order - id: {}", id);
		return maintenanceOrderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Maintenance Order not found."));

	}

	@Override
	@Cacheable
	public List<MaintenanceOrder> getAll() {

		log.info("Listing all Maintenance Orders...");
		return maintenanceOrderRepository.findAll();
	}

	@Override
	@CacheEvict(allEntries = true)
	public MaintenanceOrder save(MaintenanceOrder maintenanceOrder) {
		log.info("Saving a Maintenance Order");
		if(maintenanceOrder.getId() != null){
			get(maintenanceOrder.getId());
		}
		return maintenanceOrderRepository.save(maintenanceOrder);
	}

	@Override
	@CacheEvict(allEntries = true)
	public void remove(Long id) {
		log.info("Removing a Maintenance Order - id: {}", id);
		maintenanceOrderRepository.deleteById(id);

	}
}
