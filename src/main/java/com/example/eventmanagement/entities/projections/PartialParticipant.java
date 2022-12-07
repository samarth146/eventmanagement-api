package com.example.eventmanagement.entities.projections;

import org.springframework.data.rest.core.config.Projection;

import com.example.eventmanagement.entities.Participant;

@Projection(name ="partialParticipant", types = { Participant.class })
public interface PartialParticipant {
	
	String getName();
	Boolean getCheckedIn();
}
