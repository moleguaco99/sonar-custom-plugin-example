package org.sonarsource.plugins.custom.measures;

import org.sonar.api.ce.measure.Issue;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;
import org.sonar.api.rules.RuleType;
import org.sonar.api.utils.Duration;

import java.util.List;
import java.util.stream.Collectors;

import static org.sonar.api.ce.measure.Component.Type.FILE;
import static org.sonarsource.plugins.custom.measures.TDMetrics.EXTENDED_TECHNICAL_DEBT;
import static org.sonarsource.plugins.custom.measures.TDMetrics.SECURITY_TECHNICAL_DEBT;

public class ComputeSecurityTechnicalDebt implements MeasureComputer {

    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext measureComputerDefinitionContext) {
        return measureComputerDefinitionContext
                .newDefinitionBuilder()
                .setOutputMetrics(SECURITY_TECHNICAL_DEBT.getKey())
                .build();
    }

    @Override
    public void compute(MeasureComputerContext measureComputerContext) {
        if (measureComputerContext.getComponent().getType() == FILE) {
            List<? extends Issue> issues = measureComputerContext
                    .getIssues()
                    .stream()
                    .filter(issue -> RuleType.VULNERABILITY.equals(issue.type())
                            || RuleType.SECURITY_HOTSPOT.equals(issue.type()))
                    .collect(Collectors.toList());

            Duration securityTechnicalDebt = Duration.create(0);

            for (Issue issue : issues) {
                securityTechnicalDebt = securityTechnicalDebt.add(issue.effort() != null ? issue.effort() : Duration.create(0));
            }
            measureComputerContext.addMeasure(SECURITY_TECHNICAL_DEBT.getKey(), securityTechnicalDebt.toMinutes());
            return;
        }

        long totalSecurityTechnicalDebt = 0;
        for (Measure measure : measureComputerContext.getChildrenMeasures(SECURITY_TECHNICAL_DEBT.key())) {
            totalSecurityTechnicalDebt += measure.getLongValue();
        }

        measureComputerContext.addMeasure(SECURITY_TECHNICAL_DEBT.getKey(), totalSecurityTechnicalDebt);
    }
}
