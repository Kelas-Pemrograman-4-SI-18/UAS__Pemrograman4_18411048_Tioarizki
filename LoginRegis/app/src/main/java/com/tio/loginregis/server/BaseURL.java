package com.tio.loginregis.server;

public class BaseURL {

    public static String baseUrl = "http://192.168.43.89:5050/";

    public static String login    = baseUrl + "user/login";
    public static String register = baseUrl + "user/registrasi";


    //Baju
    public static String dataBaju = baseUrl + "baju/databaju";
    public static String editDataBaju = baseUrl + "baju/ubah/";
    public static String hapusData = baseUrl + "baju/hapus/";
    public static String inputBaju = baseUrl + "baju/input";
}
