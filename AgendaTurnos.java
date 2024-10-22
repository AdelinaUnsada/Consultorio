package nico.gestionturnos;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AgendaTurnos {
    private ArrayList<Turno> turnos;
    private static final String FILE_PATH = "turnos.json";

    public AgendaTurnos() {
        this.turnos = new ArrayList<>();
        cargarTurnos();
    }

    public void agregarTurno(Turno turno) {
        turnos.add(turno);
        guardarTurnos();
    }

    public void eliminarTurno(Turno turno) {
        turnos.remove(turno);
        guardarTurnos();
    }

    public ArrayList<Turno> getTurnos() {
        return turnos;
    }

    private void guardarTurnos() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            new Gson().toJson(turnos, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar los turnos.");
        }
    }

    private void cargarTurnos() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            turnos = new Gson().fromJson(reader, new TypeToken<ArrayList<Turno>>() {}.getType());
        } catch (IOException e) {
            turnos = new ArrayList<>();
        }
    }
}
