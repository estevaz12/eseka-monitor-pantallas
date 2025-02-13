package ar.com.leo.monitor.fx.service;

import ar.com.leo.monitor.jdbc.MaquinaDAO;
import ar.com.leo.monitor.model.Maquina;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


public class MaquinaService extends ScheduledService<Object[]> {

    private final SimpleListProperty<String> groupCodes = new SimpleListProperty<>(this, "groupCodes");
    // Property allows you to change the "groupCode" between executions
    private final SimpleStringProperty groupCode = new SimpleStringProperty(this, "groupCode");
    private final SimpleStringProperty roomCode = new SimpleStringProperty(this, "roomCode");

    public void setGroupCodes(ObservableList<String> groupCodes) {
        this.groupCodes.set(groupCodes);
        this.setGroupCode(groupCodes.get(0));
    }

    public String getGroupCode() {
        return groupCode.get();
    }

    public void setGroupCode(String groupCode) {
        this.groupCode.set(groupCode);
    }

    public String getRoomCode() {
        return roomCode.get();
    }

    public void setRoomCode(String roomCode) {
        this.roomCode.set(roomCode);
    }

    public void changeGroup() {
        int index = groupCodes.indexOf(groupCode.get());

        if (this.groupCode.get().equals("SECTOR")) {
            this.groupCode.set(groupCodes.get(0));
        } else if (index == groupCodes.size() - 1) {
            this.groupCode.set("SECTOR");
        } else {
            this.groupCode.set(groupCodes.get(index + 1));
        }
    }

    @Override
    protected Task<Object[]> createTask() {
        // creates a new Task and gives the current "groupCode" as an argument. This is called every cycle
        return new MaquinaTask(this.getGroupCode(), this.getRoomCode());
    }

    private static class MaquinaTask extends Task<Object[]> {
        // A Task is a one-shot thing and its initial state should be immutable (or at least encapsulated from external modification).
        private final String groupCode;
        private final String roomCode;

        private MaquinaTask(String groupCode, String roomCode) {
            this.groupCode = groupCode;
            this.roomCode = roomCode;
        }

        @Override
        protected Object[] call() {
            if (!"SECTOR".equals(groupCode)) { // eficiencias por maquina y eficiencia total de grupo
                return MaquinaDAO.obtenerEficienciasPorGrupo(this.groupCode);
            } else { // eficiencias por grupo del sector
                Object[] results = new Object[2];
                Object[] eficienciasSector = MaquinaDAO.obtenerEficienciasPorSector(this.roomCode);

                final Map<String, Integer> eficienciasPorGrupo = new LinkedHashMap<>();
                final Map<String, List<Maquina>> maquinasPorGrupo = ((List<Maquina>) eficienciasSector[0]).stream()
                        .collect(Collectors.groupingBy(p -> p.getGroupCode(), () -> new LinkedHashMap<>(), toList()));

                for (Map.Entry<String, List<Maquina>> set : maquinasPorGrupo.entrySet()) {
                    int divisor = 0;
                    int dividendo = 0;

                    for (Maquina maquina : set.getValue()) {
                        divisor += maquina.getWorkEfficiency() * (maquina.getTimeOn() + maquina.getTimeOff());
                        dividendo += maquina.getTimeOn() + maquina.getTimeOff();
                    }
                    Integer eficienciaGrupo = (int) Math.round((double) divisor / dividendo);
                    eficienciasPorGrupo.put(set.getKey().trim(), eficienciaGrupo);
                }

                results[0] = eficienciasPorGrupo; // grupos y eficiencias
                results[1] = eficienciasSector[1]; // eficiencia total

                return results;
            }
        }
    }

}
