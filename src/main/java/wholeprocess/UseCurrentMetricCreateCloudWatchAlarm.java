package wholeprocess;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;
import com.amazonaws.services.cloudwatch.model.ListMetricsResult;
import com.amazonaws.services.cloudwatch.model.Metric;
import com.amazonaws.services.cloudwatch.model.ComparisonOperator;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.services.cloudwatch.model.Statistic;

public class UseCurrentMetricCreateCloudWatchAlarm {
    public static void main(String[] args) {
        final String USAGE =
                "To run this example, supply a metric name and metric namespace\n" +
                        "Ex: ListMetrics <metric-name> <metric-namespace>\n";

        if (args.length != 1) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String namespace = args[0];

        final AmazonCloudWatch cw =
                AmazonCloudWatchClientBuilder.defaultClient();


        ListMetricsResult response = UseCurrentMetricCreateCloudWatchAlarm.getMetrics(cw, namespace);
        System.out.println(response.getMetrics().size());
        for(Metric metric : response.getMetrics()) {
            UseCurrentMetricCreateCloudWatchAlarm.createCloudWatchAlarm(cw, metric, metric.getMetricName());

        }

    }

    public static ListMetricsResult getMetrics(AmazonCloudWatch cw, String namespace){
        ListMetricsRequest request = new ListMetricsRequest()
                .withNamespace(namespace);

        return cw.listMetrics(request);
    }


    public static void createCloudWatchAlarm(AmazonCloudWatch cw, Metric metric, String alarmName){
        PutMetricAlarmRequest request = new PutMetricAlarmRequest()
                .withAlarmName(alarmName)
                .withComparisonOperator(
                        ComparisonOperator.GreaterThanThreshold)
                .withEvaluationPeriods(1)
                .withMetricName(metric.getMetricName())
                .withNamespace(metric.getNamespace())
                .withPeriod(60)
                .withStatistic(Statistic.Average)
                .withThreshold(70.0)
                .withActionsEnabled(false)
                .withAlarmDescription(
                        "Alarm when server CPU utilization exceeds 70%")
                .withUnit(StandardUnit.Seconds)
                .withDimensions(metric.getDimensions());

        cw.putMetricAlarm(request);
    }

}
