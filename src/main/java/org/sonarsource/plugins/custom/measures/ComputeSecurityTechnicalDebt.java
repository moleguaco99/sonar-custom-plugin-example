package org.sonarsource.plugins.custom.measures;

import org.sonar.api.ce.measure.Issue;
import org.sonar.api.ce.measure.MeasureComputer;
import org.sonar.api.rules.RuleType;
import org.sonar.api.utils.Duration;

import java.util.List;

public class ComputeSecurityTechnicalDebt implements MeasureComputer {

    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext measureComputerDefinitionContext) {
        return measureComputerDefinitionContext
                .newDefinitionBuilder()
                .setOutputMetrics(TDMetrics.SECURITY_TECHNICAL_DEBT.getKey())
                .build();
    }

    @Override
    public void compute(MeasureComputerContext measureComputerContext) {
        List<? extends Issue> issues = measureComputerContext.getIssues();
        Duration securityTechnicalDebt = Duration.create(0);
        issues.stream()
                .filter(issue -> RuleType.VULNERABILITY.equals(issue.type()))
                .forEach(issue -> securityTechnicalDebt.add(issue.effort()));

        measureComputerContext.addMeasure(TDMetrics.SECURITY_TECHNICAL_DEBT.getKey(), TDMetrics.minutesToWorkDays(securityTechnicalDebt));
    }
}
