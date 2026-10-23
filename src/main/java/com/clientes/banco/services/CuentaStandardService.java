package com.clientes.banco.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clientes.banco.entities.CuentaStandard;
import com.clientes.banco.repositories.CuentaStandardRepository;

@Service
public class CuentaStandardService {
    @Autowired
    private CuentaStandardRepository cuentaStandardRepository;

    public CuentaStandardService(CuentaStandardRepository cuentaStandardRepository) {
        this.cuentaStandardRepository = cuentaStandardRepository;
    }

    public List<CuentaStandard> listar() {
        return cuentaStandardRepository.findAll();
    }

    public CuentaStandard save(CuentaStandard cuentaStandard) {
        return cuentaStandardRepository.save(cuentaStandard);
    }

    public CuentaStandard findById(Long id) {
        return cuentaStandardRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        cuentaStandardRepository.deleteById(id);
    }

    public CuentaStandard update(CuentaStandard cuentaStandard) {
        return cuentaStandardRepository.save(cuentaStandard);
    }
}