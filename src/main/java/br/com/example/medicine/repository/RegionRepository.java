package br.com.example.medicine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.example.medicine.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{

	Region findByname(String nameRegion);

}
