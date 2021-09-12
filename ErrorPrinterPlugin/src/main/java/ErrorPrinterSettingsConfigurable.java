import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Configurable is used for user changeable data. Final interaction with it is on the SettingsComponent
 * and persistent storage is via. the SettingsState class.
 */
public class ErrorPrinterSettingsConfigurable implements Configurable {

    private ErrorPrinterSettingsComponent settingsComponent;

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "Real Error Printer";
    }

    @Override
    public @Nullable JComponent createComponent() {
        ErrorPrinterSettingsState settings = ErrorPrinterSettingsState.getInstance();
        settingsComponent = new ErrorPrinterSettingsComponent(settings.getPrinterName(), settings.isErrEnabled(), settings.isOutEnabled(), settings.isTxtEnabled(), settings.isUnformatedEnabled());
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        ErrorPrinterSettingsState settings = ErrorPrinterSettingsState.getInstance();
        boolean modified = !settingsComponent.getPrinterName().equals(settings.getPrinterName()) ||
                (settingsComponent.isErrEnabled() != settings.isErrEnabled()) ||
                (settingsComponent.isOutEnabled() != settings.isOutEnabled()) ||
                (settingsComponent.isTxtEnabled() != settings.isTxtEnabled()) ||
                (settingsComponent.isUnformatedEnabled() != settings.isUnformatedEnabled());
        return modified;
    }

    @Override
    public void apply() throws ConfigurationException {
        ErrorPrinterSettingsState settings = ErrorPrinterSettingsState.getInstance();
        settings.setPrinterName(settingsComponent.getPrinterName());
        settings.setErrEnabled(settingsComponent.isErrEnabled());
        settings.setOutEnabled(settingsComponent.isOutEnabled());
        settings.setTxtEnabled(settingsComponent.isTxtEnabled());
        settings.setUnformatedEnabled(settingsComponent.isUnformatedEnabled());
    }

    @Override
    public void reset() {
        ErrorPrinterSettingsState settings = ErrorPrinterSettingsState.getInstance();
        settingsComponent.setPrinterName(settings.getPrinterName());
        settingsComponent.setErrEnabled(settings.isErrEnabled());
        settingsComponent.setOutEnabled(settings.isOutEnabled());
        settingsComponent.setTxtEnabled(settings.isTxtEnabled());
        settingsComponent.setUnformatedEnabled(settings.isUnformatedEnabled());
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}
