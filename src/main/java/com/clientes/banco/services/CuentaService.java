public class CuentaService{
    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public Cuenta buscarCuentaPorId(String id) {
        return cuentaRepository.findById(id);
    }

    public Cuenta guardarCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public void eliminarCuenta(String id) {
        cuentaRepository.deleteById(id);
    }

    public Cuenta editarCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }
}