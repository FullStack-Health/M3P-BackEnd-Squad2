@echo off
echo Iniciando a geração da imagem %~1
copy Dockerfile ../Dockerfile
cd ..
docker build -t %~1 .
