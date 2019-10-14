package energy.viridis.exercise.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import energy.viridis.exercise.model.Equipment;
import energy.viridis.exercise.repository.EquipmentRepository;
import energy.viridis.exercise.service.EquipmentService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@CacheConfig(cacheNames={"equipaments"})
public class EquipmentServiceImpl implements EquipmentService {

	@Autowired
	private EquipmentRepository equipmentRepository;

	@Override
	@Cacheable
	public Equipment get(Long id) {

		log.info("Retrieving Equipment - id: {}", id);
		return equipmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Equipment not found."));

	}

	@Override
	@Cacheable
	public List<Equipment> getAll() {

		log.info("Listing all Equipment");
		return equipmentRepository.findAll();

	}

	@Override
	@CacheEvict(allEntries = true)
	public Equipment save(Equipment equipment) {
		log.info("Saving an Equipment");
		if(equipment.getId() != null){
			get(equipment.getId());
		}
		return equipmentRepository.save(equipment);
	}

	@Override
	@CacheEvict(allEntries = true)
	public void remove(Long id) {
		log.info("Removing an Equipment - id: {}", id);
		equipmentRepository.deleteById(id);

	}
}
