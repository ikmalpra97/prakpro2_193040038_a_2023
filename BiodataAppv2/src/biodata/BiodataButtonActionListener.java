package biodata;

import dao.BiodataDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class BiodataButtonActionListener {
    private final BiodataFrame biodataFrame;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton deleteButton;

    public BiodataButtonActionListener(BiodataFrame biodataFrame) {
        this.biodataFrame = biodataFrame;
        BiodataDAO biodataDAO = new BiodataDAO();

        addButton = new JButton("Tambah");
        addButton.addActionListener(this::actionPerformedAddData);

        editButton = new JButton("Edit");
        editButton.addActionListener(this::actionPerformedEditData);

        deleteButton = new JButton("Hapus");
        deleteButton.addActionListener(this::actionPerformedDeleteData);
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    private void actionPerformedAddData(ActionEvent e) {
        biodataFrame.addData();
    }

    private void actionPerformedEditData(ActionEvent e) {
        biodataFrame.editData();
    }

    private void actionPerformedDeleteData(ActionEvent e) {
        biodataFrame.deleteData();
    }
}