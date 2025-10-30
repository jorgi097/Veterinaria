# Implementación de Conceptos de POO
## Sistema de Gestión de Clínica Veterinaria

---

## 📋 Tabla de Contenidos

1. [Clases, Objetos y Métodos](#1-clases-objetos-y-métodos)
2. [Encapsulamiento](#2-encapsulamiento)
3. [Herencia](#3-herencia)
4. [Polimorfismo](#4-polimorfismo)
5. [Manejo de Colecciones](#5-manejo-de-colecciones)
6. [Separación por Paquetes](#6-separación-por-paquetes)
7. [Persistencia con Archivos](#7-persistencia-con-archivos)

---

## 1. Clases, Objetos y Métodos

### 1.1 Clases Implementadas

El sistema cuenta con **7 clases principales** organizadas según su responsabilidad:

#### **Modelo de Dominio (model/)**
- `Empleado` - Clase abstracta base
- `Veterinario` - Empleado especializado
- `Asistente` - Empleado de apoyo
- `Mascota` - Pacientes de la clínica
- `Dueno` - Propietarios de mascotas
- `Cita` - Reservaciones médicas
- `Servicio` - Servicios ofrecidos

#### **Controlador (controller/)**
- `ClinicaController` - Gestiona la lógica de negocio

#### **Vista (view/)**
- `ClinicaView` - Interfaz de usuario por consola

#### **Utilidades (utils/)**
- `PersistenciaArchivos` - Manejo de archivos

### 1.2 Ejemplo de Creación de Objetos

```java
// Crear un dueño
Dueno dueno = new Dueno("D001", "Juan Pérez", 
                        "Calle 123", "555-1234", 
                        "juan@email.com");

// Crear una mascota asociada al dueño
Mascota mascota = new Mascota("M001", "Max", "Perro", 
                              "Labrador", 5, 30.5, dueno);

// Crear un veterinario
Veterinario vet = new Veterinario("V001", "Dra. Ana García",
                                  "ana@clinica.com", "555-5678",
                                  "Lunes a Viernes 9-5", "Cirugía");
```

### 1.3 Métodos con Funcionalidad Específica

```java
// Método en Mascota para agregar al historial
public void agregarHistorialClinico(String registro) {
    this.historialClinico.add(registro);
}

// Método en Veterinario para diagnosticar
public String diagnosticar(Mascota mascota) {
    return "Veterinario " + getNombre() + 
           " está diagnosticando a " + mascota.getNombre();
}

// Método en Servicio para aplicar descuentos
public double aplicarDescuento(double porcentaje) {
    return precio - (precio * porcentaje / 100);
}
```

---

## 2. Encapsulamiento

### 2.1 Atributos Private

**Todas las clases del modelo utilizan atributos privados**, garantizando el principio de encapsulamiento:

```java
public class Mascota {
    // Atributos privados (no accesibles directamente)
    private String id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private double peso;
    private List<String> historialClinico;
    private Dueno dueno;
}
```

### 2.2 Getters y Setters

Cada clase proporciona **métodos públicos para acceder y modificar** los atributos privados de forma controlada:

```java
// Getter - Obtener valor
public String getNombre() {
    return nombre;
}

// Setter - Modificar valor con validación posible
public void setNombre(String nombre) {
    this.nombre = nombre;
}

// Getter para colección (retorna referencia)
public List<String> getHistorialClinico() {
    return historialClinico;
}
```

### 2.3 Beneficios del Encapsulamiento

✅ **Protección de datos**: Los atributos no pueden ser modificados directamente  
✅ **Validación**: Se pueden agregar validaciones en los setters  
✅ **Flexibilidad**: Se puede cambiar la implementación interna sin afectar el código externo  
✅ **Control de acceso**: Solo se exponen los métodos necesarios  

### 2.4 Ejemplo Completo

```java
public class Dueno {
    private String id;
    private String nombre;
    private String telefono;
    private List<Mascota> mascotas;
    
    // Constructor
    public Dueno(String id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.mascotas = new ArrayList<>();
    }
    
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    // Método controlado para agregar mascotas
    public void agregarMascota(Mascota mascota) {
        if (!mascotas.contains(mascota)) {
            mascotas.add(mascota);
        }
    }
}
```

---

## 3. Herencia

### 3.1 Jerarquía de Clases

El sistema implementa una **jerarquía de herencia** con la clase abstracta `Empleado` como base:

```
        Empleado (abstract)
              |
      +-------+-------+
      |               |
  Veterinario    Asistente
```

### 3.2 Clase Padre Abstracta

```java
public abstract class Empleado {
    // Atributos comunes a todos los empleados
    private String id;
    private String nombre;
    private String correo;
    private String telefono;
    private String horario;
    
    // Constructor de la clase padre
    public Empleado(String id, String nombre, String correo, 
                    String telefono, String horario) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.horario = horario;
    }
    
    // Método abstracto que DEBE ser implementado
    public abstract String realizarTarea();
    
    // Método común para todos los empleados
    public String obtenerInformacion() {
        return "ID: " + id + ", Nombre: " + nombre;
    }
}
```

### 3.3 Clases Hijas - Veterinario

```java
public class Veterinario extends Empleado {
    // Atributo específico de Veterinario
    private String especialidad;
    private List<Cita> citasAsignadas;
    
    // Constructor que llama al constructor padre con super()
    public Veterinario(String id, String nombre, String correo, 
                       String telefono, String horario, 
                       String especialidad) {
        super(id, nombre, correo, telefono, horario); // ← Llamada al padre
        this.especialidad = especialidad;
        this.citasAsignadas = new ArrayList<>();
    }
    
    // Implementación del método abstracto
    @Override
    public String realizarTarea() {
        return "Veterinario " + getNombre() + 
               " está atendiendo consultas médicas.";
    }
    
    // Métodos específicos de Veterinario
    public String diagnosticar(Mascota mascota) {
        return "Diagnosticando a " + mascota.getNombre();
    }
}
```

### 3.4 Clases Hijas - Asistente

```java
public class Asistente extends Empleado {
    // Atributo específico de Asistente
    private String area;
    
    // Constructor con super()
    public Asistente(String id, String nombre, String correo,
                     String telefono, String horario, String area) {
        super(id, nombre, correo, telefono, horario); // ← Llamada al padre
        this.area = area;
    }
    
    // Implementación del método abstracto
    @Override
    public String realizarTarea() {
        return "Asistente " + getNombre() + 
               " está apoyando en el área de " + area;
    }
    
    // Métodos específicos de Asistente
    public String prepararSala(String numeroSala) {
        return "Preparando sala " + numeroSala;
    }
}
```

### 3.5 Beneficios de la Herencia

✅ **Reutilización de código**: Los atributos y métodos comunes están en `Empleado`  
✅ **Extensibilidad**: Fácil agregar nuevos tipos de empleados  
✅ **Polimorfismo**: Podemos tratar a todos como `Empleado`  
✅ **Organización**: Jerarquía clara de relaciones  

---

## 4. Polimorfismo

### 4.1 Polimorfismo por Sobrescritura (Override)

Cada tipo de empleado **implementa su propia versión** del método `realizarTarea()`:

```java
// En el controlador - POLIMORFISMO EN ACCIÓN
public void mostrarTareasEmpleados() {
    for (Empleado empleado : empleados.values()) {
        // ¡La misma llamada, pero diferente comportamiento!
        System.out.println(empleado.realizarTarea());
        // Si es Veterinario → "atendiendo consultas médicas"
        // Si es Asistente → "apoyando en el área de..."
    }
}
```

**Salida esperada:**
```
Veterinario Dra. Ana García está atendiendo consultas médicas.
Asistente Carlos López está apoyando en el área de Farmacia.
```

### 4.2 Sobrescritura del método toString()

**Todas las clases sobrescriben `toString()`** para dar su propia representación:

```java
// En Mascota
@Override
public String toString() {
    return "Mascota{" +
            "id='" + id + '\'' +
            ", nombre='" + nombre + '\'' +
            ", especie='" + especie + '\'' +
            ", dueño=" + (dueno != null ? dueno.getNombre() : "Sin dueño") +
            '}';
}

// En Veterinario
@Override
public String toString() {
    return "Veterinario{" +
            "id='" + getId() + '\'' +
            ", nombre='" + getNombre() + '\'' +
            ", especialidad='" + especialidad + '\'' +
            '}';
}
```

### 4.3 Polimorfismo por Sobrecarga (Overload)

**Mismo nombre de método, diferentes parámetros**:

#### Ejemplo 1: Método diagnosticar() en Veterinario

```java
// Versión simple
public String diagnosticar(Mascota mascota) {
    return "Veterinario " + getNombre() + 
           " está diagnosticando a " + mascota.getNombre();
}

// Versión con síntomas adicionales
public String diagnosticar(Mascota mascota, String sintomas) {
    return "Veterinario " + getNombre() + 
           " está diagnosticando a " + mascota.getNombre() + 
           " con síntomas: " + sintomas;
}
```

**Uso:**
```java
vet.diagnosticar(max);                      // Versión 1
vet.diagnosticar(max, "fiebre y vómitos"); // Versión 2
```

#### Ejemplo 2: Método prepararSala() en Asistente

```java
// Versión básica
public String prepararSala(String numeroSala) {
    return "Preparando sala " + numeroSala;
}

// Versión con tipo de procedimiento
public String prepararSala(String numeroSala, String tipoProcedimiento) {
    return "Preparando sala " + numeroSala + 
           " para " + tipoProcedimiento;
}
```

#### Ejemplo 3: Constructor sobrecargado en Servicio

```java
// Constructor básico (para catálogo)
public Servicio(String id, String nombre, 
                String descripcion, double precio) {
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.precio = precio;
}

// Constructor completo (con mascota y fecha)
public Servicio(String id, String nombre, String descripcion, 
                double precio, Mascota mascota, LocalDate fechaRealizado) {
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.precio = precio;
    this.mascota = mascota;
    this.fechaRealizado = fechaRealizado;
}
```

### 4.4 Polimorfismo con instanceof y Casting

```java
// Filtrar solo veterinarios de la lista de empleados
public List<Veterinario> listarVeterinarios() {
    return empleados.values().stream()
            .filter(e -> e instanceof Veterinario)  // ← Verificar tipo
            .map(e -> (Veterinario) e)              // ← Casting
            .collect(Collectors.toList());
}

// Validar en el método agendarCita()
Empleado emp = empleados.get(idVeterinario);
if (!(emp instanceof Veterinario)) {
    return false; // No es un veterinario
}
Veterinario veterinario = (Veterinario) emp; // ← Casting seguro
```

### 4.5 Tipos de Polimorfismo Implementados

| Tipo | Ejemplo | Clase |
|------|---------|-------|
| **Sobrescritura** | `realizarTarea()` | Empleado, Veterinario, Asistente |
| **Sobrescritura** | `toString()` | Todas las clases |
| **Sobrecarga** | `diagnosticar()` | Veterinario |
| **Sobrecarga** | `prepararSala()` | Asistente |
| **Sobrecarga** | Constructor de `Servicio` | Servicio |

---

## 5. Manejo de Colecciones

### 5.1 Tipos de Colecciones Utilizadas

#### **HashMap - Almacenamiento Principal**

En `ClinicaController` se usan **Maps para acceso rápido por ID**:

```java
private Map<String, Mascota> mascotas;
private Map<String, Dueno> duenos;
private Map<String, Empleado> empleados;
private Map<String, Cita> citas;
private Map<String, Servicio> servicios;

// Inicialización
public ClinicaController() {
    mascotas = new HashMap<>();
    duenos = new HashMap<>();
    empleados = new HashMap<>();
    citas = new HashMap<>();
    servicios = new HashMap<>();
}
```

**Operaciones con HashMap:**
```java
// Agregar
mascotas.put(id, mascota);

// Verificar existencia
if (duenos.containsKey(id)) {
    return false; // ID duplicado
}

// Obtener
Mascota m = mascotas.get(id);

// Eliminar
mascotas.remove(id);

// Obtener todos los valores
List<Mascota> lista = new ArrayList<>(mascotas.values());
```

#### **ArrayList - Relaciones Uno a Muchos**

**En la clase `Dueno`** (un dueño puede tener varias mascotas):

```java
private List<Mascota> mascotas;

// Inicialización en el constructor
public Dueno(...) {
    this.mascotas = new ArrayList<>();
}

// Agregar con validación
public void agregarMascota(Mascota mascota) {
    if (!mascotas.contains(mascota)) {
        mascotas.add(mascota);
    }
}

// Eliminar
public void eliminarMascota(Mascota mascota) {
    mascotas.remove(mascota);
}

// Obtener lista completa
public List<Mascota> getMascotas() {
    return mascotas;
}
```

**En la clase `Veterinario`** (un veterinario tiene múltiples citas):

```java
private List<Cita> citasAsignadas;

public Veterinario(...) {
    this.citasAsignadas = new ArrayList<>();
}

public void agregarCita(Cita cita) {
    if (!citasAsignadas.contains(cita)) {
        citasAsignadas.add(cita);
    }
}
```

**En la clase `Mascota`** (historial clínico múltiple):

```java
private List<String> historialClinico;

public Mascota(...) {
    this.historialClinico = new ArrayList<>();
}

public void agregarHistorialClinico(String registro) {
    this.historialClinico.add(registro);
}
```

### 5.2 Streams y Programación Funcional

**Filtrado con Streams**:

```java
// Obtener solo veterinarios
public List<Veterinario> listarVeterinarios() {
    return empleados.values().stream()
            .filter(e -> e instanceof Veterinario)
            .map(e -> (Veterinario) e)
            .collect(Collectors.toList());
}

// Obtener citas de un veterinario específico
public List<Cita> obtenerCitasPorVeterinario(String idVeterinario) {
    return citas.values().stream()
            .filter(c -> c.getVeterinario().getId().equals(idVeterinario))
            .collect(Collectors.toList());
}
```

### 5.3 Iteración sobre Colecciones

**Recorrer Map con for-each**:

```java
for (Empleado empleado : empleados.values()) {
    System.out.println(empleado.realizarTarea());
}
```

**Recorrer ArrayList**:

```java
for (Cita cita : veterinario.getCitasAsignadas()) {
    if (cita.getFechaHora().equals(fechaHora)) {
        return false; // Conflicto de horario
    }
}
```

### 5.4 Resumen de Colecciones

| Colección | Uso | Ubicación | Beneficio |
|-----------|-----|-----------|-----------|
| `HashMap<String, Mascota>` | Almacenar mascotas por ID | `ClinicaController` | Búsqueda O(1) |
| `HashMap<String, Dueno>` | Almacenar dueños por ID | `ClinicaController` | Búsqueda O(1) |
| `HashMap<String, Empleado>` | Almacenar empleados por ID | `ClinicaController` | Búsqueda O(1) |
| `HashMap<String, Cita>` | Almacenar citas por ID | `ClinicaController` | Búsqueda O(1) |
| `ArrayList<Mascota>` | Mascotas de un dueño | `Dueno` | Relación 1:N |
| `ArrayList<Cita>` | Citas de un veterinario | `Veterinario` | Relación 1:N |
| `ArrayList<String>` | Historial clínico | `Mascota` | Registro histórico |

---

## 6. Separación por Paquetes

### 6.1 Estructura de Paquetes

El proyecto sigue el **patrón MVC (Model-View-Controller)** con separación clara:

```
src/
├── model/              ← Clases de dominio (entidades)
│   ├── Empleado.java
│   ├── Veterinario.java
│   ├── Asistente.java
│   ├── Mascota.java
│   ├── Dueno.java
│   ├── Cita.java
│   └── Servicio.java
│
├── controller/         ← Lógica de negocio
│   └── ClinicaController.java
│
├── view/               ← Interfaz de usuario
│   └── ClinicaView.java
│
├── utils/              ← Utilidades generales
│   └── PersistenciaArchivos.java
│
└── main/               ← Punto de entrada
    └── Main.java
```

### 6.2 Responsabilidades de Cada Paquete

#### **📦 Paquete `model`**
**Responsabilidad**: Clases que representan las entidades del dominio

```java
package model;

public class Mascota {
    // Representa UNA mascota con sus atributos y comportamientos
}
```

- ✅ Define la estructura de datos
- ✅ Encapsula atributos con getters/setters
- ✅ Implementa lógica propia de la entidad
- ❌ NO conoce la interfaz de usuario
- ❌ NO contiene lógica de negocio compleja

#### **🎮 Paquete `controller`**
**Responsabilidad**: Coordina la lógica de negocio y gestiona operaciones

```java
package controller;

import model.*;
import java.util.*;

public class ClinicaController {
    private Map<String, Mascota> mascotas;
    
    public boolean registrarMascota(...) {
        // Lógica de validación y registro
    }
}
```

- ✅ Valida datos antes de procesarlos
- ✅ Coordina operaciones entre entidades
- ✅ Mantiene las colecciones de datos
- ✅ Implementa reglas de negocio
- ❌ NO maneja entrada/salida directamente
- ❌ NO formatea datos para presentación

#### **👁️ Paquete `view`**
**Responsabilidad**: Interacción con el usuario

```java
package view;

import controller.ClinicaController;
import model.*;
import java.util.Scanner;

public class ClinicaView {
    private ClinicaController controller;
    private Scanner scanner;
    
    public void mostrarMenuPrincipal() {
        // Menús y entrada de datos
    }
}
```

- ✅ Muestra menús y opciones
- ✅ Captura entrada del usuario
- ✅ Formatea y presenta resultados
- ✅ Delega operaciones al controller
- ❌ NO contiene lógica de negocio
- ❌ NO accede directamente a las entidades

#### **🛠️ Paquete `utils`**
**Responsabilidad**: Funcionalidades de soporte

```java
package utils;

import controller.ClinicaController;
import model.*;
import java.io.*;

public class PersistenciaArchivos {
    public void guardarTodo() {
        // Guardar en archivos
    }
}
```

- ✅ Operaciones de persistencia
- ✅ Funciones auxiliares reutilizables
- ✅ Independiente de la lógica de negocio

#### **🚀 Paquete `main`**
**Responsabilidad**: Punto de entrada de la aplicación

```java
package main;

import view.ClinicaView;
import utils.PersistenciaArchivos;

public class Main {
    public static void main(String[] args) {
        // Iniciar aplicación
    }
}
```

### 6.3 Imports y Dependencias

**Dependencias entre paquetes**:

```
main → view → controller → model
  ↓      ↓
utils ← controller
```

**Ejemplo de imports**:

```java
// En ClinicaController.java
package controller;

import java.util.*;           // Java estándar
import model.*;               // Todas las clases del modelo

// En ClinicaView.java
package view;

import controller.ClinicaController;  // Controlador
import model.*;                       // Modelos para mostrar
import java.util.Scanner;             // Entrada de usuario
```

### 6.4 Beneficios de la Separación

| Beneficio | Descripción |
|-----------|-------------|
| **Mantenibilidad** | Cambios en la vista no afectan el modelo |
| **Reutilización** | El controller puede usarse con diferentes vistas |
| **Testabilidad** | Cada capa se puede probar independientemente |
| **Claridad** | Cada paquete tiene una responsabilidad específica |
| **Escalabilidad** | Fácil agregar nuevas funcionalidades |
| **Colaboración** | Equipos pueden trabajar en paralelo |

---

## 7. Persistencia con Archivos

### 7.1 Estructura de Archivos

Los datos se almacenan en archivos de texto plano en la carpeta `datos/`:

```
datos/
├── duenos.txt       ← Información de dueños
├── mascotas.txt     ← Información de mascotas + historial
├── empleados.txt    ← Veterinarios y asistentes
└── citas.txt        ← Citas programadas
```

### 7.2 Formato de Almacenamiento

#### **duenos.txt**
Formato: `ID|NOMBRE|DIRECCION|TELEFONO|CORREO`

```
D001|Juan Pérez|Calle 123|555-1234|juan@email.com
D002|María García|Av. Principal 456|555-5678|maria@email.com
```

#### **mascotas.txt**
Formato: `ID|NOMBRE|ESPECIE|RAZA|EDAD|PESO|ID_DUENO`

```
M001|Max|Perro|Labrador|5|30.5|D001
HISTORIAL:M001:2025-01-15 - Vacunación antirrábica
HISTORIAL:M001:2025-02-20 - Control de peso
M002|Luna|Gato|Siamés|3|4.2|D002
```

#### **empleados.txt**
Formato: `TIPO|ID|NOMBRE|CORREO|TELEFONO|HORARIO|ESPECIALIDAD/AREA`

```
VET|V001|Dra. Ana García|ana@clinica.com|555-9999|Lunes a Viernes 9-5|Cirugía
ASI|A001|Carlos López|carlos@clinica.com|555-8888|Lunes a Viernes 8-4|Farmacia
```

#### **citas.txt**
Formato: `ID|FECHA_HORA|MOTIVO|ID_MASCOTA|ID_DUENO|ID_VETERINARIO|ESTADO`

```
C001|15/03/2025 10:00|Consulta general|M001|D001|V001|Programada
C002|16/03/2025 14:30|Vacunación|M002|D002|V001|Completada
```

### 7.3 Clase PersistenciaArchivos

#### **Constructor y Preparación**

```java
public class PersistenciaArchivos {
    private static final String ARCHIVO_DUENOS = "datos/duenos.txt";
    private static final String ARCHIVO_MASCOTAS = "datos/mascotas.txt";
    private static final String ARCHIVO_EMPLEADOS = "datos/empleados.txt";
    private static final String ARCHIVO_CITAS = "datos/citas.txt";
    
    private ClinicaController controller;
    
    public PersistenciaArchivos(ClinicaController controller) {
        this.controller = controller;
        crearDirectorioDatos(); // Crear carpeta si no existe
    }
    
    private void crearDirectorioDatos() {
        File directorio = new File("datos");
        if (!directorio.exists()) {
            directorio.mkdir();
        }
    }
}
```

### 7.4 Guardar Datos (Escritura)

#### **Método Principal**

```java
public void guardarTodo() {
    guardarDuenos();
    guardarMascotas();
    guardarEmpleados();
    guardarCitas();
    System.out.println("✅ Datos guardados exitosamente");
}
```

#### **Guardar Dueños**

```java
private void guardarDuenos() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_DUENOS))) {
        List<Dueno> duenos = controller.listarDuenos();
        for (Dueno dueno : duenos) {
            writer.println(dueno.getId() + "|" + 
                          dueno.getNombre() + "|" + 
                          dueno.getDireccion() + "|" + 
                          dueno.getTelefono() + "|" + 
                          dueno.getCorreo());
        }
    } catch (IOException e) {
        System.err.println("Error al guardar dueños: " + e.getMessage());
    }
}
```

#### **Guardar Mascotas (con Historial)**

```java
private void guardarMascotas() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_MASCOTAS))) {
        List<Mascota> mascotas = controller.listarMascotas();
        for (Mascota mascota : mascotas) {
            // Guardar datos básicos de la mascota
            writer.println(mascota.getId() + "|" + 
                          mascota.getNombre() + "|" + 
                          mascota.getEspecie() + "|" + 
                          mascota.getRaza() + "|" + 
                          mascota.getEdad() + "|" + 
                          mascota.getPeso() + "|" + 
                          mascota.getDueno().getId());
            
            // Guardar cada registro del historial clínico
            for (String registro : mascota.getHistorialClinico()) {
                writer.println("HISTORIAL:" + mascota.getId() + ":" + registro);
            }
        }
    } catch (IOException e) {
        System.err.println("Error al guardar mascotas: " + e.getMessage());
    }
}
```

#### **Guardar Empleados (Polimorfismo)**

```java
private void guardarEmpleados() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_EMPLEADOS))) {
        List<Empleado> empleados = controller.listarEmpleados();
        for (Empleado empleado : empleados) {
            // Verificar tipo con instanceof
            if (empleado instanceof Veterinario) {
                Veterinario vet = (Veterinario) empleado;
                writer.println("VET|" + vet.getId() + "|" + 
                              vet.getNombre() + "|" + 
                              vet.getCorreo() + "|" + 
                              vet.getTelefono() + "|" + 
                              vet.getHorario() + "|" + 
                              vet.getEspecialidad());
            } else if (empleado instanceof Asistente) {
                Asistente asist = (Asistente) empleado;
                writer.println("ASI|" + asist.getId() + "|" + 
                              asist.getNombre() + "|" + 
                              asist.getCorreo() + "|" + 
                              asist.getTelefono() + "|" + 
                              asist.getHorario() + "|" + 
                              asist.getArea());
            }
        }
    } catch (IOException e) {
        System.err.println("Error al guardar empleados: " + e.getMessage());
    }
}
```

### 7.5 Cargar Datos (Lectura)

#### **Método Principal**

```java
public void cargarTodo() {
    cargarDuenos();
    cargarMascotas();
    cargarEmpleados();
    cargarCitas();
    System.out.println("✅ Datos cargados exitosamente");
}
```

#### **Cargar Dueños**

```java
private void cargarDuenos() {
    File archivo = new File(ARCHIVO_DUENOS);
    if (!archivo.exists()) return; // Si no existe, no cargar
    
    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] datos = linea.split("\\|"); // Dividir por |
            if (datos.length >= 5) {
                controller.registrarDueno(datos[0], datos[1], 
                                         datos[2], datos[3], datos[4]);
            }
        }
    } catch (IOException e) {
        System.err.println("Error al cargar dueños: " + e.getMessage());
    }
}
```

#### **Cargar Mascotas (con Historial)**

```java
private void cargarMascotas() {
    File archivo = new File(ARCHIVO_MASCOTAS);
    if (!archivo.exists()) return;
    
    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            if (linea.startsWith("HISTORIAL:")) {
                // Procesar línea de historial
                String[] partes = linea.substring(10).split(":", 2);
                if (partes.length == 2) {
                    controller.agregarHistorialClinico(partes[0], partes[1]);
                }
            } else {
                // Procesar línea de mascota
                String[] datos = linea.split("\\|");
                if (datos.length >= 7) {
                    controller.registrarMascota(
                        datos[0], datos[1], datos[2], datos[3],
                        Integer.parseInt(datos[4]), 
                        Double.parseDouble(datos[5]), 
                        datos[6]
                    );
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Error al cargar mascotas: " + e.getMessage());
    }
}
```

#### **Cargar Empleados**

```java
private void cargarEmpleados() {
    File archivo = new File(ARCHIVO_EMPLEADOS);
    if (!archivo.exists()) return;
    
    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] datos = linea.split("\\|");
            if (datos.length >= 7) {
                if (datos[0].equals("VET")) {
                    // Registrar veterinario
                    controller.registrarVeterinario(
                        datos[1], datos[2], datos[3], 
                        datos[4], datos[5], datos[6]
                    );
                } else if (datos[0].equals("ASI")) {
                    // Registrar asistente
                    controller.registrarAsistente(
                        datos[1], datos[2], datos[3], 
                        datos[4], datos[5], datos[6]
                    );
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Error al cargar empleados: " + e.getMessage());
    }
}
```

#### **Cargar Citas (con manejo de fechas)**

```java
private void cargarCitas() {
    File archivo = new File(ARCHIVO_CITAS);
    if (!archivo.exists()) return;
    
    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        while ((linea = reader.readLine()) != null) {
            String[] datos = linea.split("\\|");
            if (datos.length >= 7) {
                // Parsear fecha
                LocalDateTime fechaHora = LocalDateTime.parse(datos[1], formatter);
                
                // Agendar cita
                controller.agendarCita(datos[0], fechaHora, datos[2], 
                                      datos[3], datos[4], datos[5]);
                
                // Restaurar estado
                Cita cita = controller.obtenerCita(datos[0]);
                if (cita != null) {
                    cita.setEstado(datos[6]);
                }
            }
        }
    } catch (Exception e) {
        System.err.println("Error al cargar citas: " + e.getMessage());
    }
}
```

### 7.6 Uso de Try-With-Resources

**Ventaja**: Cierra automáticamente los archivos al terminar

```java
// ✅ Correcto - Se cierra automáticamente
try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
    writer.println("datos");
} // writer se cierra aquí automáticamente

// ❌ Antiguo - Requiere cerrar manualmente
PrintWriter writer = null;
try {
    writer = new PrintWriter(new FileWriter(archivo));
    writer.println("datos");
} finally {
    if (writer != null) writer.close();
}
```

### 7.7 Integración con Main

```java
public class Main {
    public static void main(String[] args) {
        ClinicaController controller = ClinicaController.getInstance();
        PersistenciaArchivos persistencia = new PersistenciaArchivos(controller);
        
        // Cargar datos al iniciar
        persistencia.cargarTodo();
        
        // Ejecutar la aplicación
        ClinicaView vista = new ClinicaView();
        vista.mostrarMenuPrincipal();
        
        // Guardar datos al salir
        persistencia.guardarTodo();
    }
}
```

### 7.8 Características de la Persistencia

✅ **Formato simple**: Archivos de texto legibles  
✅ **Separación de datos**: Un archivo por tipo de entidad  
✅ **Manejo de errores**: Try-catch para IOException  
✅ **Validación**: Verifica existencia antes de cargar  
✅ **Relaciones**: Guarda IDs para reconstruir asociaciones  
✅ **Historial**: Maneja listas dentro de entidades  
✅ **Fechas**: Formatea LocalDateTime correctamente  
✅ **Polimorfismo**: Distingue tipos con prefijos (VET/ASI)  

---

## 📊 Resumen de Conceptos

| Concepto | Implementación | Ubicación |
|----------|----------------|-----------|
| **Clases y Objetos** | 7 clases modelo + controller + view | Todo el proyecto |
| **Encapsulamiento** | Atributos `private` + getters/setters | Todas las clases modelo |
| **Herencia** | `Empleado` → `Veterinario`, `Asistente` | Paquete `model` |
| **Polimorfismo (Override)** | `realizarTarea()`, `toString()` | Empleado y subclases |
| **Polimorfismo (Overload)** | `diagnosticar()`, `prepararSala()` | Veterinario, Asistente |
| **HashMap** | Almacenamiento de entidades por ID | `ClinicaController` |
| **ArrayList** | Relaciones 1:N y listas | Dueno, Veterinario, Mascota |
| **Streams** | Filtrado y transformación | `ClinicaController` |
| **MVC** | Separación model/view/controller | Estructura de paquetes |
| **Persistencia** | Archivos .txt | `PersistenciaArchivos` |

---

## 🎯 Conclusión

Este proyecto demuestra una **implementación completa y profesional** de los principios de Programación Orientada a Objetos:

1. ✅ **Diseño modular** con separación clara de responsabilidades
2. ✅ **Reutilización de código** mediante herencia
3. ✅ **Flexibilidad** gracias al polimorfismo
4. ✅ **Protección de datos** con encapsulamiento
5. ✅ **Eficiencia** usando colecciones apropiadas
6. ✅ **Persistencia** para mantener datos entre ejecuciones
7. ✅ **Escalabilidad** con arquitectura MVC

El sistema está listo para **extenderse** con nuevas funcionalidades manteniendo la estructura y principios de POO.

---

**Autor**: Jorge  
**Proyecto**: Sistema de Gestión de Clínica Veterinaria  
**Fecha**: Octubre 2025
