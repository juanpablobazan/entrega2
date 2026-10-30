package com.clientes.banco.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.clientes.banco.entities.CuentaStandard;
import com.clientes.banco.services.CuentaStandardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cuentas standard")
@RestController
@RequestMapping("/api/cuenta-standard")
public class CuentaStandardController {
    @Autowired
    private CuentaStandardService cuentaStandardService;

    public CuentaStandardController(CuentaStandardService cuentaStandardService) {
        this.cuentaStandardService = cuentaStandardService;
    }

    // GET http://localhost:8080/api/cuenta-standard
    @Operation(summary = "Listar cuentas standard")
    @GetMapping
    public ResponseEntity<List<CuentaStandard>> listar() {
        return ResponseEntity.ok(cuentaStandardService.listar());
    }

    // POST http://localhost:8080/api/cuenta-standard
    @Operation(summary = "Crear cuenta standard")
    @PostMapping
    public ResponseEntity<CuentaStandard> createCuentaStandard(@RequestBody CuentaStandard cuentaStandard) {
        return ResponseEntity.ok(cuentaStandardService.save(cuentaStandard));
    }

    // GET http://localhost:8080/api/cuenta-standard/1
    @Operation(summary = "Consultar cuenta standard por id")
    @GetMapping("/{id}")
    public ResponseEntity<CuentaStandard> getCuentaStandardById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaStandardService.findById(id));
    }
    
    // PUT http://localhost:8080/api/cuenta-standard/1
    @Operation(summary = "Editar cuenta standard")
    @PutMapping("/{id}")
    public ResponseEntity<CuentaStandard> updateCuentaStandard(@PathVariable Long id, @RequestBody CuentaStandard cuentaStandard) {
        return ResponseEntity.ok(cuentaStandardService.update(id, cuentaStandard));
    }

    // DELETE http://localhost:8080/api/cuenta-standard/1
    @Operation(summary = "Eliminar cuenta standard")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuentaStandard(@PathVariable Long id) {
        cuentaStandardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}