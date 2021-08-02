import com.intellij.execution.ExecutionListener;
import com.intellij.execution.ExecutionManager;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.openapi.util.Key;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.project.Project;
import com.sun.istack.NotNull;

import java.io.PrintStream;

public class CodeInsights extends AbstractProjectComponent {
    public CodeInsights(@NotNull Project project) {
        super(project);

        project.getMessageBus().connect().subscribe(ExecutionManager.EXECUTION_TOPIC, new ExecutionListener() {
            private ErrorPrinterSettingsState settingsState = ErrorPrinterSettingsState.getInstance();
            private StringBuilder errorMessages = new StringBuilder();

            @Override
            public void processTerminated(@org.jetbrains.annotations.NotNull String executorId, @org.jetbrains.annotations.NotNull ExecutionEnvironment env, @org.jetbrains.annotations.NotNull ProcessHandler handler, int exitCode) {
                System.out.println("Stop.");
                if(settingsState.isPrintEnabled()) {
                    System.out.println(errorMessages.toString());
                    PrintUtility.printString(errorMessages.toString(), settingsState.getPrinterName());
                }
            }

            @Override
            public void processStarted(@org.jetbrains.annotations.NotNull String executorId, @org.jetbrains.annotations.NotNull ExecutionEnvironment env, @org.jetbrains.annotations.NotNull ProcessHandler handler) {
                if(settingsState.isPrintEnabled()){
                    System.out.println("Run.");

                    handler.addProcessListener(new ProcessAdapter() {
                        @Override
                        public void onTextAvailable(ProcessEvent event, Key outputType) {
                            ProcessHandler processHandler = event.getProcessHandler();
                            if (outputType == ProcessOutputTypes.STDERR) {
                                errorMessages.append(event.getText());
                            }
                        }
                    });

                }
            }
        });
    }

}
