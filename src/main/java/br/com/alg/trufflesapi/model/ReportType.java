package br.com.alg.trufflesapi.model;

public enum ReportType {

    PORTRAIT("portrait"), LANDSCAPE("landscape");

    private String description;

    private ReportType(String description) {
        this.description=description;
    }

    public String getDescription() {
        return description;
    }
}
