package com.example.eventmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.eventmanagement.controllers.exceptioins.AlreadyCheckedInException;
import com.example.eventmanagement.entities.Participant;
import com.example.eventmanagement.entities.Venue;
import com.example.eventmanagement.repos.ParticipantRepository;
import com.example.eventmanagement.repos.VenueRepository;

@RepositoryRestController
@RequestMapping("/events")
public class CheckInController {

	@Autowired
	private ParticipantRepository participantRepository;
	
	@Autowired
	private VenueRepository venueRepository;

	@PostMapping("/checkin/{id}")
	public ResponseEntity<PersistentEntityResource> checkIn(@PathVariable Long id,PersistentEntityResourceAssembler assembler) {

		Participant participant = participantRepository.findOne(id);

		if (participant != null) {
			if (participant.getCheckedIn()) {
				throw new AlreadyCheckedInException();
			}
			participant.setCheckedIn(true);
			participantRepository.save(participant);
		}

		return ResponseEntity.ok(assembler.toResource(participant));

	}
	
	@GetMapping("/getParticipant/{emailId}")
	public Participant getParticipantByEmailId(@PathVariable String emailId,PersistentEntityResourceAssembler assembler) {
		//fetching participant data based on email Id
		Participant participant =participantRepository.findByEmail(emailId);
		if (participant == null) {
			return null;
		}
		return participant;
		
	}
	@GetMapping("/getVenueByPostalCode/{postCode}")
	public Venue getVenureByPostalCode(@PathVariable String postCode,PersistentEntityResourceAssembler assembler) {
		//fetching Venue data based on postal code
		Venue venue =venueRepository.findByPostalCode(postCode);
		if (venue == null) {
			return null;
		}
		return venue;
		
	}
}
