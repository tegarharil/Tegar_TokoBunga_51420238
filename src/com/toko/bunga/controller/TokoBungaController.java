/**
 * Author: TegarHariL_51420238
 */

package com.toko.bunga.controller; 

import com.toko.bunga.config.HibernateUtil;
import com.toko.bunga.dao.TokoBungaDao;
import com.toko.bunga.model.TokoBunga;
import com.toko.bunga.model.TokoBungaTabelModel;
import com.toko.bunga.View.TokoBungaView;
import javax.swing.JOptionPane;
import java.util.List;

public class TokoBungaController {
    private final TokoBungaView tokoBungaView;
    private final TokoBungaDao tokoBungaDao;
    private TokoBungaTabelModel tokoBungaTableModel;

    public TokoBungaController(TokoBungaView tokoBungaView) {
        this.tokoBungaView = tokoBungaView;
        this.tokoBungaDao = HibernateUtil.getTokoBungaDao();
    }

    public void clear() {
        tokoBungaView.getTxtKode().setText("");
        tokoBungaView.getTxtNama().setText("");
        tokoBungaView.getTxtJenis().setText("");
        tokoBungaView.getTxtHarga().setText("");
    }

    public void saveOrUpdateBunga() {
        String kode = tokoBungaView.getTxtKode().getText();
        String nama = tokoBungaView.getTxtNama().getText();
        String jenis = tokoBungaView.getTxtJenis().getText();
        int harga;

        try {
            harga = Integer.parseInt(tokoBungaView.getTxtHarga().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TokoBunga tokoBunga = new TokoBunga();
        tokoBunga.setKd_bunga(kode);
        tokoBunga.setNama(nama);
        tokoBunga.setJenis(jenis);
        tokoBunga.setHarga(harga);

        try {
            if (kode.isEmpty()) {
                tokoBungaDao.save(tokoBunga);
                JOptionPane.showMessageDialog(null, "Berhasil menyimpan Bunga", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                tokoBungaDao.update(tokoBunga);
                JOptionPane.showMessageDialog(null, "Berhasil mengubah Bunga", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            clear();
            getAllData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan/mengubah Bunga", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteBunga() {
        String kode = tokoBungaView.getTxtKode().getText();
        if (kode.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Belum Dipilih", "Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(null, "Apakah ingin menghapus ini?", "Warning", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            try {
                TokoBunga tokoBunga = new TokoBunga();
                tokoBunga.setKd_bunga(kode);
                tokoBungaDao.delete(tokoBunga);
                JOptionPane.showMessageDialog(null, "Berhasil menghapus Bunga", "Success", JOptionPane.INFORMATION_MESSAGE);
                clear();
                getAllData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal menghapus Bunga", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void getAllData() {
        List<TokoBunga> listTokoBunga = tokoBungaDao.getList();
        tokoBungaTableModel = new TokoBungaTabelModel(listTokoBunga);
        tokoBungaView.getTblBunga().setModel(tokoBungaTableModel);
    }

    public void getData() {
        int rowIndex = tokoBungaView.getTblBunga().getSelectedRow();
        if (rowIndex >= 0) {
            tokoBungaView.getTxtKode().setText(String.valueOf(tokoBungaView.getTblBunga().getValueAt(rowIndex, 0)));
            tokoBungaView.getTxtNama().setText(String.valueOf(tokoBungaView.getTblBunga().getValueAt(rowIndex, 1)));
            tokoBungaView.getTxtJenis().setText(String.valueOf(tokoBungaView.getTblBunga().getValueAt(rowIndex, 2)));
            tokoBungaView.getTxtHarga().setText(String.valueOf(tokoBungaView.getTblBunga().getValueAt(rowIndex, 3)));
        }
    }
}
