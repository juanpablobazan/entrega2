package com.clientes.banco.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.clientes.banco.entities.CuentaStandard;
import com.clientes.banco.services.CuentaStandardService;

@RestController
@RequestMapping("/api/cuenta-standard")
public class CuentaStandardController {
    @Autowired
    private CuentaStandardService cuentaStandardService;

    public CuentaStandardController(CuentaStandardService cuentaStandardService) {
        this.cuentaStandardService = cuentaStandardService;
    }

    // GET http://localhost:8080/api/cuenta-standard
    @GetMapping
    public ResponseEntity<List<CuentaStandard>> listar() {
        return ResponseEntity.ok(cuentaStandardService.listar());
    }

    // POST http://localhost:8080/api/cuenta-standard
    @PostMapping
    public ResponseEntity<CuentaStandard> createCuentaStandard(@RequestBody CuentaStandard cuentaStandard) {
        return ResponseEntity.ok(cuentaStandardService.save(cuentaStandard));
    }

    // GET http://localhost:8080/api/cuenta-standard/1
    @GetMapping("/{id}")
    public ResponseEntity<CuentaStandard> getCuentaStandardById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaStandardService.findById(id));
    }
    
    // PUT http://localhost:8080/api/cuenta-standard/1
    @PutMapping("/{id}")
    public ResponseEntity<CuentaStandard> updateCuentaStandard(@PathVariable Long id, @RequestBody CuentaStandard cuentaStandard) {
        return ResponseEntity.ok(cuentaStandardService.update(cuentaStandard));
    }

    // DELETE http://localhost:8080/api/cuenta-standard/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuentaStandard(@PathVariable Long id) {
        cuentaStandardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}