package com.clientes.banco.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.clientes.banco.entities.CuentaStandard;
import com.clientes.banco.repositories.CuentaStandardRepository;

@ExtendWith(MockitoExtension.class)
class CuentaStandardServiceTest {

    @Mock
    private CuentaStandardRepository cuentaStandardRepository;

    @InjectMocks
    private CuentaStandardService cuentaStandardService;

    @Test
    void updateConservaElIdDeLaRuta() {
        CuentaStandard cuenta = new CuentaStandard(99L, "001", 10f, null, 0);
        when(cuentaStandardRepository.existsById(7L)).thenReturn(true);
        when(cuentaStandardRepository.save(any(CuentaStandard.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CuentaStandard resultado = cuentaStandardService.update(7L, cuenta);

        assertEquals(7L, resultado.getId());
    }
}
