Запуск ElasticSearch

docker run -d --name es762 -p 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.2

http://localhost:9200/


docker run -d --name kibana --net somenetwork -p 5601:5601 kibana:tag


docker run -d -p 9200:9200 -p 5601:5601 nshou/elasticsearch-kibana