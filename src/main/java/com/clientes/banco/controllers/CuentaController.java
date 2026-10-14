package com.clientes.banco.controllers;

import org.springframework.web.bind.annotation.*;
import com.clientes.banco.entities.Cuenta;
import com.clientes.banco.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/cuenta")
public class CuentaController {
    @Autowired
    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping("/{id}")
    public Cuenta buscarCuentaPorId(@PathVariable Long id) {
        return cuentaService.buscarCuentaPorId(id);
    }

    @PostMapping
    public Cuenta guardarCuenta(@RequestBody Cuenta cuenta) {
        return cuentaService.guardarCuenta(cuenta);
    }

    @DeleteMapping("/{id}")
    public void eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
    }

    @PutMapping("/{id}")
    public Cuenta editarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        return cuentaService.editarCuenta(cuenta);
    }
}