package org.leucam.payment.dto.type;

public enum FrontBackType {
    FRONT("Fronte"),FRONT_BACK("Fronte/Retro");

    private String label;

    FrontBackType(String label){
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
