package br.com.alg.trufflesapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    private String wtitle;
    private String wsubtitle;
    private String wpagheader;
    private String wcolheader;
    private String wimage;
    private List<Detail> wdetails = new ArrayList<>();
    private String wfooter;

    public String getWtitle() {
        return wtitle;
    }
    public void setWtitle(String wtitle) {
        this.wtitle = wtitle;
    }
    public String getWsubtitle() {
        return wsubtitle;
    }
    public void setWsubtitle(String wsubtitle) {
        this.wsubtitle = wsubtitle;
    }
    public String getWpagheader() {
        return wpagheader;
    }
    public void setWpagheader(String wpagheader) {
        this.wpagheader = wpagheader;
    }
    public String getWcolheader() {
        return wcolheader;
    }
    public void setWcolheader(String wcolheader) {
        this.wcolheader = wcolheader;
    }
    public List<Detail> getWdetails() {
        return wdetails;
    }
    public void setWdetails(List<Detail> wdetails) {
        this.wdetails = wdetails;
    }
    public String getWfooter() {
        return wfooter;
    }
    public void setWfooter(String wfooter) {
        this.wfooter = wfooter;
    }
    public String getWimage() {
        return wimage;
    }
    public void setWimage(String wimage) {
        this.wimage = wimage;
    }
}
