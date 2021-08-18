import com.intellij.execution.ExecutionListener;
import com.intellij.execution.ExecutionManager;
import com.intellij.execution.process.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;

public class ErrorStartupActivity implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        project.getMessageBus().connect().subscribe(ExecutionManager.EXECUTION_TOPIC, new ExecutionListener() {
            private ErrorPrinterSettingsState settingsState = ErrorPrinterSettingsState.getInstance();
            private StringBuilder errorMessages = new StringBuilder();

            @Override
            public void processTerminated(@org.jetbrains.annotations.NotNull String executorId, @org.jetbrains.annotations.NotNull ExecutionEnvironment env, @org.jetbrains.annotations.NotNull ProcessHandler handler, int exitCode) {
                System.out.println("Stop.");
                if(settingsState.isErrEnabled() || settingsState.isOutEnabled()) {
                    System.out.println(errorMessages.toString());
                    PrintUtility.printString(errorMessages.toString(), settingsState.getPrinterName());
                }
            }

            @Override
            public void processStarted(@org.jetbrains.annotations.NotNull String executorId, @org.jetbrains.annotations.NotNull ExecutionEnvironment env, @org.jetbrains.annotations.NotNull ProcessHandler handler) {
                errorMessages = new StringBuilder();
                handler.addProcessListener(new ProcessAdapter() {
                    @Override
                    public void onTextAvailable(ProcessEvent event, Key outputType) {
                        if (outputType == ProcessOutputTypes.STDERR && settingsState.isErrEnabled()) {
                            errorMessages.append(event.getText());
                        } else if (outputType == ProcessOutputType.STDOUT && settingsState.isOutEnabled()) {
                            errorMessages.append(event.getText());
                        }
                    }
                });
            }
        });
    }
}
