package com.anon.test;

import org.wuxb.beartific.batisUtils.MyBatisXMLAutoCreateController;

/**
 * Created by lenovo on 2016/6/29.
 */
public class TestGenerate {

    public static void main(String[] args){

        MyBatisXMLAutoCreateController con = new MyBatisXMLAutoCreateController();

        con.createMyBatisSimplePOJOXML(false,ImTestPOJO.class,"id","NoTable");

    }
}
