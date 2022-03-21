package org.sonarsource.plugins.custom;

import org.sonar.api.Plugin;
import org.sonarsource.plugins.custom.measures.ComputeExtendedTechnicalDebt;
import org.sonarsource.plugins.custom.measures.ComputeReliabilityTechnicalDebt;
import org.sonarsource.plugins.custom.measures.ComputeSecurityTechnicalDebt;
import org.sonarsource.plugins.custom.measures.TDMetrics;

public class CustomPlugin implements Plugin {
    @Override
    public void define(Context context) {
        context.addExtensions(TDMetrics.class,
                ComputeSecurityTechnicalDebt.class,
                ComputeReliabilityTechnicalDebt.class,
                ComputeExtendedTechnicalDebt.class);
    }
}
