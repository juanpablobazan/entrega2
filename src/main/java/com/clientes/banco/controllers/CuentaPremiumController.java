package com.clientes.banco.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.clientes.banco.entities.CuentaPremium;
import com.clientes.banco.services.CuentaPremiumService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cuentas premium")
@RestController
@RequestMapping("/api/cuenta-premium")
public class CuentaPremiumController {
    @Autowired
    private CuentaPremiumService cuentaPremiumService;

    public CuentaPremiumController(CuentaPremiumService cuentaPremiumService) {
        this.cuentaPremiumService = cuentaPremiumService;
    }

    // GET http://localhost:8080/api/cuenta-premium
    @Operation(summary = "Listar cuentas premium")
    @GetMapping
    public ResponseEntity<List<CuentaPremium>> listar() {
        return ResponseEntity.ok(cuentaPremiumService.listar());
    }

    // POST http://localhost:8080/api/cuenta-premium
    @Operation(summary = "Crear cuenta premium")
    @PostMapping
    public ResponseEntity<CuentaPremium> createCuentaPremium(@RequestBody CuentaPremium cuentaPremium) {
        return ResponseEntity.ok(cuentaPremiumService.save(cuentaPremium));
    }

    // GET http://localhost:8080/api/cuenta-premium/1
    @Operation(summary = "Consultar cuenta premium por id")
    @GetMapping("/{id}")
    public ResponseEntity<CuentaPremium> getCuentaPremiumById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaPremiumService.findById(id));
    }

    // PUT http://localhost:8080/api/cuenta-premium/1
    @Operation(summary = "Editar cuenta premium")
    @PutMapping("/{id}")
    public ResponseEntity<CuentaPremium> updateCuentaPremium(@PathVariable Long id, @RequestBody CuentaPremium cuentaPremium) {
        return ResponseEntity.ok(cuentaPremiumService.update(id, cuentaPremium));
    }

    // DELETE http://localhost:8080/api/cuenta-premium/1
    @Operation(summary = "Eliminar cuenta premium")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuentaPremium(@PathVariable Long id) {
        cuentaPremiumService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}