package com.example.eventmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventmanagement.controllers.exceptioins.AlreadyCheckedInException;
import com.example.eventmanagement.entities.Participant;
import com.example.eventmanagement.entities.Venue;
import com.example.eventmanagement.repos.ParticipantRepository;
import com.example.eventmanagement.repos.VenueRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
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
	public ResponseEntity<Participant> getParticipantByEmailId(@PathVariable String emailId,PersistentEntityResourceAssembler assembler) {
		//fetching participant data based on email Id
		Participant participant =participantRepository.findByEmail(emailId);
		if (participant == null) {
			return null;
		}
		return new ResponseEntity<Participant>(participant,HttpStatus.OK);
		
	}
	@RequestMapping(value="/getVenueByPostalCode/{postCode}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Venue> getVenueByPostalCode(@PathVariable String postCode) {
		// fetching Venue data based on postal code
		Venue venue = venueRepository.findByPostalCode(postCode);
		try {
			return new ResponseEntity<Venue>(venue,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
