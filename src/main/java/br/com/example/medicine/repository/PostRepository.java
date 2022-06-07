package br.com.example.medicine.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.example.medicine.model.Post;
import br.com.example.medicine.model.Region;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	Post findByname(String region);

	Page<Post> findAllByRegion(Region region, Pageable pageable);
}
