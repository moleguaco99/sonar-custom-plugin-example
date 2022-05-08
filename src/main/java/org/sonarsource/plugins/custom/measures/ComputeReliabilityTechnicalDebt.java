package org.sonarsource.plugins.custom.measures;

import org.sonar.api.ce.measure.Issue;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;
import org.sonar.api.rules.RuleType;
import org.sonar.api.utils.Duration;

import java.util.List;
import java.util.stream.Collectors;

import static org.sonar.api.ce.measure.Component.Type.FILE;
import static org.sonarsource.plugins.custom.measures.TDMetrics.RELIABILITY_TECHNICAL_DEBT;

public class ComputeReliabilityTechnicalDebt implements MeasureComputer {
    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext measureComputerDefinitionContext) {
        return measureComputerDefinitionContext
                .newDefinitionBuilder()
                .setOutputMetrics(RELIABILITY_TECHNICAL_DEBT.getKey())
                .build();
    }

    @Override
    public void compute(MeasureComputerContext measureComputerContext) {
        if (measureComputerContext.getComponent().getType() == FILE) {
            List<? extends Issue> issues = measureComputerContext
                    .getIssues()
                    .stream()
                    .filter(issue -> RuleType.BUG.equals(issue.type()))
                    .collect(Collectors.toList());

            Duration reliabilityTechnicalDebt = Duration.create(0);

            for (Issue issue : issues) {
                reliabilityTechnicalDebt = reliabilityTechnicalDebt.add(issue.effort() != null ? issue.effort() : Duration.create(0));
            }
            measureComputerContext.addMeasure(RELIABILITY_TECHNICAL_DEBT.getKey(), reliabilityTechnicalDebt.toMinutes());
            return;
        }
        long totalReliabilityTechnicalDebt = 0;
        for (Measure measure : measureComputerContext.getChildrenMeasures(RELIABILITY_TECHNICAL_DEBT.key())) {
            totalReliabilityTechnicalDebt += measure.getLongValue();
        }

        measureComputerContext.addMeasure(RELIABILITY_TECHNICAL_DEBT.key(), totalReliabilityTechnicalDebt);
    }
}