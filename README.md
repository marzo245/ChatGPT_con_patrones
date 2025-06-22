# 🧠 ChatGPT_con_patrones

Este proyecto implementa una API REST en Java que expone una función de chat mediante integración con **OpenAI (ChatGPT)**. Utiliza los patrones de diseño **Strategy** y **Factory** para manejar diferentes formas de procesar los mensajes antes de enviarlos a la API de ChatGPT, y está desplegado como una función **serverless en AWS Lambda**, accesible vía **API Gateway**.

---

## 🚀 Características

- ✅ API REST que recibe peticiones POST con mensajes.
- ✅ Comunicación con ChatGPT usando `gpt-3.5-turbo`.
- ✅ Patrones de diseño aplicados: **Strategy** y **Factory**.
- ✅ Dos modos de operación: `simple` y `validado`.
- ✅ Desplegado en **AWS Lambda** usando Java 17.
- ✅ Integración con **API Gateway** para exponer un endpoint público.

---

## 🧬 Ciclo de vida de una petición

```plaintext
Cliente HTTP
    ↓
API Gateway (POST /chat)
    ↓
Lambda (ChatHandler.java)
    ↓
↪ Selección de estrategia (simple o validado)
    ↓
↪ Procesamiento del mensaje según estrategia
    ↓
↪ Envío a OpenAI (ChatGPT)
    ↓
Respuesta generada → Devuelta al cliente en formato JSON
````

---

## 🧠 Modos disponibles (`mode`)

### 🟢 `simple`

* El mensaje se envía tal como lo escribe el usuario.
* No se aplica validación o preprocesamiento.
* Útil para pruebas rápidas.

### 🔒 `validado`

* Verifica que el mensaje tenga al menos 10 caracteres.
* Si no cumple, lanza una excepción.
* Ideal para evitar spam o entradas vacías.

---

## 📦 Cómo usar este proyecto

### 🔧 Requisitos

* Java 17
* Maven
* Cuenta de AWS con permisos para Lambda y API Gateway
* API Key de OpenAI (gpt-3.5-turbo)

---

### ⚙️ Instalación y empaquetado

```bash
# Clonar el proyecto
git clone https://github.com/tu_usuario/ChatGPT_con_patrones.git
cd ChatGPT_con_patrones

# Compilar y empaquetar el .jar
mvn clean package
```

El archivo resultante estará en:

```
target/ChatGPT_con_patrones-1.0-SNAPSHOT-shaded.jar
```

---

## ☁️ Despliegue en AWS Lambda

1. Crear función Lambda con:

   * Runtime: Java 17
   * Handler: `com.diego.chatgpt.handler.ChatHandler::handleRequest`
![image](https://github.com/user-attachments/assets/c11b02dd-df2f-48f7-a08c-c58d8dca13dd)


2. Subir el `.jar` generado.


4. Crear variable de entorno:

| Clave            | Valor                |
| ---------------- | -------------------- |
| `OPENAI_API_KEY` | tu API key de OpenAI |

![image](https://github.com/user-attachments/assets/bd9d09c7-a49a-4cb1-950c-70b425889663)


4. Crear una API REST en API Gateway con el método `POST /chat` apuntando a la Lambda.

![image](https://github.com/user-attachments/assets/35c83cab-1aea-42f6-8078-ca7302b8b21a)


---

## 📤 Ejemplo de petición

### Request

```json
POST /chat
Content-Type: application/json

{
  "mode": "validado",
  "message": "¿Cuál es la capital de Japón?"
}
```
![image](https://github.com/user-attachments/assets/7ebf5af2-b7e1-4891-9266-117d67d6cba8)

### Response

```json
{
  "response": "La capital de Japón es Tokio."
}
```
![image](https://github.com/user-attachments/assets/e9fee51b-d06f-4803-bf45-6acbc98d70d7)


### Validador (forma incorrecta)
![image](https://github.com/user-attachments/assets/fd37da16-4195-4d8c-bae1-5f1ebf780a72)

---
## 📁 Estructura del proyecto

```plaintext
src/
└── main/
    └── java/com/diego/chatgpt/
        ├── handler/
        │   └── ChatHandler.java
        ├── strategy/
        │   ├── MessageStrategy.java
        │   ├── SimpleStrategy.java
        │   └── ValidatedStrategy.java
        ├── factory/
        │   └── StrategyFactory.java
        └── service/
            └── OpenAIClient.java
```

---

## 🧠 Patrones de diseño aplicados

### 🧩 Strategy

Permite definir múltiples comportamientos de procesamiento (`SimpleStrategy`, `ValidatedStrategy`) y elegir uno en tiempo de ejecución.

### 🏭 Factory

Instancia la estrategia adecuada según el parámetro `mode` que recibe la petición.

---

## 🛡️ Seguridad

* La API Key de OpenAI **no debe incluirse en el código fuente**.
* Se configura como **variable de entorno** (`OPENAI_API_KEY`) en Lambda.

---

## 📄 Licencia

Este proyecto está licenciado bajo la [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.html).

Puedes usar, estudiar, modificar y redistribuir el código bajo los términos de esta licencia.

---

## 🙌 Autor

**Diego Chicuazuque**
Estudiante de Ingeniería de Sistemas


