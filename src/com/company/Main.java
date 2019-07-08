package com.company;


import org.apache.lucene.document.Document;

import java.util.HashMap;

public class Main {
    IndexBuilder ix = new IndexBuilder();

    public static void main(String[] args) {

    }

    static void println(Object obj) {
        System.out.println(obj);
    }

    static void showresult(HashMap<Document, Float> documentFloatHashMap) {
        documentFloatHashMap.forEach((indexableFields, aFloat) -> println(aFloat + " " + indexableFields));
    }

}