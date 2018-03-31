OPERAÇÕES
===========================================

Descrição
---------
**GET**__
curl localhost:8080/cadastro

curl localhost:8080/cadastro/1

**POST**__
curl -H "Content-Type: application/json" -d "{\"nome\": \"CATATAU\"}" -X POST localhost:8080/cadastro

curl -H "Content-Type: application/json" -d "{\"nome\": \"MESTRE DOS MAGOS\"}" -X POST localhost:8080/cadastro

**PUT**__
curl -H "Content-Type: application/json" -d "{\"nome\": \"CATATAUZIN\"}" -X PUT localhost:8080/cadastro/2

**DELETE**__
curl -X DELETE localhost:8080/cadastro/1
