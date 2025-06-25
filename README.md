# 🧠 ChatGPT con Patrones de Diseño

Este proyecto implementa una **API REST en Java** que expone una función de chat mediante integración con **OpenAI (ChatGPT)**. Aplica los patrones de diseño **Strategy*## ✨ Pipeline de mejoras de prompt (Modo Validado)

El modo `validado` aplica un pipeline de mejoras a través de `PromptProcessorService`:

### 🧹 **1. PromptCleaner**
- ✅ Eliminación de palabras irrelevantes: "hola", "chat", "gpt", "por favor", "una pregunta"
- ✅ Limpieza de espacios múltiples y caracteres innecesarios
- ✅ Normalización del texto de entrada

### 🎯 **2. PromptEnhancer**
- ✅ Reestructuración automática del mensaje en forma de pregunta clara
- ✅ Capitalización automática de la primera letra
- ✅ Añade signos de interrogación cuando faltan
- ✅ Mejora la estructura gramatical básica

### 📝 **3. GrammarCorrector**
- ✅ Corrección gramatical externa vía [LanguageTool API](https://api.languagetool.org/v2/)
- ✅ Corrección específica para idioma español
- ✅ Manejo resiliente de errores (continúa si falla la API)
- ✅ Aplicación inteligente de sugerencias de corrección

### 🔄 **Flujo del Pipeline**
```
Mensaje original → PromptCleaner → PromptEnhancer → GrammarCorrector → Mensaje optimizado
```

**Ejemplo de transformación:**
```
Entrada: "hola chat gpt dime cual es la capital de japon"
Salida:  "¿Cuál es la capital de Japón?"
```ctory** y **Composite** para seleccionar y procesar diferentes formas de mensajes antes de enviarlos a ChatGPT, y se despliega como una función **serverless en AWS Lambda**, accesible vía **API Gateway**.
```

## 🏗️ Arquitectura y Patrones de Diseño

![Diagrama de Clases](Captura%20de%20pantalla%202025-06-25%20160354.png)

### 🔄 **Strategy Pattern**
- **Interface**: `MessageStrategy`
- **Implementaciones**: `SimpleStrategy` y `ValidatedStrategy`
- **Beneficio**: Permite intercambiar algoritmos de procesamiento de mensajes en tiempo de ejecución

### 🏭 **Factory Pattern**
- **Clase**: `StrategyFactory`
- **Función**: Crea instancias de estrategias basándose en el parámetro `mode`
- **Beneficio**: Desacopla la creación de objetos del código cliente

### 🧩 **Composite Pattern**
- **Clase**: `PromptProcessorService`
- **Componentes**: `PromptCleaner`, `PromptEnhancer`, `GrammarCorrector`
- **Beneficio**: Combina múltiples procesadores en un solo servicio para mejorar la calidad del texto

---

## 🚀 Características

- ✅ **API REST** que recibe peticiones POST con mensajes.
- ✅ **Comunicación con ChatGPT** usando `gpt-3.5-turbo`.
- ✅ **Patrones de diseño aplicados**: **Strategy**, **Factory** y **Composite**.
- ✅ **Procesamiento inteligente** de mensajes con validación, limpieza y mejora.
- ✅ **Corrección gramatical** vía **API pública de LanguageTool** (ligero).
- ✅ **Modularizado** con `PromptProcessorService`.
- ✅ **Desplegado en AWS Lambda** usando Java 17.
- ✅ **Integración con API Gateway** para exponer un endpoint público.
- ✅ **Arquitectura escalable** y mantenible con separación de responsabilidades.

---


## 🧬 Ciclo de vida de una petición

```plaintext
Cliente HTTP
    ↓
API Gateway (POST /chat)
    ↓
Lambda (ChatHandler.java)
    ↓
↪ StrategyFactory selecciona estrategia según 'mode'
    ↓
↪ Si mode="validado":
    ↪ PromptProcessorService ejecuta pipeline:
        → PromptCleaner (limpieza)
        → PromptEnhancer (mejora estructura)
        → GrammarCorrector (corrección gramatical)
    ↓
↪ Si mode="simple": mensaje sin procesar
    ↓
↪ OpenAIClient envía a ChatGPT (gpt-3.5-turbo)
    ↓
Respuesta generada → Devuelta al cliente en formato JSON
```

## 🎯 Ventajas del diseño

### **🔧 Mantenibilidad**
- Código organizado en paquetes por responsabilidad
- Separación clara entre lógica de negocio y infraestructura
- Fácil localización y corrección de errores

### **🚀 Escalabilidad**
- Nuevas estrategias se agregan sin modificar código existente
- Pipeline de procesamiento extensible
- Arquitectura serverless que escala automáticamente

### **🧪 Testabilidad**
- Cada componente puede probarse independientemente
- Interfaces bien definidas facilitan el mocking
- Cobertura de pruebas granular por funcionalidad

### **🔄 Reutilización**
- Componentes modulares reutilizables en otros contextos
- Factory pattern permite intercambiar implementaciones
- Pipeline de procesamiento configurable`

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
        ├── App.java
        ├── handler/
        │   └── ChatHandler.java
        ├── strategy/
        │   ├── MessageStrategy.java
        │   ├── SimpleStrategy.java
        │   ├── ValidatedStrategy.java
        │   └── mejoras/
        │       ├── PromptProcessorService.java
        │       ├── PromptCleaner.java
        │       ├── PromptEnhancer.java
        │       └── GrammarCorrector.java
        ├── factory/
        │   └── StrategyFactory.java
        └── service/
            └── OpenAIClient.java
```

---

## 🧠 Patrones de diseño aplicados

### 🔄 **Strategy Pattern**
Permite definir múltiples comportamientos de procesamiento (`SimpleStrategy`, `ValidatedStrategy`) y elegir uno en tiempo de ejecución según el parámetro `mode`.

**Ventajas:**
- Facilita agregar nuevas estrategias sin modificar código existente
- Permite cambiar algoritmos dinámicamente
- Cumple con el principio Abierto/Cerrado

### 🏭 **Factory Pattern**
La clase `StrategyFactory` instancia la estrategia adecuada según el parámetro `mode` que recibe la petición.

**Ventajas:**
- Centraliza la lógica de creación de objetos
- Desacopla el cliente de las clases concretas
- Facilita el mantenimiento y testing

### 🧩 **Composite Pattern**
`PromptProcessorService` actúa como un compuesto que combina múltiples procesadores (`PromptCleaner`, `PromptEnhancer`, `GrammarCorrector`) en un flujo unificado.

**Ventajas:**
- Trata objetos individuales y composiciones de manera uniforme
- Facilita agregar nuevos procesadores al pipeline
- Mejora la modularidad y reutilización del código

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

