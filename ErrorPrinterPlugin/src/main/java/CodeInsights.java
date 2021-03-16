import com.intellij.execution.ExecutionListener;
import com.intellij.execution.ExecutionManager;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.project.Project;
import com.sun.istack.NotNull;

public class CodeInsights extends AbstractProjectComponent {
    public CodeInsights(@NotNull Project project) {
        super(project);

        project.getMessageBus().connect().subscribe(ExecutionManager.EXECUTION_TOPIC, new ExecutionListener() {
            @Override
            public void processTerminated(@org.jetbrains.annotations.NotNull String executorId, @org.jetbrains.annotations.NotNull ExecutionEnvironment env, @org.jetbrains.annotations.NotNull ProcessHandler handler, int exitCode) {
                System.out.println("Stop.");
            }

            @Override
            public void processStarted(@org.jetbrains.annotations.NotNull String executorId, @org.jetbrains.annotations.NotNull ExecutionEnvironment env, @org.jetbrains.annotations.NotNull ProcessHandler handler) {
                System.out.println("Run.");
            }
        });
    }

}
