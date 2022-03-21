package org.sonarsource.plugins.custom.measures;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;
import org.sonar.api.utils.Duration;

import java.util.List;

import static java.util.Arrays.asList;

public class TDMetrics implements Metrics {

    private static final int MINUTES_PER_HOUR = 60;
    private static final int HOURS_PER_DAY = 8;

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

    public static Long minutesToWorkDays(Duration workDuration) {
        long workDurationInMinutes = workDuration.toMinutes();
        return workDurationInMinutes / (MINUTES_PER_HOUR * HOURS_PER_DAY);
    }

    @Override
    public List<Metric> getMetrics() {
        return asList(SECURITY_TECHNICAL_DEBT, RELIABILITY_TECHNICAL_DEBT, EXTENDED_TECHNICAL_DEBT);
    }

}
