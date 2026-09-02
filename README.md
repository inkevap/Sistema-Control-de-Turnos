# Sistema Control de Turnos

Sistema de escritorio para la gestión de turnos, marcaje de asistencia, usuarios y solicitudes de una empresa, desarrollado como proyecto universitario del curso de Algoritmos.

## Tecnologías

- **Java 1.8.0_231**
- **Interfaz gráfica:** `javax.swing`
- **Persistencia:** archivos de texto plano (`.txt`), delimitados por `|` — sin base de datos ni frameworks
- **Proyecto:** NetBeans (Ant)
- **Dependencias:** [JavaMail](https://javaee.github.io/javamail/) (`lib/javax.mail-1.6.2.jar`) para notificaciones por correo; JUnit 4.13.2 + Hamcrest para pruebas
- **Configuración:** credenciales SMTP externalizadas en `config.properties` (no versionado; ver `config.properties.example`)

## Arquitectura

Arquitectura en capas: `Presentación → Controlador → DTO → Servicio → DAO → Entidad`, con interfaces + implementaciones en el mismo paquete (sin subpaquetes `impl/`), siguiendo los principios SOLID. Ver el detalle completo, diagramas y justificación en [`Documentacion/Documentacion_Tecnica.md`](Documentacion/Documentacion_Tecnica.md).

```
src/sistemacontrolturnos/
├── presentacion/   Vistas Swing (por modulo: usuario/, marcaje/, turno/, ...)
├── controlador/    Orquestan Vista <-> Servicio (usan DTO)
├── dto/            Objetos de transporte Controlador -> Servicio
├── servicio/       Interfaces (I*Service) + implementaciones (*ServiceImpl)
├── dao/            Interfaces (I*DAO) + implementaciones (*DAOTexto)
├── entidad/        Modelo de datos persistente
└── util/           ManejadorArchivos, Constantes, ConfiguracionApp, RegistroErrores
```

## Casos de uso

| CU | Nombre | Estado |
|---|---|---|
| CU1 | Mantenimiento de Usuarios | ✅ Completo |
| CU2 | Marcaje | ✅ Completo |
| CU3 | Asignación de Turnos | ✅ Completo |
| CU4 | Gestión de solicitudes | ✅ Completo |
| CU5 | Gestión del Empleado | ⬜ Pendiente |

Ver el checklist detallado, bloque por bloque, en [`Documentacion/Plan_Tareas_Detallado.md`](Documentacion/Plan_Tareas_Detallado.md), y el backlog de historias de usuario en [`Documentacion/Backlog_Scrum.md`](Documentacion/Backlog_Scrum.md).

### Funcionalidades adicionales

- **Recuperación de contraseña** self-service por código de un solo uso enviado por correo (con expiración).
- **Log central de errores** en `data/log.txt` (`util/RegistroErrores`), enganchado en los `catch` de las capas y en un handler global de excepciones no capturadas.
- **Roles** con rol vacío `SIN_ROL` (patrón Null Object) al remover el rol de un usuario.

## Cómo ejecutar

1. Abrir el proyecto en NetBeans (`File > Open Project`).
2. Confirmar que la librería `lib/javax.mail-1.6.2.jar` está agregada en Properties → Libraries.
3. Copiar `config.properties.example` a `config.properties` y completar las credenciales SMTP (para que las notificaciones por correo se envíen de verdad).
4. **Crear los usuarios iniciales**: copiar `data/usuarios.ejemplo.txt` a `data/usuarios.txt` (ver la sección siguiente).
5. **Clean and Build**.
6. Ejecutar (`F6`), o correr `SistemaControlTurnos.java`.

### Usuarios

Por seguridad, **`data/usuarios.txt` no existe en el repositorio** — no se versionan usuarios reales (contendría datos personales y contraseñas). El archivo se genera en la primera ejecución/registro.

Para tener usuarios por defecto y poder probar el sistema, copia la plantilla:

```
cp data/usuarios.ejemplo.txt data/usuarios.txt
```

Todos los usuarios de ejemplo usan la contraseña **`clave123`**:

| Usuario | Contraseña | Rol | Notas |
|---|---|---|---|
| `admin` | `clave123` | ADMIN_RRHH | Mantenimiento de usuarios, roles, solicitudes RRHH |
| `jefe` | `clave123` | ADMIN_AREA | Supervisor de `empleado1`/`empleado2`; asignación de turnos y gestión de solicitudes |
| `jefe2` | `clave123` | ADMIN_AREA | Supervisor turno vespertino (área Ventas) |
| `empleado1` | `clave123` | EMPLEADO | Marcaje; a cargo de `jefe` |
| `empleado2` | `clave123` | EMPLEADO | Marcaje; a cargo de `jefe` |

> Nota: las contraseñas se guardan hasheadas (SHA-256) en `data/usuarios.txt`, que no se versiona (está en `.gitignore`). Las credenciales de envío de correo se configuran en `config.properties` (a partir de `config.properties.example`) — usa una cuenta y contraseña de aplicación reales para que las notificaciones por correo se envíen de verdad.

## Flujo de Git

`GitHub Flow`: una rama por caso de uso (`cuN-nombre`), con sub-ramas por feature dentro de cada una (`cuN/feature`), que se mergean a la rama del CU conforme se completan. Al terminar el CU completo, su rama se mergea a `main`.
