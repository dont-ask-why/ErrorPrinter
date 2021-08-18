import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

public class ErrorPrinterSettingsComponent {
    private final JPanel mainPanel;
    private final JComboBox<String> printerList = new ComboBox<>(PrintUtility.getPrinterServiceNameArray());
    //private final JBList<String> printerList = new JBList<String>(PrintUtility.getPrinterServiceNameList());
    private final JBCheckBox enablePrinterCheckBox = new JBCheckBox("Print error messages to printer");

    private boolean enabled;
    private String printerName;

    public ErrorPrinterSettingsComponent(String selectedPrinterName, boolean enabled) {
        mainPanel = FormBuilder.createFormBuilder()
                .addComponent(enablePrinterCheckBox, 1)
                .addComponent(printerList, 2)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();

        this.printerName = selectedPrinterName;
        this.enabled = enabled;

        enablePrinterCheckBox.setSelected(enabled);
        printerList.setSelectedItem(printerName);

/*        printerList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                printerName = printerList.getSelectedValue();
                System.out.println("selected printer: " + printerName);
            }
        });*/

        printerList.addActionListener(actionEvent -> {
            if(printerList.getSelectedItem() != null) {
                System.out.println(printerList.getSelectedItem().toString());
                setPrinterName(printerList.getSelectedItem().toString());
            }
        });

        enablePrinterCheckBox.addActionListener(actionEvent -> {
            if (enablePrinterCheckBox.isSelected()) {
                setEnabled(true);
                System.out.println("Checkbox is checked.");
            } else {
                setEnabled(false);
                System.out.println("Checkbox not checked.");
            }
        });
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public String getPrinterName() {
        return this.printerName;
    }

    public boolean isPrinterEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }
}
