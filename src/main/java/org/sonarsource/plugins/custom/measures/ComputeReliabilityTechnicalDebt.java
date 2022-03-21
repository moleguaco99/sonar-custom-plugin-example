package org.sonarsource.plugins.custom.measures;

import org.sonar.api.ce.measure.Issue;
import org.sonar.api.ce.measure.MeasureComputer;
import org.sonar.api.rules.RuleType;
import org.sonar.api.utils.Duration;

import java.util.List;

public class ComputeReliabilityTechnicalDebt implements MeasureComputer {
    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext measureComputerDefinitionContext) {
        return measureComputerDefinitionContext
                .newDefinitionBuilder()
                .setOutputMetrics(TDMetrics.RELIABILITY_TECHNICAL_DEBT.getKey())
                .build();
    }

    @Override
    public void compute(MeasureComputerContext measureComputerContext) {
        List<? extends Issue> issues = measureComputerContext.getIssues();
        Duration reliabilityTechnicalDebt = Duration.create(0);
        issues.stream()
                .filter(issue -> RuleType.BUG.equals(issue.type()))
                .forEach(issue -> reliabilityTechnicalDebt.add(issue.effort()));

        measureComputerContext.addMeasure(TDMetrics.SECURITY_TECHNICAL_DEBT.getKey(), TDMetrics.minutesToWorkDays(reliabilityTechnicalDebt));
    }
}
