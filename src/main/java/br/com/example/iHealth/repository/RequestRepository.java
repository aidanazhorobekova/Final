package br.com.example.iHealth.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.example.iHealth.model.Request;
import br.com.example.iHealth.model.Status;
import br.com.example.iHealth.model.User;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{

	List<Request> findAllByStatus(Status status, Sort sort);

	List<Request> findAllByUser(User user);

	List<Request> findAllByUserAndStatus(User user, Status recusado, Sort descending);

	List<Request> deleteAllByUser(User user);

	List<Request> findAllByUser(User user, Sort descending);

}
