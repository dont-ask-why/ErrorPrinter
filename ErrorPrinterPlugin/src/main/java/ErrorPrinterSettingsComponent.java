import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorPrinterSettingsComponent {
    private final JPanel mainPanel;
    private final JBList<String> printerList = new JBList<String>(PrintUtility.getPrinterServiceNameList());
    private final JBCheckBox enablePrinterCheckBox = new JBCheckBox("Print error messages to printer");
//    private final JButton selectPrinterButton;

    private boolean enabled = false;
    private String printerName = "None selected";

    public ErrorPrinterSettingsComponent() {
        mainPanel = FormBuilder.createFormBuilder()
                .addComponent(enablePrinterCheckBox, 1)
                .addComponent(printerList, 2)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();

        printerList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                printerName = printerList.getSelectedValue();
                System.out.println("selected printer: " + printerName);
            }
        });

        enablePrinterCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (enablePrinterCheckBox.isSelected()) {
                    enabled = true;
                    System.out.println("Checkbox is checked.");
                } else {
                    enabled = false;
                    System.out.println("Checkbox not checked.");
                }
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
