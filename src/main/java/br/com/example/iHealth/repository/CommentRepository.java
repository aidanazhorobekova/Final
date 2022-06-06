package br.com.example.iHealth.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.example.iHealth.model.Comment;
import br.com.example.iHealth.model.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByUser(User user, Sort sort);

	List<Comment>findAll(Sort sort);
}
