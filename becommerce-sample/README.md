OPERAÇÕES
===========================================

Descrição
---------
**GET**
curl localhost:8080/cadastro

curl localhost:8080/cadastro/1

**POST** 
curl -H "Content-Type: application/json" -d "{\"nome\": \"CATATAU\"}" -X POST localhost:8080/cadastro

curl -H "Content-Type: application/json" -d "{\"nome\": \"MESTRE DOS MAGOS\"}" -X POST localhost:8080/cadastro

**PUT**
curl -H "Content-Type: application/json" -d "{\"nome\": \"CATATAUZIN\"}" -X PUT localhost:8080/cadastro/2

**DELETE**
curl -X DELETE localhost:8080/cadastro/1
