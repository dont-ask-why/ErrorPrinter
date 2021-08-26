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

/**
 * PersistentStateComponent stores the settings of the program after closing it. Meaning the data is written to the
 * drive and not only accessed in ram
 */
public class ErrorPrinterSettingsState implements PersistentStateComponent<ErrorPrinterSettingsState> {

    private String printerName = "None selected";
    private boolean errEnabled = false;
    private boolean outEnabled = false;

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
     * @param printerName String to set the selected Printer.
     */
    public void setPrinterName(String printerName) {
        if(printerName != null) {
            this.printerName = printerName;
        }
    }

    /**
     *
     * @return String that represents the selected Printer.
     */
    public String getPrinterName() {
        return printerName;
    }

    /**
     *
     * @return Boolean that indicates if std.err should be printed.
     */
    public boolean isErrEnabled() {
        return errEnabled;
    }

    /**
     *
     * @return Boolean that indicates if std.out should be printed.
     */
    public boolean isOutEnabled(){
        return outEnabled;
    }
}
