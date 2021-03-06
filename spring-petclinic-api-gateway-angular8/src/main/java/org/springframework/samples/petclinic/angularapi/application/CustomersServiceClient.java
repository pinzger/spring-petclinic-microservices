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
package org.springframework.samples.petclinic.angularapi.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.samples.petclinic.angularapi.dto.OwnerDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Maciej Szarlinski
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomersServiceClient {

    private final WebClient.Builder webClientBuilder;

    public Mono<OwnerDetails> getOwner(final int ownerId) {
        log.info("called " + this.getClass().getName() + " getOwner");
        return webClientBuilder.build().get()
            .uri("http://customers-service/owners/{ownerId}", ownerId)
            .retrieve()
            .bodyToMono(OwnerDetails.class); // Martin, TODO: what happens if the Owner object in the customerservices changes?
    }
}
