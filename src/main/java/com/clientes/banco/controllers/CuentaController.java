package com.clientes.banco.controllers;

import java.util.List;

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

    // GET http://localhost:8080/api/cuenta/cuentas
    @GetMapping("/cuentas")
    public List<Cuenta> listar() {
        return cuentaService.listar();
    }

    // GET http://localhost:8080/api/cuenta/1
    @GetMapping("/{id}")
    public Cuenta buscarCuentaPorId(@PathVariable Long id) {
        return cuentaService.buscarCuentaPorId(id);
    }

    // POST http://localhost:8080/api/cuenta
    @PostMapping
    public Cuenta guardarCuenta(@RequestBody Cuenta cuenta) {
        return cuentaService.guardarCuenta(cuenta);
    }

    // DELETE http://localhost:8080/api/cuenta/1
    @DeleteMapping("/{id}")
    public void eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
    }

    // PUT http://localhost:8080/api/cuenta/1
    @PutMapping("/{id}")
    public Cuenta editarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        return cuentaService.editarCuenta(cuenta);
    }
}