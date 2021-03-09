import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ErrorPrinterSettingsComponent {
    private final JPanel mainPanel;
    private final JBList<String> printerList = new JBList<String>(PrintUtility.getPrinterServiceNameList());
    private final JBCheckBox enablePrinterCheckBox = new JBCheckBox("Print Error Messages to Printer");
//    private final JButton selectPrinterButton;

    public ErrorPrinterSettingsComponent() {
        mainPanel = FormBuilder.createFormBuilder()
                .addComponent(enablePrinterCheckBox, 1)
                .addComponent(printerList, 2)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();

        printerList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                String printer = printerList.getSelectedValue();
                System.out.print("selected printer: " + printer);
            }
        });
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public String getPrinterName() {
        return printerList.getSelectedValue();
    }
}
