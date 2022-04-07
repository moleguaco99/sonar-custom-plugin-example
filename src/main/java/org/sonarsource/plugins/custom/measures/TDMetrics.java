package org.sonarsource.plugins.custom.measures;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;
import org.sonar.api.utils.Duration;

import java.util.List;

import static java.util.Arrays.asList;

public class TDMetrics implements Metrics {

    public static final Metric<Long> PERFORMANCE_RELATED_DEBT = new Metric
            .Builder("performance_related_debt", "Performance Related Debt", Metric.ValueType.WORK_DUR)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setDomain(CoreMetrics.DEVELOPMENT_COST_KEY)
            .create();

    public static final Metric<Long> SECURITY_TECHNICAL_DEBT = new Metric
            .Builder("security_technical_debt", "Security Technical Debt", Metric.ValueType.WORK_DUR)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setDomain(CoreMetrics.DEVELOPMENT_COST_KEY)
            .create();

    public static final Metric<Long> RELIABILITY_TECHNICAL_DEBT = new Metric
            .Builder("reliability_technical_debt", "Reliability Technical Debt", Metric.ValueType.WORK_DUR)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setDomain(CoreMetrics.DEVELOPMENT_COST_KEY)
            .create();

    public static final Metric<Long> EXTENDED_TECHNICAL_DEBT = new Metric
            .Builder("extended_technical_debt", "Extended Technical Debt", Metric.ValueType.WORK_DUR)
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setDomain(CoreMetrics.DEVELOPMENT_COST_KEY)
            .create();

    @Override
    public List<Metric> getMetrics() {
        return asList(PERFORMANCE_RELATED_DEBT, SECURITY_TECHNICAL_DEBT, RELIABILITY_TECHNICAL_DEBT, EXTENDED_TECHNICAL_DEBT);
    }

}
