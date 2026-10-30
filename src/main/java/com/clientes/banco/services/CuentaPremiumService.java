package com.clientes.banco.services;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.clientes.banco.entities.CuentaPremium;
import com.clientes.banco.exceptions.ErrorBaseDatosException;
import com.clientes.banco.exceptions.RecursoNoEncontradoException;
import com.clientes.banco.repositories.CuentaPremiumRepository;

@Service
public class CuentaPremiumService {

    private final CuentaPremiumRepository cuentaPremiumRepository;

    public CuentaPremiumService(CuentaPremiumRepository cuentaPremiumRepository) {
        this.cuentaPremiumRepository = cuentaPremiumRepository;
    }

    public List<CuentaPremium> listar() {
        try {
            return cuentaPremiumRepository.findAll();
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al listar las cuentas premium", ex);
        }
    }

    public CuentaPremium save(CuentaPremium cuentaPremium) {
        try {
            return cuentaPremiumRepository.save(cuentaPremium);
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al guardar la cuenta premium", ex);
        }
    }

    public CuentaPremium findById(Long id) {
        try {
            return cuentaPremiumRepository.findById(id)
                    .orElseThrow(() -> new RecursoNoEncontradoException("No existe la cuenta premium con id " + id));
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al buscar la cuenta premium con id " + id, ex);
        }
    }

    public void deleteById(Long id) {
        try {
            if (!cuentaPremiumRepository.existsById(id)) {
                throw new RecursoNoEncontradoException("No existe la cuenta premium con id " + id);
            }
            cuentaPremiumRepository.deleteById(id);
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al eliminar la cuenta premium con id " + id, ex);
        }
    }

    public CuentaPremium update(Long id, CuentaPremium cuentaPremium) {
        try {
            if (!cuentaPremiumRepository.existsById(id)) {
                throw new RecursoNoEncontradoException("No existe la cuenta premium con id " + id);
            }
            cuentaPremium.setId(id);
            return cuentaPremiumRepository.save(cuentaPremium);
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al editar la cuenta premium con id " + id, ex);
        }
    }
}
