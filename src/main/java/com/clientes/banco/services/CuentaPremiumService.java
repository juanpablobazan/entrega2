package com.clientes.banco.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.clientes.banco.entities.CuentaPremium;
import com.clientes.banco.repositories.CuentaPremiumRepository;
import org.springframework.stereotype.Service;

@Service
public class CuentaPremiumService {
    @Autowired
    private CuentaPremiumRepository cuentaPremiumRepository;

    public CuentaPremiumService(CuentaPremiumRepository cuentaPremiumRepository) {
        this.cuentaPremiumRepository = cuentaPremiumRepository;
    }

    public List<CuentaPremium> listar() {
        return cuentaPremiumRepository.findAll();
    }

    public CuentaPremium save(CuentaPremium cuentaPremium) {
        return cuentaPremiumRepository.save(cuentaPremium);
    }

    public CuentaPremium findById(Long id) {
        return cuentaPremiumRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        cuentaPremiumRepository.deleteById(id);
    }

    public CuentaPremium update(CuentaPremium cuentaPremium) {
        return cuentaPremiumRepository.save(cuentaPremium);
    }
}