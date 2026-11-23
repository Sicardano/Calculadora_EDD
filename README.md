# Registro de comandos Git

## 1. Inicialización del repositorio

```bash
git init
```

Inicializa un repositorio Git local en la carpeta del proyecto.

## 2. Comprobación de ramas

```bash
git branch
```

Muestra las ramas locales existentes.

## 3. Creación del primer commit

```bash
git add README.md
git commit -m "Initial commit"
```

El primer comando añade el archivo al área de preparación. El segundo registra los cambios en el historial del repositorio.

## 4. Creación de ramas

```bash
git branch frontend
git branch backend
```

Crea las dos ramas de desarrollo: una para HTML y otra para Java.

## 5. Conexión con GitHub

```bash
git remote add origin https://github.com/Sicardano/Calculadora_EDD.git
```

Vincula el repositorio local con el repositorio remoto de GitHub.

## 6. Comprobación del repositorio remoto

```bash
git remote -v
```

Muestra las direcciones configuradas para descargar y subir cambios.

## 7. Publicación del repositorio

```bash
git push -u origin main
git push -u origin frontend
git push -u origin backend
```

Publica las tres ramas en GitHub. La opción `-u` configura la rama remota de seguimiento.

## 8. Desarrollo del frontend

```bash
git switch frontend
git add index.html
git commit -m "Create calculator HTML form"
git push origin frontend
```

Cambia a la rama `frontend`, registra el archivo HTML y publica los cambios.

## 9. Desarrollo del backend

```bash
git switch backend
git add src/Calculator.java src/Server.java
git commit -m "Implement calculator backend"
git push origin backend
```

Cambia a la rama `backend`, registra los archivos Java y publica los cambios.

## 10. Compilación y ejecución

```bash
javac src/Calculator.java src/Server.java
java -cp src Server
```

Compila los archivos Java y ejecuta el servidor HTTP.

## 11. Fusión de ramas

```bash
git switch main
git merge --no-ff frontend
git merge --no-ff backend
```

Fusiona el trabajo de ambas ramas en `main`, conservando los commits de fusión.

## 12. Publicación final

```bash
git push origin main
```

Sube a GitHub la versión final del proyecto.