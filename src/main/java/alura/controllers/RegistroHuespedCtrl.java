package alura.controllers;

import alura.models.Huesped;
import alura.models.Reserva;
import alura.repositories.HuespedRepositoryImpl;
import alura.repositories.IRepository;
import alura.services.HuespedeService;
import static alura.services.ListNacionalidadesService.nacionalidades;

import alura.views.RegistroHuespedFrm;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

public class RegistroHuespedCtrl {

    private final RegistroHuespedFrm registroHuespedFrm;
    RegistroReservasCtrl registroReservasCtrl;
    MenuUsuarioCtrl menuUsuarioCtrl;
    IRepository<Huesped> repository = new HuespedRepositoryImpl();
    HuespedeService service = new HuespedeService(repository);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public RegistroHuespedCtrl(int idReserva) {
        registroHuespedFrm = new RegistroHuespedFrm();
        registroHuespedFrm.getTxtNumeroReserva().setText("" + idReserva);

        nacionalidades.forEach(n -> registroHuespedFrm.getCmbNacionalidad().addItem(n));

        registroHuespedFrm.getBtnAtras().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                registroReservasCtrl = new RegistroReservasCtrl();
                registroReservasCtrl.init();
                registroHuespedFrm.dispose();
            }
            
        });
        registroHuespedFrm.getBtnGuardar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                validarDatos();
            }
            
        });
    }
    
    private void validarDatos() {
        String nombre = registroHuespedFrm.getTxtNombre().getText();
        String apellido = registroHuespedFrm.getTxtApellido().getText();
        String fecha;
        try {
            fecha = sdf.format(registroHuespedFrm.getjDateChooser().getCalendar().getTime());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(registroHuespedFrm, "Seleccionar la fecha",
                    "Campos vacíos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String nacionalidad = (String) registroHuespedFrm.getCmbNacionalidad().getSelectedItem();
        String telefono = registroHuespedFrm.getTxtTelefono().getText();
        int idReserva = Integer.parseInt(registroHuespedFrm.getTxtNumeroReserva().getText());

        if (nombre.isEmpty() || apellido.isEmpty() || fecha.isEmpty() || nacionalidad == null || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(registroHuespedFrm, "Debes llenar todos los campos",
                    "Campos vacíos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Huesped huesped = new Huesped();
        Reserva reserva = new Reserva();
        huesped.setNombre(nombre);
        huesped.setApellido(apellido);
        huesped.setFechaNacimiento(fecha);
        huesped.setNacionalidad(nacionalidad);
        huesped.setTelefono(telefono);
        reserva.setId(idReserva);
        huesped.setReserva(reserva);
        service.crearHuesped(huesped);

        JOptionPane.showMessageDialog(registroHuespedFrm, "El huésped se ha registrado con éxito.",
                "Registro Huésped", JOptionPane.INFORMATION_MESSAGE);
        menuUsuarioCtrl = new MenuUsuarioCtrl();
        menuUsuarioCtrl.init();
        registroHuespedFrm.dispose();
    }
    
    public void init() {
        registroHuespedFrm.setVisible(true);
    }
    

    
    
}