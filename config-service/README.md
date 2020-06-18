#start rabbit mq
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

    - http://localhost:15672/

#zipkin
docker run -d -p 9411:9411 openzipkin/zipkin

    -http://localhost:9411/zipkin/

#actuator
localhost:8012/actuator/

#refresh properties
http://localhost:8012/actuator/bus-refresh
POST -> 204 No content 