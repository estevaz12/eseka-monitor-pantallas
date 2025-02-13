package ar.com.leo.monitor.model;

/**
 * @author Leo
 */
public class Maquina {

    private Integer machCode;
    private Integer workEfficiency;
    private Integer timeEfficiency;
    private String groupCode;
    private Integer state;
    private Integer timeOn;
    private Integer timeOff;
    private Integer functionKey;
    //    private Integer roomCode;
//    private String styleCode;
//    private Integer lastTimeOn;
//    private Integer lastTimeOff;
//    private Integer pieces;
//    private Integer targetOrder;
//    private Integer shiftPieces;
//    private Integer lastPieces;
//    private Integer bagPieces;
//    private Integer bagTarget;
//    private Integer idealCycle;
//    private Integer lastCycle;
//    private Integer lastStopCode;
//    private Integer step;
//    private Integer discards;
//    private Integer shift;
//    private Integer stopFrequency;
//    private Integer pairCode;
//    private Integer shiftDiscards;
//    private Integer lastDiscards;
//    private String dateStartShift;

    public Maquina() {
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Integer getMachCode() {
        return machCode;
    }

    public void setMachCode(Integer machCode) {
        this.machCode = machCode;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTimeOn() {
        return timeOn;
    }

    public void setTimeOn(Integer timeOn) {
        this.timeOn = timeOn;
    }

    public Integer getTimeOff() {
        return timeOff;
    }

    public void setTimeOff(Integer timeOff) {
        this.timeOff = timeOff;
    }

    public Integer getFunctionKey() {
        return functionKey;
    }

    public void setFunctionKey(Integer functionKey) {
        this.functionKey = functionKey;
    }

    public Integer getWorkEfficiency() {
        return workEfficiency;
    }

    public void setWorkEfficiency(Integer workEfficiency) {
        this.workEfficiency = workEfficiency;
    }

    public Integer getTimeEfficiency() {
        return timeEfficiency;
    }

    public void setTimeEfficiency(Integer timeEfficiency) {
        this.timeEfficiency = timeEfficiency;
    }
}
