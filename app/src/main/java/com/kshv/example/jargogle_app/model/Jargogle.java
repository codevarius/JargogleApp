package com.kshv.example.jargogle_app.model;

import java.util.UUID;

public class Jargogle {
    public static final String JARGOGLE_POSITION = "jargogle.position";
    public static int ENCODED = 1, DECODED = 0;
    private String data = "";
    private String title = "default";
    private String uuid;
    private int encoded;
    private String chain_len, chain_seed;
    private String passwd;

    public Jargogle(){
        this.uuid = UUID.randomUUID ().toString ();
    }

    Jargogle(String uuid){
        this.uuid = uuid;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUUID() {
        return uuid;
    }

    public int getEncoded() {
        return encoded;
    }

    public void setEncoded(int encoded) {
        this.encoded = encoded;
    }

    public String getChain_len() {
        return chain_len;
    }

    public void setChain_len(String chain_len) {
        this.chain_len = chain_len;
    }

    public String getChain_seed() {
        return chain_seed;
    }

    public void setChain_seed(String chain_seed) {
        this.chain_seed = chain_seed;
    }
}
