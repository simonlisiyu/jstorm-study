

## maven package with dependencies
mvn clean assembly:assembly

## jstorm run jar
jstorm jar target/jstorm-1.0-SNAPSHOT-jar-with-dependencies.jar com.lsy.jstorm.storm.WordCountTopology

bin/jstorm jar job/jstorm-kafka.jar com.lsy.jstorm.stormKafka.KafkaTopoly

bin/jstorm jar job/jstorm-rt.jar com.lsy.jstorm.rt.storm.RtTopology

bin/jstorm jar job/jstorm-test.jar com.lsy.jstorm.rt.topology.LogTopology

bin/jstorm kill KafkaTopoly

