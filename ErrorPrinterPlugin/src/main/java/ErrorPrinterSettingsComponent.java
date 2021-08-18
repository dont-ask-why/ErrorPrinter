import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

public class ErrorPrinterSettingsComponent {
    private final JPanel mainPanel;
    private final JComboBox<String> printerList = new ComboBox<>(PrintUtility.getPrinterServiceNameArray());
    private final JBCheckBox enableSysErrCheckBox = new JBCheckBox("Print everything from System.err");
    private final JBCheckBox enableSysOutCheckBox = new JBCheckBox("Print everything from System.out");

    private boolean errEnabled;
    private boolean outEnabled;
    private String printerName;

    public ErrorPrinterSettingsComponent(String selectedPrinterName, boolean errEnabled, boolean outEnabled) {
        mainPanel = FormBuilder.createFormBuilder()
                .addComponent(printerList, 1)
                .addComponent(enableSysErrCheckBox, 2)
                .addComponent(enableSysOutCheckBox, 2)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();

        this.printerName = selectedPrinterName;
        this.errEnabled = errEnabled;
        this.outEnabled = outEnabled;

        enableSysErrCheckBox.setSelected(errEnabled);
        enableSysOutCheckBox.setSelected(outEnabled);
        printerList.setSelectedItem(printerName);

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

    public String getPrinterName() {
        return this.printerName;
    }

    public boolean isErrEnabled() {
        return this.errEnabled;
    }

    public boolean isOutEnabled(){
        return this.outEnabled;
    }

    public void setOutEnabled(boolean outEnabled){
        this.outEnabled = outEnabled;
    }

    public void setErrEnabled(boolean errEnabled) {
        this.errEnabled = errEnabled;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }
}
