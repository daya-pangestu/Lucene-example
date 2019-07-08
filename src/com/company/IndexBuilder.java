package com.company;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IndexBuilder {
    private static IndexBuilder INSTANCE;


    //konstruktor
    public IndexBuilder() {
        readCsv();
    }

    //kelas singleton
    static IndexBuilder getINSTANCE() {
        if (INSTANCE == null) {
            return new IndexBuilder();
        } else {
            return INSTANCE;
        }
    }

    //method untuk membaca file csv yang ada
    private void readCsv() {
        File files = new File("file.csv");
        if (files.isFile()) {

            try {
                BufferedReader br = new BufferedReader(new FileReader(files));
                String line;
                List<String> documentString = new ArrayList<>();

                while ((line = br.readLine()) != null) {
                    documentString.add(line);
                }

                br.close();

                addDocument(documentString); //panggil addocument
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method untuk menambahkan document ke mesin pencarian
    private void addDocument(List<String> strings) throws IOException {//buat document yang nantinya digunakan untuk peng indexan
        System.out.println("cek");

        IndexWriterConfig iwc = new IndexWriterConfig(new EnglishAnalyzer());
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter writer = new IndexWriter(FSDirectory.open(new File("plotIndex").toPath()), iwc);
        Document document;

        for (String doc : strings) {

            document = new Document();
            document.add(new TextField("title", doc, Field.Store.YES));
            writer.addDocument(document);
        }
        writer.close();
    }
}
