package org.springframework.samples.petclinic.customers.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.customers.model.PetRepository;
import org.springframework.samples.petclinic.customers.model.PetType;
import org.springframework.samples.petclinic.customers.model.PetTypeRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/petTypes")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PetTypeResource {
    private final PetTypeRepository petTypeRepository;

    /**
     * Create PetType
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetType createPetType(@Valid @RequestBody PetType petType) {
        return petTypeRepository.save(petType);
    }

    /**
     * Read single Specialty
     */
    @GetMapping(value = "/{petTypeId}")
    public Optional<PetType> findPetType(@PathVariable("petTypeId") int petTypeId) {
        log.info("called findPetType(...) of customers service");
        return petTypeRepository.findById(petTypeId);
    }

    @GetMapping
    public List<PetType> getPetTypes() {
        return petTypeRepository.findAll();
    }

    /**
     * Update PetType
     */
    @PutMapping(value = "/{petTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSpecialty(@PathVariable("petTypeId") int petTypeId, @Valid @RequestBody PetType petTypeRequest) {
        log.info("called updatePetType(...)");
        final Optional<PetType> petType = petTypeRepository.findById(petTypeId);

        final PetType petTypeModel = petType.orElseThrow(() -> new ResourceNotFoundException("PetType "+petTypeId+" not found"));
        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
        petTypeModel.setName(petTypeRequest.getName());
        log.info("Saving pettype {}", petTypeModel);
        petTypeRepository.save(petTypeModel);
    }

    /**
     * Delete PetType
     */
    @DeleteMapping(value = "/{petTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpecialty(@PathVariable("petTypeId") int petTypeId, @Valid @RequestBody PetType petTypeRequest) {
        log.info("called deletePetType(...)");
        final Optional<PetType> specialty = petTypeRepository.findById(petTypeId);

        final PetType specialtyModel = specialty.orElseThrow(() -> new ResourceNotFoundException("PetType "+petTypeId+" not found"));
        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
        log.info("Deleting pettype {}", specialtyModel);
        petTypeRepository.delete(specialtyModel);
    }

}
