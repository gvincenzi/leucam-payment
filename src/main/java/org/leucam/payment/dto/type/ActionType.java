package org.leucam.payment.dto.type;

public enum ActionType {
    QUICK_PRINT("Stampa immediata");

    private String label;

    ActionType(String label){
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
