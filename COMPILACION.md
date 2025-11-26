# Compilación y ejecución

## Descargar las dependencias

Este proyecto requiere los siguientes archivos JAR:

1. **sqlite-jdbc-3.44.1.0.jar** - Driver JDBC de SQLite
2. **slf4j-api-1.7.36.jar** - API de logging (dependencia de SQLite)
3. **slf4j-simple-1.7.36.jar** - Implementación simple de logging

### Descargar con PowerShell (recomendado)

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

### Descargar manualmente

También puede descargar los archivos manualmente desde:
- SQLite JDBC: https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.1.0/sqlite-jdbc-3.44.1.0.jar
- SLF4J API: https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar
- SLF4J Simple: https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/1.7.36/slf4j-simple-1.7.36.jar

Coloque los 3 archivos .jar en la carpeta raíz del proyecto.

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

La aplicación abrirá la interfaz gráfica si todo se compiló correctamente.
La base de datos `clinica.db` se creará automáticamente en la carpeta del proyecto.

## Ejecutar casos de prueba

```powershell
# Ejecutar los casos de prueba
java -cp "bin;sqlite-jdbc-3.44.1.0.jar;slf4j-api-1.7.36.jar;slf4j-simple-1.7.36.jar" main.CasosDePrueba
```

## Limpieza (opcional)

Para eliminar archivos compilados (`.class`) generados en `bin` puede usar:

```powershell
# Eliminar todos los .class dentro de bin (use con precaución)
Remove-Item "bin\*\*.class" -Recurse -Force
```

O, si prefieres eliminar todo el contenido de `bin`:

```powershell
Remove-Item "bin\*" -Recurse -Force
New-Item -ItemType Directory -Path bin | Out-Null
```
