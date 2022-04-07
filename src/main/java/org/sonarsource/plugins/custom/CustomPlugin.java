package org.sonarsource.plugins.custom;

import org.sonar.api.Plugin;
import org.sonarsource.plugins.custom.measures.*;

public class CustomPlugin implements Plugin {
    @Override
    public void define(Context context) {
        context.addExtensions(TDMetrics.class,
                ComputeSecurityTechnicalDebt.class,
                ComputeReliabilityTechnicalDebt.class,
                ComputeExtendedTechnicalDebt.class,
                ComputePerformanceRelatedDebt.class);
    }
}
