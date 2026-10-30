package com.clientes.banco.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.clientes.banco.entities.Cuenta;
import com.clientes.banco.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cuentas")
@RestController
@RequestMapping("/api/cuenta")
public class CuentaController {
    @Autowired
    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    // GET http://localhost:8080/api/cuenta/cuentas
    @Operation(summary = "Listar cuentas")
    @GetMapping("/cuentas")
    public List<Cuenta> listar() {
        return cuentaService.listar();
    }

    // GET http://localhost:8080/api/cuenta/1
    @Operation(summary = "Consultar cuenta por id")
    @GetMapping("/{id}")
    public Cuenta buscarCuentaPorId(@PathVariable Long id) {
        return cuentaService.buscarCuentaPorId(id);
    }

    // POST http://localhost:8080/api/cuenta
    @Operation(summary = "Crear cuenta")
    @PostMapping
    public Cuenta guardarCuenta(@RequestBody Cuenta cuenta) {
        return cuentaService.guardarCuenta(cuenta);
    }

    // DELETE http://localhost:8080/api/cuenta/1
    @Operation(summary = "Eliminar cuenta")
    @DeleteMapping("/{id}")
    public void eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
    }

    // PUT http://localhost:8080/api/cuenta/1
    @Operation(summary = "Editar cuenta")
    @PutMapping("/{id}")
    public Cuenta editarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        return cuentaService.editarCuenta(id, cuenta);
    }
}