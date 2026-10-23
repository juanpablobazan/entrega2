package com.clientes.banco.services;

import java.util.List;

import com.clientes.banco.entities.Cuenta;
import com.clientes.banco.repositories.CuentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CuentaService{
    @Autowired
    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public List<Cuenta> listar() {
        return cuentaRepository.findAll();
    }

    public Cuenta buscarCuentaPorId(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    public Cuenta guardarCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public void eliminarCuenta(Long id) {
        cuentaRepository.deleteById(id);
    }

    public Cuenta editarCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }
}