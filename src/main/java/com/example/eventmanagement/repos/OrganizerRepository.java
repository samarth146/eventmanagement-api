package com.example.eventmanagement.repos;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.eventmanagement.entities.Organizer;

public interface OrganizerRepository extends PagingAndSortingRepository<Organizer, Long> {

}
