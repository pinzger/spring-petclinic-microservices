package org.springframework.samples.petclinic.vets.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.vets.model.Specialty;
import org.springframework.samples.petclinic.vets.model.SpecialtyRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/specialties")
@RestController
@RequiredArgsConstructor
@Slf4j
public class SpecialtyResource {
    private final SpecialtyRepository specialtyRepository;

    /**
     * Create Specialty
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Specialty createSpecialty(@Valid @RequestBody Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    /**
     * Read single Specialty
     */
    @GetMapping(value = "/{specialtyId}")
    public Optional<Specialty> findSpecialty(@PathVariable("specialtyId") int specialtyId) {
        log.info("called findSpecialty(...) of customers service");
        return specialtyRepository.findById(specialtyId);
    }

    @GetMapping
    public List<Specialty> getSpecialties() {
        return specialtyRepository.findAll();
    }

    /**
     * Update Specialty
     */
    @PutMapping(value = "/{specialtyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSpecialty(@PathVariable("specialtyId") int specialtyId, @Valid @RequestBody Specialty specialtyRequest) {
        log.info("called updateSpecialty(...)");
        final Optional<Specialty> specialty = specialtyRepository.findById(specialtyId);

        final Specialty specialtyModel = specialty.orElseThrow(() -> new ResourceNotFoundException("Specialty "+specialtyId+" not found"));
        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
        specialtyModel.setName(specialtyRequest.getName());
        log.info("Saving specialty {}", specialtyModel);
        specialtyRepository.save(specialtyModel);
    }

    /**
     * Delete Specialty
     */
    @DeleteMapping(value = "/{specialtyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpecialty(@PathVariable("specialtyId") int specialtyId, @Valid @RequestBody Specialty specialtyRequest) {
        log.info("called deleteSpecialty(...)");
        final Optional<Specialty> specialty = specialtyRepository.findById(specialtyId);

        final Specialty specialtyModel = specialty.orElseThrow(() -> new ResourceNotFoundException("Specialty "+specialtyId+" not found"));
        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
        log.info("Deleting specialty {}", specialtyModel);
        specialtyRepository.delete(specialtyModel);
    }
}
