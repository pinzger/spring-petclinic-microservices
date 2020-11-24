/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vets.web;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.vets.model.Vet;
import org.springframework.samples.petclinic.vets.model.VetRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Maciej Szarlinski
 */
@RequestMapping("/vets")
@RestController
@RequiredArgsConstructor
@Slf4j
class VetResource {
    private final VetRepository vetRepository;

    /**
     * Create Vet
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vet createVet(@Valid @RequestBody Vet vet) {
        return vetRepository.save(vet);
    }

    @GetMapping
    public List<Vet> findAll() {
        log.info("called findAll {}", this.getClass().getName());
        return vetRepository.findAll();
    }

    /**
     * Read single Vet
     */
    @GetMapping(value = "/{vetId}")
    public Optional<Vet> findVet(@PathVariable("vetId") int vetId) {
        log.info("called findVet(...)");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return vetRepository.findById(vetId);
    }

    /**
     * Update Vet
     */
    @PutMapping(value = "/{vetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVet(@PathVariable("vetId") int vetId, @Valid @RequestBody Vet vetRequest) {
        log.info("called updateVet(...)");
        final Optional<Vet> vet = vetRepository.findById(vetId);

        final Vet vetModel = vet.orElseThrow(() -> new ResourceNotFoundException("Vet "+vetId+" not found"));
        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
        vetModel.setFirstName(vetRequest.getFirstName());
        vetModel.setLastName(vetRequest.getLastName());
        log.info("Saving vet {}", vetModel);
        vetRepository.save(vetModel);
    }

    /**
     * Delete Vet
     */
    @DeleteMapping(value = "/{vetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVet(@PathVariable("vetId") int vetId, @Valid @RequestBody Vet vetRequest) {
        log.info("called deleteVet(...)");
        final Optional<Vet> vet = vetRepository.findById(vetId);

        final Vet vetModel = vet.orElseThrow(() -> new ResourceNotFoundException("Vet "+vetId+" not found"));
        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
        log.info("Deleting vet {}", vetModel);
        vetRepository.delete(vetModel);
    }
}
