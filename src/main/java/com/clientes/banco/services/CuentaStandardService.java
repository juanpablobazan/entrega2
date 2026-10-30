package com.clientes.banco.services;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.clientes.banco.entities.CuentaStandard;
import com.clientes.banco.exceptions.ErrorBaseDatosException;
import com.clientes.banco.exceptions.RecursoNoEncontradoException;
import com.clientes.banco.repositories.CuentaStandardRepository;

@Service
public class CuentaStandardService {

    private final CuentaStandardRepository cuentaStandardRepository;

    public CuentaStandardService(CuentaStandardRepository cuentaStandardRepository) {
        this.cuentaStandardRepository = cuentaStandardRepository;
    }

    public List<CuentaStandard> listar() {
        try {
            return cuentaStandardRepository.findAll();
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al listar las cuentas standard", ex);
        }
    }

    public CuentaStandard save(CuentaStandard cuentaStandard) {
        try {
            return cuentaStandardRepository.save(cuentaStandard);
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al guardar la cuenta standard", ex);
        }
    }

    public CuentaStandard findById(Long id) {
        try {
            return cuentaStandardRepository.findById(id)
                    .orElseThrow(() -> new RecursoNoEncontradoException("No existe la cuenta standard con id " + id));
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al buscar la cuenta standard con id " + id, ex);
        }
    }

    public void deleteById(Long id) {
        try {
            if (!cuentaStandardRepository.existsById(id)) {
                throw new RecursoNoEncontradoException("No existe la cuenta standard con id " + id);
            }
            cuentaStandardRepository.deleteById(id);
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al eliminar la cuenta standard con id " + id, ex);
        }
    }

    public CuentaStandard update(Long id, CuentaStandard cuentaStandard) {
        try {
            if (!cuentaStandardRepository.existsById(id)) {
                throw new RecursoNoEncontradoException("No existe la cuenta standard con id " + id);
            }
            cuentaStandard.setId(id);
            return cuentaStandardRepository.save(cuentaStandard);
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al editar la cuenta standard con id " + id, ex);
        }
    }
}
