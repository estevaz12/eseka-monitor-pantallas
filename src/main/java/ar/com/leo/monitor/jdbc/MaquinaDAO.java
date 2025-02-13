package ar.com.leo.monitor.jdbc;

import ar.com.leo.monitor.model.Maquina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ar.com.leo.monitor.jdbc.DataSourceConfig.dataSource;

// * @author Leo
public class MaquinaDAO {

    public static Object[] obtenerEficienciasPorGrupo(String groupCode) {

        List<Maquina> maquinas = new ArrayList<>();

        final Object[] results = new Object[2];
        Integer eficienciaGrupo = 0;
        Integer divisor = 0;
        Integer dividendo = 0;

        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT w.MachCode, w.GroupCode, w.TimeOn, w.TimeOff, w.WorkEfficiency, w.TimeEfficiency, m.State, m.FunctionKey" +
                    " FROM Weff_Actual_Monitor AS w" +
                    " INNER JOIN MACHINES AS m ON w.MachCode = m.MachCode" +
                    " WHERE w.GroupCode = '" + groupCode + "'" +
                    " ORDER BY w.MachCode");
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Maquina maquina = new Maquina();
                    maquina.setMachCode(rs.getInt("MachCode"));
                    maquina.setGroupCode(rs.getString("GroupCode"));
                    maquina.setTimeOn(rs.getInt("TimeOn"));
                    maquina.setTimeOff(rs.getInt("TimeOff"));
                    maquina.setWorkEfficiency(rs.getInt("WorkEfficiency") > 100 ? rs.getInt("TimeEfficiency") : rs.getInt("WorkEfficiency"));
                    maquina.setTimeEfficiency(rs.getInt("TimeEfficiency"));
                    maquina.setState(rs.getInt("State"));
                    maquina.setFunctionKey(rs.getInt("FunctionKey"));

                    maquinas.add(maquina);
                    divisor += maquina.getWorkEfficiency() * (maquina.getTimeOn() + maquina.getTimeOff());
                    dividendo += maquina.getTimeOn() + maquina.getTimeOff();
                }
                if (dividendo > 0)
                    eficienciaGrupo = (int) (Math.round((double) divisor / dividendo));
                results[0] = maquinas;
                results[1] = eficienciaGrupo;
            } catch (SQLException e) {
                e.printStackTrace();
                //consider a re-throw, throwing a wrapping exception, etc
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static Object[] obtenerEficienciasPorSector(String roomCode) {

        Object[] results = new Object[2];
        List<Maquina> maquinas = new ArrayList<>();

        Integer eficienciaTotal = 0;
        Integer divisor = 0;
        Integer dividendo = 0;

        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM Weff_Actual_Monitor" +
                    " WHERE RoomCode LIKE '" + roomCode + "%'" +
                    " ORDER BY MachCode");
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Maquina maquina = new Maquina();
                    maquina.setMachCode(rs.getInt("MachCode"));
                    maquina.setGroupCode(rs.getString("GroupCode"));
                    maquina.setTimeOn(rs.getInt("TimeOn"));
                    maquina.setTimeOff(rs.getInt("TimeOff"));
                    maquina.setWorkEfficiency(rs.getInt("WorkEfficiency") > 100 ? rs.getInt("TimeEfficiency") : rs.getInt("WorkEfficiency"));
                    maquina.setTimeEfficiency(rs.getInt("TimeEfficiency"));

                    maquinas.add(maquina);
                    divisor += maquina.getWorkEfficiency() * (maquina.getTimeOn() + maquina.getTimeOff());
                    dividendo += maquina.getTimeOn() + maquina.getTimeOff();
                }
                if (dividendo > 0)
                    eficienciaTotal = (int) Math.round((double) divisor / dividendo);
            } catch (SQLException e) {
                e.printStackTrace();
                //consider a re-throw, throwing a wrapping exception, etc
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        results[0] = maquinas;
        results[1] = eficienciaTotal;

        return results;
    }

}
