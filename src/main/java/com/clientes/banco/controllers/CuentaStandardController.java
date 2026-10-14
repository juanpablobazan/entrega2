package com.clientes.banco.controllers;

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

    @PostMapping
    public ResponseEntity<CuentaStandard> createCuentaStandard(@RequestBody CuentaStandard cuentaStandard) {
        return ResponseEntity.ok(cuentaStandardService.save(cuentaStandard));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaStandard> getCuentaStandardById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaStandardService.findById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CuentaStandard> updateCuentaStandard(@PathVariable Long id, @RequestBody CuentaStandard cuentaStandard) {
        return ResponseEntity.ok(cuentaStandardService.update(cuentaStandard));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuentaStandard(@PathVariable Long id) {
        cuentaStandardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}