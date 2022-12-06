package com.example.eventmanagement.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.eventmanagement.entities.Participant;

import java.lang.String;

public interface ParticipantRepository extends PagingAndSortingRepository<Participant, Long> {
	Page<Participant> findByEmail(@Param("email") String email, Pageable pageable);

	Participant findByEmail(String emailId);
}
