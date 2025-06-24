# 🧠 ChatGPT_con_patrones

Este proyecto implementa una API REST en Java que expone una función de chat mediante integración con **OpenAI (ChatGPT)**. Aplica los patrones de diseño **Strategy** y **Factory** para seleccionar diferentes formas de procesar los mensajes antes de enviarlos a ChatGPT, y se despliega como una función **serverless en AWS Lambda**, accesible vía **API Gateway**.

---

## 🚀 Características

- ✅ API REST que recibe peticiones POST con mensajes.
- ✅ Comunicación con ChatGPT usando `gpt-3.5-turbo`.
- ✅ Patrones de diseño aplicados: **Strategy** y **Factory**.
- ✅ Procesamiento inteligente de mensajes con validación, limpieza y mejora.
- ✅ Corrección gramatical vía **API pública de LanguageTool** (ligero).
- ✅ Modularizado con `PromptProcessorService`.
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
↪ Procesamiento del mensaje con PromptProcessorService:
     → Limpieza → Mejora semántica → Corrección gramatical (API externa)
    ↓
↪ Envío a OpenAI (ChatGPT)
    ↓
Respuesta generada → Devuelta al cliente en formato JSON
````

---

## 🧠 Modos disponibles (`mode`)

### 🟢 `simple`

* El mensaje se envía tal como lo escribe el usuario.
* Sin validación ni mejora del prompt.
* Útil para pruebas directas o prompts muy controlados.

### 🔒 `validado`

* Verifica que el mensaje tenga al menos 10 caracteres.
* Filtra muletillas como “hola”, “chat”, “gpt”.
* Mejora la estructura del prompt para que sea una pregunta clara.
* Corrige errores gramaticales usando la **API pública de LanguageTool**.
* Ideal para garantizar calidad en los prompts.

---

## 📦 Cómo usar este proyecto

### 🔧 Requisitos

* Java 17
* Maven
* Cuenta de AWS con permisos para Lambda y API Gateway
* API Key de OpenAI (`gpt-3.5-turbo`)

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

> ⚠️ Este `.jar` está optimizado para pesar menos de **50 MB**.

---

## ☁️ Despliegue en AWS Lambda

1. Crear una función Lambda con:

   * Runtime: Java 17
   * Handler: `com.diego.chatgpt.handler.ChatHandler::handleRequest`

2. Subir el `.jar` generado.

3. Crear variable de entorno:

| Clave            | Valor                |
| ---------------- | -------------------- |
| `OPENAI_API_KEY` | tu API key de OpenAI |

4. Crear una API REST en **API Gateway** con método `POST /chat` apuntando a la Lambda.

---

## 📤 Ejemplo de petición

### Request

```json
POST /chat
Content-Type: application/json

{
  "mode": "validado",
  "message": "hola chat, dime cual es la capital de japon"
}
```

### Response

```json
{
  "response": "La capital de Japón es Tokio."
}
```

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
        ├── service/
        │   └── OpenAIClient.java
        └── prompt/
            ├── PromptCleaner.java
            ├── PromptEnhancer.java
            ├── GrammarCorrectorHttp.java
            └── PromptProcessorService.java
```

---

## 🧠 Patrones de diseño aplicados

### 🧩 Strategy

Permite definir múltiples comportamientos de procesamiento (`SimpleStrategy`, `ValidatedStrategy`) y elegir uno en tiempo de ejecución.

### 🏭 Factory

Instancia la estrategia adecuada según el parámetro `mode` que recibe la petición.

---

## ✨ Mejoras de prompt aplicadas

✔ Eliminación de palabras irrelevantes: “hola”, “chat”, “gpt”, etc.
✔ Reestructuración automática del mensaje en forma de pregunta clara.
✔ Capitalización y puntuación básica.
✔ Corrección gramatical externa vía [LanguageTool API](https://api.languagetool.org/v2/).
✔ Modularización total para pruebas y extensibilidad.

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
🚀 Explorando arquitecturas serverless, patrones de diseño y NLP en Java.

```

