package org.springframework.samples.petclinic.customers.web;

import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.model.OwnerRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OwnerResource.class)
@ActiveProfiles("test")
public class OwnerResourceTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    OwnerRepository ownerRepository;

    @Test
    void shouldGetAOwnerInJSonFormt() throws Exception {
        Owner owner = setupOwner();

        given(ownerRepository.findById(2)).willReturn(Optional.of(owner));
        mvc.perform(get("/owners/2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.firstName").value("Betty"))
            .andExpect(jsonPath("$.lastName").value("Davis"))
            .andExpect(jsonPath("$.email").value("b.davis@x.com"));
    }

    private Owner setupOwner() {
        Owner owner = new Owner();
        owner.setId(2);
        owner.setFirstName("Betty");
        owner.setLastName("Davis");
        owner.setEmail("b.davis@x.com");

        return owner;
    }
}
