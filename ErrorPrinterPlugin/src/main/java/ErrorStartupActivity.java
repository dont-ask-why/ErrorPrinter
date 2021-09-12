import com.intellij.execution.ExecutionListener;
import com.intellij.execution.ExecutionManager;
import com.intellij.execution.process.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;

/**
 * StartupActivity is used for calling an activity on the specified Actions.
 * Here it is used to create some instances on the start of the process and reading those out on process termination.
 */
public class ErrorStartupActivity implements StartupActivity {
    /**
     * Method is called on project start to add a Listener to the project.
     * @param project which has been interacted with.
     */
    @Override
    public void runActivity(@NotNull Project project) {
        project.getMessageBus().connect().subscribe(ExecutionManager.EXECUTION_TOPIC, new ExecutionListener() {
            private ErrorPrinterSettingsState settingsState = ErrorPrinterSettingsState.getInstance();
            private StringBuilder errorMessages = new StringBuilder();

            /**
             * This method is called on termination of the project process.
             * It will print all error messages to the console and to an actual printer.
             * @param executorId Necessary for interface implementation. Not used here.
             * @param env        Necessary for interface implementation. Not used here.
             * @param handler    Necessary for interface implementation. Not used here.
             * @param exitCode   Necessary for interface implementation. Not used here.
             */
            @Override
            public void processTerminated(@org.jetbrains.annotations.NotNull String executorId, @org.jetbrains.annotations.NotNull ExecutionEnvironment env, @org.jetbrains.annotations.NotNull ProcessHandler handler, int exitCode) {
                if(settingsState.isErrEnabled() || settingsState.isOutEnabled()) {
                    if(settingsState.isTxtEnabled()) {
                        PrintUtility.writeTextToFile(errorMessages.toString());
                    } else {
                        if(settingsState.isUnformatedEnabled()){
                            PrintUtility.printUnformatted(errorMessages.toString(), settingsState.getPrinterName());
                        } else {
                            PrintUtility.printString(errorMessages.toString(), settingsState.getPrinterName());
                        }
                    }
                }
            }

            /**
             * This method is called on start of the project process.
             * It will add a listener to read out any output of the project and write them to the StringBuilder attribute.
             * @param executorId Necessary for interface implementation. Not used here.
             * @param env        Necessary for interface implementation. Not used here.
             * @param handler    Control entity of the process.
             */
            @Override
            public void processStarted(@org.jetbrains.annotations.NotNull String executorId, @org.jetbrains.annotations.NotNull ExecutionEnvironment env, @org.jetbrains.annotations.NotNull ProcessHandler handler) {
                errorMessages = new StringBuilder();
                handler.addProcessListener(new ProcessAdapter() {
                    /**
                     * Method which adds text from the console to the action listener,
                     * depending on user settings and text type.
                     * @param event which contains more information on how this listener was called.
                     * @param outputType of the text written. Typical are ProcessOutputTypes.STDERR and .STDOUT
                     */
                    @Override
                    public void onTextAvailable(@NotNull ProcessEvent event, @NotNull Key outputType) {
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
