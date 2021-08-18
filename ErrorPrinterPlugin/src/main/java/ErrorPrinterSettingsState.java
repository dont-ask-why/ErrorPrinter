import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.application.ApplicationManager;

/**
 * Supports storing the application settings in a persistent way.
 * The {@link State} and {@link Storage} annotations define the name of the data and the file name where
 * these persistent application settings are stored.
 */
@State(
        name = "ErrorPrinterSettingsState",
        storages = {@Storage("SdkSettingsPlugin.xml")}
)

public class ErrorPrinterSettingsState implements PersistentStateComponent<ErrorPrinterSettingsState> {

    private String printerName = "None selected";
    private boolean errEnabled = false;
    private boolean outEnabled = false;

    public void setOutEnabled(boolean outEnabled){
        this.outEnabled = outEnabled;
    }

    public void setErrEnabled(boolean errEnabled) {
        this.errEnabled = errEnabled;
    }

    public void setPrinterName(String printerName) {
        if(printerName != null) {
            this.printerName = printerName;
        }
    }

    public String getPrinterName() {
        return printerName;
    }

    public boolean isErrEnabled() {
        return errEnabled;
    }

    public boolean isOutEnabled(){
        return outEnabled;
    }

    public static ErrorPrinterSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(ErrorPrinterSettingsState.class);
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
