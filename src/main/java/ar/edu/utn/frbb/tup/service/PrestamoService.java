package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.PrestamoResultado;
import ar.edu.utn.frbb.tup.model.enums.EstadoPrestamo;
import ar.edu.utn.frbb.tup.model.exception.prestamos.PrestamoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.PrestamoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoService {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private PrestamoDao prestamoDao;

    @Autowired
    private ScoreCreditService scoreCreditService;


    public PrestamoResultado solicitarPrestamo (PrestamoDto prestamoDto) throws  Exception {
        Prestamo prestamo = new Prestamo(prestamoDto);
        if (!scoreCreditService.verifyScore(prestamo.getNumeroCliente())) {
            PrestamoResultado prestamoResultado = new PrestamoResultado();
            prestamoResultado.setEstado(EstadoPrestamo.RECHAZADO);
            prestamoResultado.setMensaje("El cliente no tiene un credito apto para solicitar un prestamo");
            return prestamoResultado;
        }
        clienteService.agregarPrestamo(prestamo, prestamo.getNumeroCliente());
        cuentaService.actualizarCuenta(prestamo);
        prestamoDao.save(prestamo);

        PrestamoResultado prestamoResultado = new PrestamoResultado();
        prestamoResultado.setEstado(EstadoPrestamo.APROBADO);
        prestamoResultado.setMensaje("El monto del préstamo fue acreditado en su cuenta");
        prestamoResultado.setPlanPago(prestamo.getPlazoMeses(), prestamo.getMontoPedido());
        return prestamoResultado;
        }

        public List<Prestamo> getPrestamosByCliente(long dni) throws  Exception{
            clienteService.buscarClientePorDni(dni);
            return prestamoDao.getPrestamosByCliente(dni);
        }

        public Prestamo pagarCuota(long id) throws  Exception{
            Prestamo prestamo = prestamoDao.find(id);
            if (prestamo == null) {
                throw new PrestamoNotFoundException("El prestamo no existe");
            }
            cuentaService.pagarCuotaPrestamo(prestamo);
            prestamo.pagarCuota();
            prestamoDao.save(prestamo);
            return prestamo;
        }
    }

