使用方法：
============================================================================================
将数据上传到 CloudWatch
1.使用 maven 进行编译
2.在根目录下
/Users/hezhou/zh/IgnitionLearning/ignition-sdk-examples/AWSSDKS3
运行
./run_example.sh PutMetricData '1.0'

============================================================================================
获得某个 metrics 的信息
1.使用 maven 进行编译
2.在根目录下
/Users/hezhou/zh/IgnitionLearning/ignition-sdk-examples/AWSSDKS3
运行
./listmetrics.sh ListMetrics 'SITE/TRAFFIC'

============================================================================================
创建CloudWatch Alarm
1.使用 maven 进行编译
2.在根目录下
/Users/hezhou/zh/IgnitionLearning/ignition-sdk-examples/AWSSDKS3
运行
./createcloudwatchalarm.sh PutCloudWatchAlarm 'test1 01'
