package main;


import biodata.BiodataFrame;

import javax.swing.*;

public class MainProgram {
    private BiodataFrame biodataFrame;

    public MainProgram() {
        this.biodataFrame = new BiodataFrame();
    }

    public BiodataFrame getBiodataFrame() {
        return biodataFrame;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainProgram mainProgram = new MainProgram();
                mainProgram.getBiodataFrame().displayData();
            }
        });
    }
}