import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ErrorPrinterSettingsState implements PersistentStateComponent<ErrorPrinterSettingsState> {

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
