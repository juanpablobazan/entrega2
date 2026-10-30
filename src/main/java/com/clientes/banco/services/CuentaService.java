package com.clientes.banco.services;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.clientes.banco.entities.Cuenta;
import com.clientes.banco.exceptions.ErrorBaseDatosException;
import com.clientes.banco.exceptions.RecursoNoEncontradoException;
import com.clientes.banco.repositories.CuentaRepository;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public List<Cuenta> listar() {
        try {
            return cuentaRepository.findAll();
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al listar las cuentas", ex);
        }
    }

    public Cuenta buscarCuentaPorId(Long id) {
        try {
            return cuentaRepository.findById(id)
                    .orElseThrow(() -> new RecursoNoEncontradoException("No existe la cuenta con id " + id));
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al buscar la cuenta con id " + id, ex);
        }
    }

    public Cuenta guardarCuenta(Cuenta cuenta) {
        try {
            return cuentaRepository.save(cuenta);
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al guardar la cuenta", ex);
        }
    }

    public void eliminarCuenta(Long id) {
        try {
            if (!cuentaRepository.existsById(id)) {
                throw new RecursoNoEncontradoException("No existe la cuenta con id " + id);
            }
            cuentaRepository.deleteById(id);
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al eliminar la cuenta con id " + id, ex);
        }
    }

    public Cuenta editarCuenta(Long id, Cuenta cuenta) {
        try {
            if (!cuentaRepository.existsById(id)) {
                throw new RecursoNoEncontradoException("No existe la cuenta con id " + id);
            }
            cuenta.setId(id);
            return cuentaRepository.save(cuenta);
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al editar la cuenta con id " + id, ex);
        }
    }
}
