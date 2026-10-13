public class CuentaController {
    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping("/{id}")
    public Cuenta buscarCuentaPorId(@PathVariable String id) {
        return cuentaService.buscarCuentaPorId(id);
    }

    @PostMapping
    public Cuenta guardarCuenta(@RequestBody Cuenta cuenta) {
        return cuentaService.guardarCuenta(cuenta);
    }

    @DeleteMapping("/{id}")
    public void eliminarCuenta(@PathVariable String id) {
        cuentaService.eliminarCuenta(id);
    }

    @PutMapping("/{id}")
    public Cuenta editarCuenta(@PathVariable String id, @RequestBody Cuenta cuenta) {
        return cuentaService.editarCuenta(cuenta);
    }
}