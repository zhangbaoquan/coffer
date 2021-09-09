package plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CofferPlugin  implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        System.out.println("-----coffer plugin entrance-----");
    }
}
