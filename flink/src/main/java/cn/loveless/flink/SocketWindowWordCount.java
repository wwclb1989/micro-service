package cn.loveless.flink;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.io.CsvInputFormat;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer08;
import org.apache.flink.streaming.connectors.wikiedits.WikipediaEditEvent;
import org.apache.flink.streaming.connectors.wikiedits.WikipediaEditsSource;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Arrays;
import java.util.Properties;

public class SocketWindowWordCount {

    public static void main(String[] args) throws Exception {
        // 第一步：设定执行环境
        // 1.设定Flink运行环境，如果在本地启动则创建本地环境，如果在集群上启动，则创建集群环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 2.指定并行度创建本地执行环境
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment(5);
        // 3.指定远程JobManagerIP和RPC商品以及运行程序所在jar包及其依赖包
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.createRemoteEnvironment("JobManagerHost", 6021, 5, "/user/application.jar");


        // 第二步：指定数据源地址，读取输入数据
        // （1）内部数据源
        // 1.文件数据源
//        DataStreamSource<String> textStream = env.readTextFile("/usr/local/data_example.log");
//        env.readFile(new CsvInputFormat<String>(new Path("/usr/local/data_example.csv")) {
//            @Override
//            protected String fillRecord(String s, Object[] objects) {
//                return null;
//            }
//        }, "/usr/local/data_example.csv");

        // 2.Socket数据源
//        DataStreamSource<String> socketDataStream = env.socketTextStream("localhost", 9999);

        // 3.集合数据源
//        DataStreamSource<Tuple2> dataStream = env.fromElements(new Tuple2(1L, 3L), new Tuple2(1L, 5L), new Tuple2(1L, 7L), new Tuple2(1L, 4L), new Tuple2(1L, 2L));

//        String[] elements = new String[]{"hello", "flink"};
//        DataStreamSource<String> dataStream = env.fromCollection(Arrays.asList(elements));

        // （2）外部数据源
        // 1.kafka（pom.xml引入flink-connector-kafka依赖）
//        Properties properties = new Properties();
//        properties.setProperty("bootstrap.servers", "localhost:9092");
//        properties.setProperty("zookeeper.connect", "localhost:2181");
//        properties.setProperty("group.id", "test");
//        DataStream<String> input = env.addSource(new FlinkKafkaConsumer08<>(
//                properties.getProperty("input-data-topic"),
//                new SimpleStringSchema(),
//                properties));

        DataStream<WikipediaEditEvent> dataStream = env.addSource(new WikipediaEditsSource());


        // TODO: 第三步：对数据集指定转换操作逻辑
        // （1）Single-DataStream操作
        // 1.Map[DataStream->DataStream]
        // 2.FlatMap[DataStream->DataStream]
        // 3.Filter[DataStream->DataStream]
        // 4.KeyBy[DataStream->KeyedStream]
        KeyedStream<WikipediaEditEvent, String> keyedStream = dataStream
                .keyBy(WikipediaEditEvent::getUser);
        // 5.Reduce[KeyedStream->DataStream]
        // 6.Aggregations[KeyedStream->DataStream]

        // （2）Multi-DataStream
        // 1.Union[DataStream->DataStream]
        // 2.Connect, CoMap, CoFlatMap[DataStream->DataStream]
        // 3.Split[DataStream->SplitStream]
        // 4.Select[SplitStream->DataStream]
        // 5.Iterate[DataStream->IterativeStream-DataStream]

        // （3）物理分区操作
        // 1.随机分区(Random Partitioning):[DataStream->DataStream]
        // 2.Roundrobin Partitioning:[DataStream->DataStream]
        // 3.Rescaling Partitioning:[DataStream->DataStream]
        // 4.广播操作(Broadcasting):[DataStream->DataStream]
        // 5.自定义分区(Custom Partitioning):[DataStream->DataStream]

        DataStream<Tuple2<String, Long>> result = keyedStream
                .timeWindow(Time.seconds(5))
                .aggregate(new AggregateFunction<WikipediaEditEvent, Tuple2<String, Long>, Tuple2<String, Long>>() {
                    @Override
                    public Tuple2<String, Long> createAccumulator() {
                        return new Tuple2<>("", 0L);
                    }

                    @Override
                    public Tuple2<String, Long> add(WikipediaEditEvent value, Tuple2<String, Long> accumulator) {
                        accumulator.f0 = value.getUser();
                        accumulator.f1 += value.getByteDiff();
                        return accumulator;
                    }

                    @Override
                    public Tuple2<String, Long> getResult(Tuple2<String, Long> accumulator) {
                        return accumulator;
                    }

                    @Override
                    public Tuple2<String, Long> merge(Tuple2<String, Long> a, Tuple2<String, Long> b) {
                        return new Tuple2<>(a.f0, a.f1 + b.f1);
                    }
                });

        // TODO: 第四步：指定计算结果输出位置(DataSink操作)
        // 1.转换成csv文件输出
//        result.writeAsCsv("file:///path/to/person.csv", FileSystem.WriteMode.NO_OVERWRITE);
        // 2.转换成txt文件输出
//        result.writeAsText("file:///path/to/person.txt");
        // 3.输出到指定Socket端口
//        result.writeToSocket("localhost", 8080, new SimpleStringSchema());

        // 4.第三方数据输出
//        FlinkKafkaProducer08<String> kafkaProducer = new FlinkKafkaProducer08<>(
//                "localhost:9092",    // 指定Broker List参数
//                "kafka-topic",         // 指定目标Kafka Topic名称
//                new SimpleStringSchema()       // 设定序列化Schema
//        );
//        dataStream.addSink(kafkaProducer);
        result.print();

        // 第五步：指定名称并触发流式任务
        env.execute("wordCount");
    }

    // 自定义Schema
//    public class SourceEventSchema implements DeserializationSchema

}