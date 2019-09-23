package com.pwpb.uas;

public class Notes {
    String id,judul,waktu,desc;

    public Notes(){}

    public Notes(String id, String judul, String desc, String waktu) {
        this.id = id;
        this.judul = judul;
        this.waktu = waktu;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
