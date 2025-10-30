# Compilación y ejecución

> Notas:
- Se asume que tiene instalado JDK (javac/java) y que su shell es PowerShell en Windows.
- Los comandos están pensados para ejecutarse desde la raíz del proyecto (donde está este archivo).

## Compilar

Abra PowerShell y ejecute:

```powershell
# Navegar al directorio raíz del proyecto (ajuste la ruta si es necesario)
cd "C:\Users\jorge\Desktop\Veterinaria"

# Compilar todas las clases y colocar los .class en la carpeta bin
javac -d bin -encoding UTF-8 src/model/*.java src/controller/*.java src/utils/*.java src/view/*.java src/main/*.java
```

Si su proyecto tiene subpaquetes adicionales adapte el patrón `src/**/*.java` o incluya las rutas necesarias.

## Ejecutar la aplicación

```powershell
# Ejecutar la clase principal
java -cp bin main.Main
```

La aplicación abrirá la interfaz gráfica si todo se compiló correctamente.

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
