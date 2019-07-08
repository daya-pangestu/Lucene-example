package com.company;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.company.Main.println;

public class Search {

    //saat konstroktor dipanggil lakukan pengecekan apakah plotindex exist. jika iya maka panggil buildindex()
    public Search() {
        File files = new File("plotIndex");
        if (!files.exists()) {
            buildIndex();
            println("buildind index...");
        } else {
            println("index exist");
        }
    }

    //method untuk mendapatkan hasil pencarian, string pertama untuk judul dan string kedua untuk plot
    List<SearchModel> search(String query) {

        try {
            IndexReader reader = DirectoryReader.open(FSDirectory.open(new File("plotIndex").toPath()));
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser queryParser = new QueryParser("title", new EnglishAnalyzer());
            Query querysearch = queryParser.parse(query);

            Sort sortFromHighest = new Sort(SortField.FIELD_SCORE);//sort result dari yang terbesar ke terkecil

            int max_result = 10;
            TopDocs topDocs = searcher.search(querysearch, max_result, sortFromHighest, true);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            println("total hits dengan query " + query + " : " + topDocs.totalHits);
            println("\n");

            List<SearchModel> resultList = new ArrayList<>();

            for (ScoreDoc scoreDoc : scoreDocs) {
                Document document = searcher.doc(scoreDoc.doc);

                document.getFields()
                        .forEach(indexableField -> {
                            String result = indexableField.getCharSequenceValue().toString();
                            String[] substract = result.split(",", 2);
                            resultList.add(new SearchModel(scoreDoc.score, substract[0], substract[1]));
                        });
            }

            return resultList;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //memanggil kelas indexBuilder
    private void buildIndex() {
        IndexBuilder.getINSTANCE();
    }
}