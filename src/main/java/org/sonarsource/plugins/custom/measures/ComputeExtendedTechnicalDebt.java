package org.sonarsource.plugins.custom.measures;

import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;

import static org.sonar.api.measures.CoreMetrics.TECHNICAL_DEBT;

/**
 *
 */
public class ComputeExtendedTechnicalDebt implements MeasureComputer {
    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext measureComputerDefinitionContext) {
        return measureComputerDefinitionContext
                .newDefinitionBuilder()
                .setInputMetrics(TDMetrics.SECURITY_TECHNICAL_DEBT.getKey(), TDMetrics.RELIABILITY_TECHNICAL_DEBT.getKey(), TECHNICAL_DEBT.getKey())
                .setOutputMetrics(TDMetrics.EXTENDED_TECHNICAL_DEBT.getKey())
                .build();
    }

    /**
     * Computes sum of technical debt effort for
     * @param measureComputerContext
     */
    @Override
    public void compute(MeasureComputerContext measureComputerContext) {
        Measure maintainabilityTechnicalDebt = measureComputerContext.getMeasure(TECHNICAL_DEBT.getKey());
        Measure reliabilityTechnicalDebt = measureComputerContext.getMeasure(TDMetrics.RELIABILITY_TECHNICAL_DEBT.getKey());
        Measure securityTechnicalDebt = measureComputerContext.getMeasure(TDMetrics.SECURITY_TECHNICAL_DEBT.getKey());

        assert maintainabilityTechnicalDebt != null;
        assert reliabilityTechnicalDebt != null;
        assert securityTechnicalDebt != null;

        long extendedTechnicalDebt = maintainabilityTechnicalDebt.getLongValue() +
                reliabilityTechnicalDebt.getLongValue() +
                securityTechnicalDebt.getLongValue();

        measureComputerContext.addMeasure(TDMetrics.EXTENDED_TECHNICAL_DEBT.getKey(), extendedTechnicalDebt);
    }
}
