package br.com.alg.trufflesapi.model;

import java.io.Serializable;

public class Detail implements Serializable {

    private static final long serialVersionUID = 1L;
    private String line;

    public String getLine() {
        return line;
    }

    public Detail setLine(String line) {
        this.line = line;
        return  this;
    }
}
