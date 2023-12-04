package biodata;

import dao.BiodataDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BiodataFrame extends JFrame {
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField namaField, nomorHPField;
    private JRadioButton priaRadio, wanitaRadio;
    private ButtonGroup jenisKelaminGroup;
    private JTextArea alamatArea;
    private ArrayList<BiodataTable> biodataList = new ArrayList<>();
    private BiodataButtonActionListener buttonActionListener;

    public BiodataFrame() {
        frame = new JFrame("Aplikasi Biodata");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Apakah Anda yakin ingin keluar dari aplikasi?", "Konfirmasi Keluar",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    saveDataToFile();
                    System.exit(0);
                }
            }
        });

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Nama");
        tableModel.addColumn("Jenis Kelamin");
        tableModel.addColumn("Nomor HP");
        tableModel.addColumn("Alamat");

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Nama:"));
        namaField = new JTextField();
        inputPanel.add(namaField);

        jenisKelaminGroup = new ButtonGroup();
        priaRadio = new JRadioButton("Pria");
        wanitaRadio = new JRadioButton("Wanita");
        jenisKelaminGroup.add(priaRadio);
        jenisKelaminGroup.add(wanitaRadio);

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

        buttonActionListener = new BiodataButtonActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(buttonActionListener.getAddButton());
        buttonPanel.add(buttonActionListener.getEditButton());
        buttonPanel.add(buttonActionListener.getDeleteButton());

        frame.setLayout(new BorderLayout());
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public ArrayList<BiodataTable> getBiodataList() {
        return biodataList;
    }

    public JTextField getNamaField() {
        return namaField;
    }

    public JTextField getNomorHPField() {
        return nomorHPField;
    }

    public JRadioButton getPriaRadio() {
        return priaRadio;
    }

    public JRadioButton getWanitaRadio() {
        return wanitaRadio;
    }

    public ButtonGroup getJenisKelaminGroup() {
        return jenisKelaminGroup;
    }

    public JTextArea getAlamatArea() {
        return alamatArea;
    }



    public void displayData() {
        // Menampilkan data dari database ke tabel
        for (BiodataTable biodata : this.biodataList) {
            getTableModel().addRow(new Object[]{biodata.getNama(), biodata.getJenisKelamin(), biodata.getNomorHP(), biodata.getAlamat()});
        }
    }
    protected void addData() {
        String nama = namaField.getText();
        String jenisKelamin = priaRadio.isSelected() ? "Pria" : "Wanita";
        String nomorHP = nomorHPField.getText();
        String alamat = alamatArea.getText();
        // Memeriksa apakah semua field telah diisi
        if (nama.isEmpty() || nomorHP.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Semua data harus diisi.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return; // Menghentikan proses jika ada field yang kosong
        }


        int result = JOptionPane.showConfirmDialog(frame, "Apakah data yang dimasukkan sudah sesuai?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        BiodataTable biodata = null;
        if (result == JOptionPane.YES_OPTION) {
            biodata = new BiodataTable();
            biodata.setNama(nama);
            biodata.setJenisKelamin(jenisKelamin);
            biodata.setNomorHP(nomorHP);
            biodata.setAlamat(alamat);
        }

        assert biodata != null;
        int inserResult = BiodataDAO.insert(biodata);

        if (inserResult != -1) {
            // Jika berhasil ditambahkan ke database
            tableModel.addRow(new Object[]{nama, jenisKelamin, nomorHP, alamat});
            clearFields();
        } else {
            // Jika terjadi kesalahan
            JOptionPane.showMessageDialog(frame, "Gagal menambahkan biodata.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metode untuk mengedit biodata yang ada
    protected void editData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Ambil nilai dari tabel
            String nama = namaField.getText();
            String jenisKelamin = priaRadio.isSelected() ? "Pria" : "Wanita";
            String nomorHP = nomorHPField.getText();
            String alamat = alamatArea.getText();

            // Ambil id dari tabel
            int Id = biodataList.get(selectedRow).getId();

            // Ubah nilai di database
            int result = BiodataDAO.update(new BiodataTable(Id, nama, jenisKelamin, nomorHP, alamat));

            if (result != -1) {
                // Jika berhasil diubah di database
                tableModel.setValueAt(nama, selectedRow, 0);
                tableModel.setValueAt(jenisKelamin, selectedRow, 1);
                tableModel.setValueAt(nomorHP, selectedRow, 2);
                tableModel.setValueAt(alamat, selectedRow, 3);

                clearFields();
            } else {
                // Jika terjadi kesalahan
                JOptionPane.showMessageDialog(frame, "Gagal mengedit biodata.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Pilih biodata yang akan diedit.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Metode untuk menghapus biodata dari tabel dan database
    protected void deleteData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Ambil id dari tabel
            int selectedId = biodataList.get(selectedRow).getId();

            // Hapus dari database
            int result = BiodataDAO.delete(new BiodataTable(selectedId, "", "", "", ""));

            if (result != -1) {
                // Jika berhasil dihapus dari database
                tableModel.removeRow(selectedRow);
                clearFields();
            } else {
                // Jika terjadi kesalahan
                JOptionPane.showMessageDialog(frame, "Gagal menghapus biodata.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Pilih biodata yang akan dihapus.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Metode untuk mengosongkan input fields setelah menambah atau mengedit biodata
    private void clearFields() {
        namaField.setText("");
        nomorHPField.setText("");
        jenisKelaminGroup.clearSelection();
        alamatArea.setText("");
    }

    public void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("biodata.txt"))) {
            for (BiodataTable biodata : biodataList) {
                writer.write(biodata.getNama() + "," + biodata.getJenisKelamin() + "," + biodata.getNomorHP() + "," + biodata.getAlamat());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}