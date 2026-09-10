package vn.iotstar.util;


public class Validation {


    public static boolean isEmpty(String value){

        return value == null || value.trim().isEmpty();

    }


    public static boolean isPhone(String phone){

        return phone != null && phone.matches("\\d{10}");

    }


}
