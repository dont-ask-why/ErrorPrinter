import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Class that represents the settings page.
 */

public class ErrorPrinterSettingsComponent {
    private final JPanel mainPanel;

    private final JPanel whereToPrintPanel;
    private final JPanel whatToPrintPanel;
    private final JPanel howToPrintPanel;

    private final JComboBox<String> printerList = new ComboBox<>(PrintUtility.getPrinterServiceNameArray());
    private final JBCheckBox enableSysErrCheckBox = new JBCheckBox("System.err");
    private final JBCheckBox enableSysOutCheckBox = new JBCheckBox("System.out");
    private final JBCheckBox enableTxtPrintCheckBox = new JBCheckBox("Save as .txt");
    private final JBCheckBox enableUnformatedCheckBox = new JBCheckBox("Simplified option");

    private boolean errEnabled;
    private boolean outEnabled;
    private boolean txtEnabled;
    private boolean unformatedEnabled;
    private String printerName;

    public ErrorPrinterSettingsComponent(String selectedPrinterName, boolean errEnabled, boolean outEnabled, boolean txtEnabled, boolean unformatedEnabled) {
        String WHERE_TO_PRINT_COMMENT = "Select a printer or the (boring) .txt option. You can also use both." +
                "If neither is selected it will behave like it is entirely disabled.";
        JPanel innerGrid = UI.PanelFactory.grid()
                .add(UI.PanelFactory.panel(printerList).withLabel("Select printer:").resizeX(false).resizeY(true))
                .createPanel();
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        innerGrid.add(enableTxtPrintCheckBox, c);
        whereToPrintPanel = UI.PanelFactory.panel(innerGrid).withComment(WHERE_TO_PRINT_COMMENT).createPanel();
        whereToPrintPanel.setBorder(IdeBorderFactory.createTitledBorder("Where to Print"));

        String WHAT_TO_PRINT_COMMENT = "Choose which outputs to print." +
                "If none is chosen it will behave like no printer is enabled.";
        whatToPrintPanel = UI.PanelFactory.panel(UI.PanelFactory.grid()
                        .add(UI.PanelFactory.panel(enableSysOutCheckBox))
                        .add(UI.PanelFactory.panel(enableSysErrCheckBox)).createPanel())
                .withComment(WHAT_TO_PRINT_COMMENT).createPanel();
        whatToPrintPanel.setBorder(IdeBorderFactory.createTitledBorder("What to Print"));

        String HOW_TO_PRINT_COMMENT = "Most printers will work just fine with this setting disabled." +
                "It is mainly a feature for older printers using the \"Generic/Text Only\" print driver.";
        howToPrintPanel = UI.PanelFactory.panel(UI.PanelFactory.panel(enableUnformatedCheckBox).createPanel())
                .withComment(HOW_TO_PRINT_COMMENT).createPanel();
        howToPrintPanel.setBorder(IdeBorderFactory.createTitledBorder("How to Print"));

        mainPanel = FormBuilder.createFormBuilder()
                .addComponent(whereToPrintPanel)
                .addComponent(whatToPrintPanel)
                .addComponent(howToPrintPanel)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();

        this.printerName = selectedPrinterName;
        this.errEnabled = errEnabled;
        this.outEnabled = outEnabled;
        this.txtEnabled = txtEnabled;
        this.unformatedEnabled = unformatedEnabled;

        enableTxtPrintCheckBox.setSelected(txtEnabled);
        enableSysErrCheckBox.setSelected(errEnabled);
        enableSysOutCheckBox.setSelected(outEnabled);
        enableUnformatedCheckBox.setSelected(unformatedEnabled);
        printerList.setSelectedItem(printerName);

        validatePrinting();

        enableUnformatedCheckBox.addActionListener(actionEvent -> {
            setUnformatedEnabled(enableUnformatedCheckBox.isSelected());
        });

        enableTxtPrintCheckBox.addActionListener(actionEvent -> {
            setTxtEnabled(enableTxtPrintCheckBox.isSelected());
            validatePrinting();
        });

        printerList.addActionListener(actionEvent -> {
            if (printerList.getSelectedItem() != null) {
                System.out.println(printerList.getSelectedItem().toString());
                setPrinterName(printerList.getSelectedItem().toString());
            }
            validatePrinting();
        });

        enableSysErrCheckBox.addActionListener(actionEvent -> {
            setErrEnabled(enableSysErrCheckBox.isSelected());
        });

        enableSysOutCheckBox.addActionListener(actionEvent -> {
            setOutEnabled(enableSysOutCheckBox.isSelected());
        });
    }

    private void validatePrinting() {
        if (txtEnabled || !printerList.getSelectedItem().equals("Not selected")) {
            enableUnformatedCheckBox.setEnabled(true);
            enableSysErrCheckBox.setEnabled(true);
            enableSysOutCheckBox.setEnabled(true);
        } else {
            enableUnformatedCheckBox.setEnabled(false);
            enableSysErrCheckBox.setEnabled(false);
            enableSysOutCheckBox.setEnabled(false);
        }
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    /**
     * @return String that represents the selected Printer.
     */
    public String getPrinterName() {
        return this.printerName;
    }

    /**
     * @return Boolean that indicates if std.err should be printed.
     */
    public boolean isErrEnabled() {
        return this.errEnabled;
    }

    /**
     * @return Boolean that indicates if std.out should be printed.
     */
    public boolean isOutEnabled() {
        return this.outEnabled;
    }

    /**
     * @return Boolean that indicates if a .txt file should be created.
     */
    public boolean isTxtEnabled() {
        return this.txtEnabled;
    }

    public boolean isUnformatedEnabled() {
        return this.unformatedEnabled;
    }

    /**
     * @param outEnabled boolean to set std.out printing.
     */
    public void setOutEnabled(boolean outEnabled) {
        this.outEnabled = outEnabled;
    }

    /**
     * @param errEnabled boolean to set std.err printing.
     */
    public void setErrEnabled(boolean errEnabled) {
        this.errEnabled = errEnabled;
    }

    /**
     * @param txtEnabled boolean to set .txt file creation
     */
    public void setTxtEnabled(boolean txtEnabled) {
        this.txtEnabled = txtEnabled;
    }

    public void setUnformatedEnabled(boolean unformatedEnabled) {
        this.unformatedEnabled = unformatedEnabled;
    }

    /**
     * @param printerName String to set the selected Printer.
     */
    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }
}
