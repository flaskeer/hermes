package com.asterisk.opensource.test.config;

/**
 * Created by dudycoco on 17-1-12.
 */
public class TestException {

    public static void main(String[] args) {
        String result = createString();
        System.out.println(result);
    }

    public static String createString() {
        String result = null;
        try{
            result= createString2();
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String createString2() throws Exception{
        System.out.println("开始执行");
        StringBuilder sb = new StringBuilder();
        sb.append("1");
        return sb.toString();
    }


}
