package kafka.examples;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;


public class ConsumerProducerDemo3
{
    public static void main(String[] args)
    {
        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");
        int messagesToProduce = args.length == 0 ? -1 : Integer.parseInt(args[1].trim());

        System.out.println("ConsumerProducerDemo3");

        KafkaProperties kprops = new KafkaProperties();

        Properties prodProps = new Properties();
        prodProps.put("bootstrap.servers", kprops.KAFKA_BOOTSTRAP_SERVERS);
        prodProps.put("topic", kprops.TOPIC);
        prodProps.put("client.id", "DemoProducer");
        prodProps.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        prodProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prodProps.put("max.block.ms", 10000);
        prodProps.put("request.timeout.ms", 20000);

        Properties consProps = new Properties();
        consProps.put("bootstrap.servers", kprops.KAFKA_BOOTSTRAP_SERVERS);
        consProps.put("topic", kprops.TOPIC);
        consProps.put("group.id", kprops.GROUP_ID);
        consProps.put("auto.offset.reset", kprops.AUTO_OFFSET_RESET);
        consProps.put("enable.auto.commit", "false");
        consProps.put("auto.commit.interval.ms", "1000");
        consProps.put("heartbeat.interval.ms", "3000");
        consProps.put("session.timeout.ms", "30000");
        consProps.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        consProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //consProps.put("max.partition.fetch.bytes", "50");

        ConsumerAssign1 consumer1Thread = new ConsumerAssign1(consProps);
        consumer1Thread.start();

        ConsumerAssign2 consumer2Thread = new ConsumerAssign2(consProps);
        consumer2Thread.start();

        ConsumerAssign3 consumer3Thread = new ConsumerAssign3(consProps);
        consumer3Thread.start();

        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Producer1 producerThread = new Producer1(prodProps, isAsync, messagesToProduce);
        producerThread.start();
    }
}
