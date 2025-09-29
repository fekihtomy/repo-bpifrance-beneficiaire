package com.beneficiaire.controller;

import com.beneficiaire.dto.CreatePersonneDTO;
import com.beneficiaire.dto.PersonneDTO;
import com.beneficiaire.service.PersonneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnes")
@RequiredArgsConstructor
public class PersonneController {

    private final PersonneService personneService;

    @PostMapping
    public ResponseEntity<PersonneDTO> createPersonne(@Valid @RequestBody CreatePersonneDTO dto) {
        PersonneDTO created = personneService.createPersonne(dto);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<PersonneDTO>> getAllPersonnes() {
        return ResponseEntity.ok(personneService.getAllPersonnes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonneDTO> getPersonneById(@PathVariable Long id) {
        return ResponseEntity.ok(personneService.getPersonneById(id));
    }
}
