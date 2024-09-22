package com.petEvent.petEvent.Service;

import com.petEvent.petEvent.Entity.Event;
import com.petEvent.petEvent.Entity.Pet;
import com.petEvent.petEvent.Repository.EventRepository;
import com.petEvent.petEvent.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

@Service
public class PetService {
    @Autowired
    public PetRepository petRepository;
    @Autowired
    public EventRepository eventRepository;

    public List<Pet> getAllPet(String species) {
        if (species == null) {
            return petRepository.findAll();
        } else {
            return petRepository.findBySpecies(species);
        }
    }

    public Pet getPetAndEvents(int id, String sortBy, String sortOrder) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found"));
        List<Event> events = pet.getEvents();
        if (events != null && !events.isEmpty()) {
            Comparator<Event> comparator;
            switch (sortBy.toLowerCase()) {
                case "type":
                    comparator = Comparator.comparing(Event::getType, String.CASE_INSENSITIVE_ORDER);
                    break;
                case "remark":
                    comparator = Comparator.comparing(Event::getRemark, String.CASE_INSENSITIVE_ORDER);
                    break;
                case "date":
                default:
                    comparator = Comparator.comparing(Event::getDate);
                    break;
            }
            if (sortOrder.equalsIgnoreCase("desc")) {
                comparator = comparator.reversed();
            }
            events.sort(comparator);
        }
        return pet;
    }


    public Pet addPet(Pet pet) {
        if (pet.getEvents() != null) {
            System.out.println("=========================================="+pet.getEvents());
            for (Event event : pet.getEvents()) {
                event.setPet(pet);  // Set the Pet reference for each event
            }
        }
        return petRepository.save(pet);  // Cascade will save both Pet and its Events
    }

    public Pet updatePet(int petId, Pet pet) {
        Pet existingPet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        existingPet.setName(pet.getName());
        existingPet.setOwner(pet.getOwner());
        existingPet.setBirth(pet.getBirth());
        existingPet.setSex(pet.getSex());
        existingPet.setDeath(pet.getDeath());
        existingPet.setSpecies(pet.getSpecies());

        if (pet.getEvents() != null) {
            for (Event newEvent : pet.getEvents()) {
                Event existingEvent = existingPet.getEvents()
                        .stream()
                        .filter(event -> event.getId() == newEvent.getId())
                        .findFirst()
                        .orElse(null);

                if (existingEvent != null) {
                    existingEvent.setDate(newEvent.getDate());
                    existingEvent.setType(newEvent.getType());
                    existingEvent.setRemark(newEvent.getRemark());
                } else {
                    // Add new event
                    newEvent.setPet(existingPet);
                    existingPet.getEvents().add(newEvent);
                }
            }
        }

        return petRepository.save(existingPet);
    }


    public void deletePet(int petid) {
        petRepository.deleteById(petid);
    }

    public Event addEvent(int id, Event event) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found"));
        event.setPet(pet);
        return eventRepository.save(event);

    }
}
