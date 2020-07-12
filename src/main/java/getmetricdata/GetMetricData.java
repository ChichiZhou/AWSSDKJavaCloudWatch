package getmetricdata;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.GetMetricDataResult;
import com.amazonaws.services.cloudwatch.model.Metric;
import com.amazonaws.services.cloudwatch.model.MetricStat;
import com.amazonaws.services.cloudwatch.model.MetricDataQuery;
import com.amazonaws.services.cloudwatch.model.GetMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.MetricDataResult;
import com.amazonaws.services.cloudwatch.model.AmazonCloudWatchException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
// snippet-end:[cloudwatch.java2.get_metric_data.import]

/**
 * Gets a sample metric data point
 */
public class GetMetricData {

    public static void main(String[] args) {

        Regions region = Regions.US_EAST_1;

        AmazonCloudWatch cw = AmazonCloudWatchClient.builder()
                .withRegion(region)
                .build();

        getMetData(cw) ;
    }

    // snippet-start:[cloudwatch.java2.get_metric_alarm.main]
    public static void getMetData(AmazonCloudWatch cw) {

        try {
            // Set the date
            //Instant start = Instant.ofEpochMilli(new Date().getTime());
            //start = Instant.parse("2019-10-23T10:12:35Z");
            Date start = Date.from(Instant.ofEpochMilli(new Date().getTime()));

            //Instant endDate = Instant.now();
            Date endDate = new Date();

            Metric met = new Metric()
                    .withMetricName("PAGES_VISITED")
                    .withNamespace("SITE/TRAFFIC");

            MetricStat metStat = new MetricStat()
                    .withStat("Minimum")
                    .withPeriod(60)
                    .withMetric(met);

            MetricDataQuery dataQUery = new MetricDataQuery()
                    .withMetricStat(metStat)
                    .withId("foo2")
                    .withReturnData(true);

            List<MetricDataQuery> dq = new ArrayList();
            dq.add(dataQUery);

            GetMetricDataRequest getMetReq = new GetMetricDataRequest()
                    .withMaxDatapoints(100)
                    .withStartTime(start)
                    .withEndTime(endDate)
                    .withMetricDataQueries(dq);

            GetMetricDataResult response = cw.getMetricData(getMetReq);

            List<MetricDataResult> data = response.getMetricDataResults();

            for (int i = 0; i < data.size(); i++) {

                MetricDataResult item = (MetricDataResult) data.get(i);
                System.out.println("The label is "+item.getLabel());
                System.out.println("The status code is "+item.getStatusCode());
            }

        } catch (AmazonCloudWatchException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        // snippet-end:[cloudwatch.java2.get_metric_alarm.main]
    }
}
