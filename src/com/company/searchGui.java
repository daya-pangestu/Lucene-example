package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class searchGui {
    private JButton mBtnSearch;
    private JTextField mTxtSearch;
    private JList<Object> mListResult;
    private JPanel mPanelMain;
    private JTextPane mTvPlot;

    private List<SearchModel> mHasil = new ArrayList<>();

    private searchGui() {

        mBtnSearch.addActionListener(e -> performSearch());
        mTxtSearch.addActionListener(e -> performSearch());
    }

    public static void main(String[] args) {

        //untuk memanggil gui
        JFrame frame = new JFrame("searchGui");
        JScrollPane jsp = new JScrollPane(new searchGui().mTvPlot);
        frame.add(jsp);
        frame.setContentPane(new searchGui().mPanelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1200, 500));
        frame.pack();
        frame.setVisible(true);
    }

    private void performSearch() {
        String query = mTxtSearch.getText();
        Search s = new Search();

        mTxtSearch.setText("");
        mTvPlot.setText("");

        List<String> title = new ArrayList<>(); //list untuk menampung judul dari mHasil pencarian

        s.search(query).forEach((SearchModel searchModel) -> {
            mHasil.add(searchModel);
            title.add(searchModel.getJudul());

            System.out.println(searchModel.getScore() + " " + searchModel.getJudul());
        });


        mListResult.setListData(title.toArray());//masukan mHasil gettilte kedalam jList

        jListSelectedListener(); //panggil method
    }

    private void jListSelectedListener() {
        mListResult.addListSelectionListener(e -> { //lakukan sesuatu jika item jList diklik
            if (!e.getValueIsAdjusting()) {

                String selectedResult = mListResult.getSelectedValue().toString(); //
                System.out.println(selectedResult); //print selected item

                mHasil.forEach(searchModel -> {
                    if (selectedResult.equalsIgnoreCase(searchModel.getJudul())) {
                        if (searchModel.getPlot().length() <= 1600) {
                            mTvPlot.setText(searchModel.getJudul() + " \n" + searchModel.getPlot());
                        } else {
                            mTvPlot.setText(searchModel.getJudul() + "\n " + searchModel.getPlot().substring(0, 1600) + "....");
                        }
                    }
                });

            }
        });
    }
}
