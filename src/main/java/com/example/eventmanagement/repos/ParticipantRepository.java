package com.example.eventmanagement.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.eventmanagement.entities.Participant;
import com.example.eventmanagement.entities.projections.PartialParticipant;

import java.lang.String;

@RepositoryRestResource(excerptProjection =PartialParticipant.class )
public interface ParticipantRepository extends PagingAndSortingRepository<Participant, Long> {
	Page<Participant> findByEmail(@Param("email") String email, Pageable pageable);

	Participant findByEmail(String emailId);
}
