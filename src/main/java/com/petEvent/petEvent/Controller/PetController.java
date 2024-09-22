package com.petEvent.petEvent.Controller;
import com.petEvent.petEvent.Entity.Event;
import com.petEvent.petEvent.Entity.Pet;
import com.petEvent.petEvent.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
     private PetService petService;

    @GetMapping
    public List<Pet> getAllPet(@RequestParam(required = false) String species) {
        return petService.getAllPet(species);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetAndEvents(
            @PathVariable int id,
            @RequestParam(required = false, defaultValue = "date") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {

        Pet pet = petService.getPetAndEvents(id, sortBy, sortOrder);
        return ResponseEntity.ok(pet);
    }


    @PostMapping
    public Pet addPet(@RequestBody Pet pet) {
        return petService.addPet(pet);
    }

    @PutMapping("/{id}")
    public Pet updatePet(@RequestBody Pet pet, @PathVariable int id) {
        return petService.updatePet(id, pet);
    }

    @DeleteMapping("{id}")
    public void deleteId(@PathVariable int id) {
        petService.deletePet(id);
    }


    @PostMapping("{id}")
    public Event addEvent(@RequestBody Event event, @PathVariable int id) {
        return petService.addEvent(id, event);
    }

}
