# Compilación y ejecución

## Descargar las dependencias

Este proyecto requiere los siguientes archivos JAR:

1. **sqlite-jdbc-3.44.1.0.jar** - Driver JDBC de SQLite
2. **slf4j-api-1.7.36.jar** - API de logging (dependencia de SQLite)
3. **slf4j-simple-1.7.36.jar** - Implementación simple de logging

### Descargar con PowerShell

Abra PowerShell en la carpeta del proyecto y ejecute:

```powershell
# Navegar al directorio raíz del proyecto
cd "C:\Users\jorge\Downloads\Veterinaria-1"

# Descargar el driver de SQLite
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.1.0/sqlite-jdbc-3.44.1.0.jar" -OutFile "sqlite-jdbc-3.44.1.0.jar"

# Descargar SLF4J API
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar" -OutFile "slf4j-api-1.7.36.jar"

# Descargar SLF4J Simple
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/1.7.36/slf4j-simple-1.7.36.jar" -OutFile "slf4j-simple-1.7.36.jar"
```

## Compilar

Abra PowerShell y ejecute:

```powershell
# Navegar al directorio raíz del proyecto (ajuste la ruta si es necesario)
cd "C:\Users\user\Veterinaria"

# Compilar todas las clases y colocar los .class en la carpeta bin
javac -cp "sqlite-jdbc-3.44.1.0.jar" -d bin -encoding UTF-8 -sourcepath src src/main/Main.java src/main/CasosDePrueba.java
```

## Ejecutar la aplicación

```powershell
# Ejecutar la clase principal (incluyendo todas las dependencias en el classpath)
java -cp "bin;sqlite-jdbc-3.44.1.0.jar;slf4j-api-1.7.36.jar;slf4j-simple-1.7.36.jar" main.Main
```

## Limpieza (opcional)

Para eliminar archivos compilados (`.class`):

```powershell
# Eliminar todos los .class dentro de bin
Remove-Item "bin\*\*.class" -Recurse -Force
```