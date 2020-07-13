package listmetrics;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;
import com.amazonaws.services.cloudwatch.model.ListMetricsResult;
import com.amazonaws.services.cloudwatch.model.Metric;

import static java.lang.String.format;

/**
 * Lists CloudWatch metrics
 */
public class ListMetrics {

    public static void main(String[] args) {

        final String USAGE =
                "To run this example, supply a metric name and metric namespace\n" +
                        "Ex: ListMetrics <metric-name> <metric-namespace>\n";

//        if (args.length != 2) {
//            System.out.println(USAGE);
//            System.exit(1);
//        }
        if (args.length != 1) {
            System.out.println(USAGE);
            System.exit(1);
        }

//        String name = args[0];
        String namespace = args[0];

        // 这里是用 defaultClient
        final AmazonCloudWatch cw =
                AmazonCloudWatchClientBuilder.defaultClient();

        // 如何用这个 getCredentialsProvider ?
        //final AmazonCloudWatch cw = AmazonCloudWatchClientBuilder.standard().withCredentials(getCredentialsProvider());

//        ListMetricsRequest request = new ListMetricsRequest()
//                .withMetricName(name)
//                .withNamespace(namespace);

        ListMetricsRequest request = new ListMetricsRequest()
                .withNamespace(namespace);

        boolean done = false;

        while(!done) {
            ListMetricsResult response = cw.listMetrics(request);
            System.out.println(format("The length is ", response.getMetrics().size()));
            System.out.println(response.getMetrics().size());
            for(Metric metric : response.getMetrics()) {
                System.out.printf(
                        "Retrieved metric %s", metric.getMetricName());
                System.out.printf(
                        "Retrieved metric %s", metric.getNamespace());
                System.out.printf(
                        "Retrieved metric %s", metric.getDimensions());

            }

            request.setNextToken(response.getNextToken());

            if(response.getNextToken() == null) {
                done = true;
            }
        }
    }
}
