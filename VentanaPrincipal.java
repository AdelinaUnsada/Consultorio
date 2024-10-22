package nico.gestionturnos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VentanaPrincipal extends JFrame {
    private JTextField nombreCampo, apellidoCampo, dniCampo, obraSocialCampo;
    private JSpinner fechaSpinner, horaSpinner;
    private JTextArea areaTexto;
    private AgendaTurnos agenda;

    public VentanaPrincipal() {
        agenda = new AgendaTurnos();
        setTitle("Gestión de Turnos");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(6, 2));
        nombreCampo = new JTextField();
        apellidoCampo = new JTextField();
        dniCampo = new JTextField();
        obraSocialCampo = new JTextField();
        fechaSpinner = new JSpinner(new SpinnerDateModel());
        horaSpinner = new JSpinner(new SpinnerDateModel());

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(nombreCampo);
        panelFormulario.add(new JLabel("Apellido:"));
        panelFormulario.add(apellidoCampo);
        panelFormulario.add(new JLabel("DNI:"));
        panelFormulario.add(dniCampo);
        panelFormulario.add(new JLabel("Obra Social:"));
        panelFormulario.add(obraSocialCampo);
        panelFormulario.add(new JLabel("Fecha:"));
        panelFormulario.add(fechaSpinner);
        panelFormulario.add(new JLabel("Hora:"));
        panelFormulario.add(horaSpinner);

        JPanel panelBotones = new JPanel();
        JButton agregarBtn = new JButton("Agregar");
        JButton listarBtn = new JButton("Listar");
        JButton listarPorObraBtn = new JButton("Listar por Obra Social");
        panelBotones.add(agregarBtn);
        panelBotones.add(listarBtn);
        panelBotones.add(listarPorObraBtn);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);

        agregarBtn.addActionListener(e -> agregarTurno());
        listarBtn.addActionListener(e -> listarTurnos());
        listarPorObraBtn.addActionListener(e -> listarPorObraSocial());

        add(panelFormulario, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void agregarTurno() {
        if (nombreCampo.getText().isEmpty() || dniCampo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int dni = Integer.parseInt(dniCampo.getText());
            Turno turno = new Turno(nombreCampo.getText(), apellidoCampo.getText(), dni, 
                                    obraSocialCampo.getText(), (Date) fechaSpinner.getValue());
            agenda.agregarTurno(turno);
            areaTexto.append("Turno agregado: " + turno + "\n");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "DNI inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarTurnos() {
        areaTexto.setText("Turnos:\n");
        for (Turno turno : agenda.getTurnos()) {
            areaTexto.append(turno + "\n");
        }
    }

    private void listarPorObraSocial() {
        areaTexto.setText("Pacientes por Obra Social:\n");
        Map<String, ArrayList<Turno>> porObraSocial = new HashMap<>();

        for (Turno turno : agenda.getTurnos()) {
            porObraSocial
                .computeIfAbsent(turno.getObraSocial(), k -> new ArrayList<>())
                .add(turno);
        }

        for (String obra : porObraSocial.keySet()) {
            areaTexto.append("\nObra Social: " + obra + "\n");
            for (Turno t : porObraSocial.get(obra)) {
                areaTexto.append(t + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
    }
}
