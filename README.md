# home-acc-beta-2

файлы с документацией 
files with documentation->
ru_home-acc-beta-2_description
en_home-acc-beta-2_description
https://documenter.getpostman.com/view/5121731/SzzoaFg1?version=latest

полезня статья про актуатор
https://habr.com/ru/company/otus/blog/452624/

start:
запуск:

1. docker : 
    - rabbit mq
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
    - zipkin
docker run -d -p 9411:9411 openzipkin/zipkin
2. config-service
3. discovery-service
4. zuul-service
5. user-service
6. bills-service
7. currency-service
8. scheduler-service


