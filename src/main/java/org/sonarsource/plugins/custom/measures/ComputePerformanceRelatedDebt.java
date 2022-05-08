package org.sonarsource.plugins.custom.measures;

import org.sonar.api.ce.measure.Issue;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;
import org.sonar.api.utils.Duration;

import java.util.Collection;
import java.util.List;

import static org.sonar.api.ce.measure.Component.Type.FILE;
import static org.sonarsource.plugins.custom.measures.TDMetrics.PERFORMANCE_RELATED_DEBT;

public class ComputePerformanceRelatedDebt implements MeasureComputer {
    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext measureComputerDefinitionContext) {
        return measureComputerDefinitionContext
                .newDefinitionBuilder()
                .setOutputMetrics(PERFORMANCE_RELATED_DEBT.getKey())
                .build();
    }

    @Override
    public void compute(MeasureComputerContext measureComputerContext) {
        if (measureComputerContext.getComponent().getType() == FILE) {
            List<? extends Issue> issues = measureComputerContext
                    .getIssues();

            Duration performanceTechnicalDebt = Duration.create(0);

            for (Issue issue : issues) {
                Collection<String> issueTags = ((org.sonar.api.issue.Issue) issue).tags();
                boolean isPerformanceIssue = issueTags.stream().anyMatch("performance"::equals);
                if(isPerformanceIssue)
                    performanceTechnicalDebt = performanceTechnicalDebt.add(issue.effort() != null ? issue.effort() : Duration.create(0));
            }
            measureComputerContext.addMeasure(PERFORMANCE_RELATED_DEBT.getKey(), performanceTechnicalDebt.toMinutes());
            return;
        }
        long totalPerformanceDebt = 0;
        for (Measure measure : measureComputerContext.getChildrenMeasures(PERFORMANCE_RELATED_DEBT.key())) {
            totalPerformanceDebt += measure.getLongValue();
        }

        measureComputerContext.addMeasure(PERFORMANCE_RELATED_DEBT.key(), totalPerformanceDebt);
    }
}
