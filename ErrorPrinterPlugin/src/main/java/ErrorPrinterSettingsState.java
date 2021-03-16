import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "org.intellij.ErrorPrinterSettingsState",
        storages = {@Storage("SdkSettingsPlugin.xml")}
)

public class ErrorPrinterSettingsState implements PersistentStateComponent<ErrorPrinterSettingsState> {

    private String printerName = "None selected";
    private boolean printEnabled = false;

    public void setPrintEnabled(boolean printEnabled) {
        this.printEnabled = printEnabled;
    }

    public void setPrinterName(String printerName) {
        if(printerName != null) {
            this.printerName = printerName;
        }
    }

    public String getPrinterName() {
        return printerName;
    }

    public boolean isPrintEnabled() {
        return printEnabled;
    }

    public static ErrorPrinterSettingsState getInstance() {
        return ServiceManager.getService(ErrorPrinterSettingsState.class);
    }

    @Override
    public @Nullable ErrorPrinterSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ErrorPrinterSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
