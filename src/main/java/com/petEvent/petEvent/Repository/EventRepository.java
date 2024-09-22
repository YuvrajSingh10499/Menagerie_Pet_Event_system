package com.petEvent.petEvent.Repository;

import com.petEvent.petEvent.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Integer> {

}
