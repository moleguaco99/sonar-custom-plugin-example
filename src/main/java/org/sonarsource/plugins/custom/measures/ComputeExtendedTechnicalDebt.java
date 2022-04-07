package org.sonarsource.plugins.custom.measures;

import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;

import static org.sonar.api.ce.measure.Component.Type.FILE;
import static org.sonar.api.measures.CoreMetrics.TECHNICAL_DEBT;
import static org.sonarsource.plugins.custom.measures.TDMetrics.EXTENDED_TECHNICAL_DEBT;
import static org.sonarsource.plugins.custom.measures.TDMetrics.RELIABILITY_TECHNICAL_DEBT;

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
        if (measureComputerContext.getComponent().getType() == FILE) {
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
            return;
        }
        long totalExtendedTechnicalDebt = 0;
        for (Measure measure : measureComputerContext.getChildrenMeasures(EXTENDED_TECHNICAL_DEBT.key())) {
            totalExtendedTechnicalDebt += measure.getLongValue();
        }

        measureComputerContext.addMeasure(TDMetrics.EXTENDED_TECHNICAL_DEBT.getKey(), totalExtendedTechnicalDebt);
    }
}
