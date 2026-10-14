package com.clientes.banco.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.clientes.banco.entities.CuentaPremium;
import com.clientes.banco.services.CuentaPremiumService;


@RestController
@RequestMapping("/api/cuenta-premium")
public class CuentaPremiumController {
    @Autowired
    private CuentaPremiumService cuentaPremiumService;

    public CuentaPremiumController(CuentaPremiumService cuentaPremiumService) {
        this.cuentaPremiumService = cuentaPremiumService;
    }

    @PostMapping
    public ResponseEntity<CuentaPremium> createCuentaPremium(@RequestBody CuentaPremium cuentaPremium) {
        return ResponseEntity.ok(cuentaPremiumService.save(cuentaPremium));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaPremium> getCuentaPremiumById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaPremiumService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaPremium> updateCuentaPremium(@PathVariable Long id, @RequestBody CuentaPremium cuentaPremium) {
        return ResponseEntity.ok(cuentaPremiumService.update(cuentaPremium));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuentaPremium(@PathVariable Long id) {
        cuentaPremiumService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}