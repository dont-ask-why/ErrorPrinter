import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ErrorPrinterSettingsConfigurable implements Configurable {

    private ErrorPrinterSettingsComponent settingsComponent;

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "Real Error Printer";
    }

    @Override
    public @Nullable JComponent createComponent() {
        settingsComponent = new ErrorPrinterSettingsComponent();
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
//        ErrorPrinterSettingsState settings = ErrorPrinterSettingsState.getInstance();
//        boolean modified = settingsComponent.getPrinterName().equals();
//        return true;
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
        ErrorPrinterSettingsState settings = ErrorPrinterSettingsState.getInstance();
    }
}
