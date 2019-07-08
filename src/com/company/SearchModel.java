package com.company;

class SearchModel {
    private float score;
    private String judul;
    private String plot;

    SearchModel(float score, String judul, String plot) {
        this.score = score;
        this.judul = judul;
        this.plot = plot;
    }

    float getScore() {
        return score;
    }

    String getJudul() {
        return judul;
    }

    String getPlot() {
        return plot;
    }
}
