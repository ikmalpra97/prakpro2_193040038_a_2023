package com.biodata;

/**
 *
 * @author Ilham
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BiodataApp {
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField namaField, nomorHPField;
    private JRadioButton priaRadio, wanitaRadio;
    private ButtonGroup jenisKelaminGroup;
    private JTextArea alamatArea;

    private ArrayList<Biodata> biodataList = new ArrayList<>();

    // Konstruktor aplikasi
    public BiodataApp() {
        // Membuat jendela aplikasi
        frame = new JFrame("Aplikasi Biodata");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Menambahkan WindowListener untuk menangani peristiwa penutupan jendela
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Apakah Anda yakin ingin keluar dari aplikasi?", "Konfirmasi Keluar",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    saveDataToFile(); // Simpan data ke file sebelum keluar
                    System.exit(0);
                }
            }
        });

        // Inisialisasi tabel dan model tabel
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Nama");
        tableModel.addColumn("Jenis Kelamin");
        tableModel.addColumn("Nomor HP");
        tableModel.addColumn("Alamat");

        // Panel input
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Nama:"));
        namaField = new JTextField();
        inputPanel.add(namaField);

        // Inisialisasi radio button jenis kelamin dan mengelompokkannya
        jenisKelaminGroup = new ButtonGroup();
        priaRadio = new JRadioButton("Pria");
        wanitaRadio = new JRadioButton("Wanita");
        jenisKelaminGroup.add(priaRadio);
        jenisKelaminGroup.add(wanitaRadio);

        // Panel untuk radio button jenis kelamin
        JPanel jenisKelaminPanel = new JPanel();
        jenisKelaminPanel.add(priaRadio);
        jenisKelaminPanel.add(wanitaRadio);

        inputPanel.add(new JLabel("Jenis Kelamin:"));
        inputPanel.add(jenisKelaminPanel);

        inputPanel.add(new JLabel("Nomor HP:"));
        nomorHPField = new JTextField();
        inputPanel.add(nomorHPField);
        inputPanel.add(new JLabel("Alamat:"));
        alamatArea = new JTextArea(3, 20);
        inputPanel.add(new JScrollPane(alamatArea));

        // Tombol "Tambah", "Edit", dan "Hapus"
        JButton addButton = new JButton("Tambah");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addData();
            }
        });

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editData();
            }
        });

        JButton deleteButton = new JButton("Hapus");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });

        // Panel tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Menyiapkan tata letak frame menggunakan BorderLayout
        frame.setLayout(new BorderLayout());
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    // Metode untuk menambahkan biodata ke tabel
    private void addData() {
        String nama = namaField.getText();
        String jenisKelamin = priaRadio.isSelected() ? "Pria" : "Wanita";
        String nomorHP = nomorHPField.getText();
        String alamat = alamatArea.getText();

        // Validasi input data
        if (nama.isEmpty() || nomorHP.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Harap isi semua data.");
        } else {
            Biodata biodata = new Biodata(nama, jenisKelamin, nomorHP, alamat);
            biodataList.add(biodata);
            tableModel.addRow(new Object[]{nama, jenisKelamin, nomorHP, alamat});
            clearFields();
        }
    }

    // Metode untuk mengosongkan input fields setelah menambah atau mengedit biodata
    private void clearFields() {
        namaField.setText("");
        jenisKelaminGroup.clearSelection();
        nomorHPField.setText("");
        alamatArea.setText("");
    }

    // Metode untuk mengedit biodata yang ada
    private void editData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String nama = namaField.getText();
            String jenisKelamin = priaRadio.isSelected() ? "Pria" : "Wanita";
            String nomorHP = nomorHPField.getText();
            String alamat = alamatArea.getText();

            // Validasi input data
            if (nama.isEmpty() || nomorHP.isEmpty() || alamat.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Harap isi semua data.");
            } else {
                Biodata biodata = biodataList.get(selectedRow);
                biodata.setNama(nama);
                biodata.setJenisKelamin(jenisKelamin);
                biodata.setNomorHP(nomorHP);
                biodata.setAlamat(alamat);

                // Memperbarui data di tabel
                tableModel.setValueAt(nama, selectedRow, 0);
                tableModel.setValueAt(jenisKelamin, selectedRow, 1);
                tableModel.setValueAt(nomorHP, selectedRow, 2);
                tableModel.setValueAt(alamat, selectedRow, 3);
                clearFields();
            }
        }
    }

    // Metode untuk menghapus biodata dari tabel
    private void deleteData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            biodataList.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            clearFields();
        }
    }

    // Metode untuk menyimpan data ke file teks ketika aplikasi ditutup
    private void saveDataToFile() {
        try (FileWriter writer = new FileWriter("biodata.txt")) {
            for (Biodata biodata : biodataList) {
                writer.write(biodata.getNama() + "," + biodata.getJenisKelamin() + "," + biodata.getNomorHP() + "," + biodata.getAlamat() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metode main untuk menjalankan aplikasi Swing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BiodataApp();
            }
        });
    }
}
