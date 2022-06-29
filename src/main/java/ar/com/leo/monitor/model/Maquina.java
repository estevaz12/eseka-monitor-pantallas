package ar.com.leo.monitor.model;

/**
 * @author Leo
 */
public class Maquina {

    private Integer machCode;
    private Integer roomCode;
    private String groupCode;
    private Integer state;
    private String styleCode;
    private Integer timeOn;
    private Integer timeOff;
    private Integer lastTimeOn;
    private Integer lastTimeOff;
    private Integer functionKey;
    private Integer pieces;
    private Integer targetOrder;
    private Integer shiftPieces;
    private Integer lastPieces;
    private Integer bagPieces;
    private Integer bagTarget;
    private Integer idealCycle;
    private Integer lastCycle;
    private Integer lastStopCode;
    private Integer step;
    private Integer discards;
    private Integer workEfficiency;
    private Integer timeEfficiency;
    private Integer shift;
    private Integer stopFrequency;
    private Integer pairCode;
    private Integer shiftDiscards;
    private Integer lastDiscards;
    private String dateStartShift;

    public Maquina() {
    }

    public Integer getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(Integer roomCode) {
        this.roomCode = roomCode;
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

    public String getStyleCode() {
        return styleCode;
    }

    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode;
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

    public Integer getLastTimeOn() {
        return lastTimeOn;
    }

    public void setLastTimeOn(Integer lastTimeOn) {
        this.lastTimeOn = lastTimeOn;
    }

    public Integer getLastTimeOff() {
        return lastTimeOff;
    }

    public void setLastTimeOff(Integer lastTimeOff) {
        this.lastTimeOff = lastTimeOff;
    }

    public Integer getFunctionKey() {
        return functionKey;
    }

    public void setFunctionKey(Integer functionKey) {
        this.functionKey = functionKey;
    }

    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    public Integer getTargetOrder() {
        return targetOrder;
    }

    public void setTargetOrder(Integer targetOrder) {
        this.targetOrder = targetOrder;
    }

    public Integer getShiftPieces() {
        return shiftPieces;
    }

    public void setShiftPieces(Integer shiftPieces) {
        this.shiftPieces = shiftPieces;
    }

    public Integer getLastPieces() {
        return lastPieces;
    }

    public void setLastPieces(Integer lastPieces) {
        this.lastPieces = lastPieces;
    }

    public Integer getBagPieces() {
        return bagPieces;
    }

    public void setBagPieces(Integer bagPieces) {
        this.bagPieces = bagPieces;
    }

    public Integer getBagTarget() {
        return bagTarget;
    }

    public void setBagTarget(Integer bagTarget) {
        this.bagTarget = bagTarget;
    }

    public Integer getIdealCycle() {
        return idealCycle;
    }

    public void setIdealCycle(Integer idealCycle) {
        this.idealCycle = idealCycle;
    }

    public Integer getLastCycle() {
        return lastCycle;
    }

    public void setLastCycle(Integer lastCycle) {
        this.lastCycle = lastCycle;
    }

    public Integer getLastStopCode() {
        return lastStopCode;
    }

    public void setLastStopCode(Integer lastStopCode) {
        this.lastStopCode = lastStopCode;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getDiscards() {
        return discards;
    }

    public void setDiscards(Integer discards) {
        this.discards = discards;
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

    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }

    public Integer getStopFrequency() {
        return stopFrequency;
    }

    public void setStopFrequency(Integer stopFrequency) {
        this.stopFrequency = stopFrequency;
    }

    public Integer getPairCode() {
        return pairCode;
    }

    public void setPairCode(Integer pairCode) {
        this.pairCode = pairCode;
    }

    public Integer getShiftDiscards() {
        return shiftDiscards;
    }

    public void setShiftDiscards(Integer shiftDiscards) {
        this.shiftDiscards = shiftDiscards;
    }

    public Integer getLastDiscards() {
        return lastDiscards;
    }

    public void setLastDiscards(Integer lastDiscards) {
        this.lastDiscards = lastDiscards;
    }

    public String getDateStartShift() {
        return dateStartShift;
    }

    public void setDateStartShift(String dateStartShift) {
        this.dateStartShift = dateStartShift;
    }
}
