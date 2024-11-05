# Etapa 1: Compilar o projeto Angular
FROM node:18-alpine AS build-frontend

WORKDIR /app/frontend

# Copia os arquivos do projeto Angular
COPY M3P-FrontEnd-Squad2/ ./
RUN npm install
RUN npm run build --prod

# Etapa 2: Compilar o projeto Spring
FROM maven:3.9.7-eclipse-temurin-21-alpine AS build-backend

WORKDIR /app/backend

# Copia os arquivos do projeto Spring
COPY M3P-BackEnd-Squad2/pom.xml ./
COPY M3P-BackEnd-Squad2/src ./src
RUN mvn package

# Etapa 3: Criar a imagem final
FROM eclipse-temurin:21-jdk-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia o jar compilado da etapa anterior
COPY --from=build-backend /app/backend/target/*.jar app.jar

# Copia os arquivos estáticos do Angular
COPY --from=build-frontend /app/frontend/dist/labmedical/browser /app/public/

# Instale Node.js (se necessário para servir arquivos estáticos)
RUN apk add --no-cache nodejs npm

# Expõe a porta da aplicação
EXPOSE ${PORT}

# Comando para rodar a aplicação Spring
CMD ["java", "-jar", "app.jar"]

