import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

/**
 * Class that represents the settings page.
 */

public class ErrorPrinterSettingsComponent {
    private final JPanel mainPanel;
    private final JComboBox<String> printerList = new ComboBox<>(PrintUtility.getPrinterServiceNameArray());
    private final JBCheckBox enableSysErrCheckBox = new JBCheckBox("Print everything from System.err");
    private final JBCheckBox enableSysOutCheckBox = new JBCheckBox("Print everything from System.out");
    private final JBCheckBox enableTxtPrintCheckBox = new JBCheckBox("Save as .txt");

    private boolean errEnabled;
    private boolean outEnabled;
    private boolean txtEnabled;
    private String printerName;

    public ErrorPrinterSettingsComponent(String selectedPrinterName, boolean errEnabled, boolean outEnabled, boolean txtEnabled) {
        mainPanel = FormBuilder.createFormBuilder()
                .addComponent(enableTxtPrintCheckBox, 1)
                .addComponent(printerList, 1)
                .addComponent(enableSysErrCheckBox, 2)
                .addComponent(enableSysOutCheckBox, 2)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();

        this.printerName = selectedPrinterName;
        this.errEnabled = errEnabled;
        this.outEnabled = outEnabled;
        this.txtEnabled = txtEnabled;

        enableTxtPrintCheckBox.setSelected(txtEnabled);
        enableSysErrCheckBox.setSelected(errEnabled);
        enableSysOutCheckBox.setSelected(outEnabled);
        printerList.setSelectedItem(printerName);

        enableTxtPrintCheckBox.addActionListener(actionEvent -> {
            setTxtEnabled(enableTxtPrintCheckBox.isSelected());
            printerList.setEnabled(!isTxtEnabled());
        });

        printerList.addActionListener(actionEvent -> {
            if(printerList.getSelectedItem() != null) {
                System.out.println(printerList.getSelectedItem().toString());
                setPrinterName(printerList.getSelectedItem().toString());
            }
        });

        enableSysErrCheckBox.addActionListener(actionEvent -> {
            setErrEnabled(enableSysErrCheckBox.isSelected());
        });

        enableSysOutCheckBox.addActionListener(actionEvent -> {
            setOutEnabled(enableSysOutCheckBox.isSelected());
        });
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    /**
     *
     * @return String that represents the selected Printer.
     */
    public String getPrinterName() {
        return this.printerName;
    }

    /**
     *
     * @return Boolean that indicates if std.err should be printed.
     */
    public boolean isErrEnabled() {
        return this.errEnabled;
    }

    /**
     *
     * @return Boolean that indicates if std.out should be printed.
     */
    public boolean isOutEnabled(){
        return this.outEnabled;
    }

    /**
     *
     * @return Boolean that indicates if a .txt file should be created.
     */
    public boolean isTxtEnabled() {
        return this.txtEnabled;
    }

    /**
     *
     * @param outEnabled boolean to set std.out printing.
     */
    public void setOutEnabled(boolean outEnabled){
        this.outEnabled = outEnabled;
    }

    /**
     *
     * @param errEnabled boolean to set std.err printing.
     */
    public void setErrEnabled(boolean errEnabled) {
        this.errEnabled = errEnabled;
    }

    /**
     *
     * @param txtEnabled boolean to set .txt file creation
     */
    public void setTxtEnabled(boolean txtEnabled) {
        this.txtEnabled = txtEnabled;
    }

    /**
     *
     * @param printerName String to set the selected Printer.
     */
    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }
}
