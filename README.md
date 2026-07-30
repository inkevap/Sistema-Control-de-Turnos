# Sistema Control de Turnos

Sistema de escritorio para la gestión de turnos, marcaje de asistencia, usuarios y solicitudes de una empresa, desarrollado como proyecto universitario del curso de Algoritmos.

## Tecnologías

- **Java 1.8.0_231**
- **Interfaz gráfica:** `javax.swing`
- **Persistencia:** archivos de texto plano (`.txt`), delimitados por `|` — sin base de datos ni frameworks
- **Proyecto:** NetBeans (Ant)
- **Dependencias:** [JavaMail](https://javaee.github.io/javamail/) (`lib/javax.mail-1.6.2.jar`) para notificaciones por correo

## Arquitectura

Arquitectura en capas: `Presentación → Controlador → DTO → Servicio → DAO → Entidad`, con interfaces + implementaciones en el mismo paquete (sin subpaquetes `impl/`), siguiendo los principios SOLID. Ver el detalle completo, diagramas y justificación en [`Documentacion/Documentacion_Tecnica.md`](Documentacion/Documentacion_Tecnica.md).

```
src/sistemacontrolturnos/
├── presentacion/   Vistas Swing (por modulo: usuario/, marcaje/, ...)
├── controlador/    Orquestan Vista <-> Servicio (usan DTO)
├── dto/            Objetos de transporte Controlador -> Servicio
├── servicio/       Interfaces (I*Service) + implementaciones (*ServiceImpl)
├── dao/            Interfaces (I*DAO) + implementaciones (*DAOTexto)
├── entidad/        Modelo de datos persistente
└── util/           ManejadorArchivos, Constantes
```

## Casos de uso

| CU | Nombre | Estado |
|---|---|---|
| CU1 | Mantenimiento de Usuarios | ✅ Completo |
| CU2 | Marcaje | ✅ Completo |
| CU3 | Asignación de Turnos | ⬜ Pendiente |
| CU4 | Gestión de solicitudes | ⬜ Pendiente |
| CU5 | Gestión del Empleado | ⬜ Pendiente |

Ver el checklist detallado, bloque por bloque, en [`Documentacion/Plan_Tareas_Detallado.md`](Documentacion/Plan_Tareas_Detallado.md), y el backlog de historias de usuario en [`Documentacion/Backlog_Scrum.md`](Documentacion/Backlog_Scrum.md).

## Cómo ejecutar

1. Abrir el proyecto en NetBeans (`File > Open Project`).
2. Confirmar que la librería `lib/javax.mail-1.6.2.jar` está agregada en Properties → Libraries.
3. **Clean and Build**.
4. Ejecutar (`F6`), o correr `SistemaControlTurnos.java`.

### Usuarios de prueba

| Usuario | Contraseña | Rol |
|---|---|---|
| `admin` | `admin123` | ADMIN_RRHH |
| `wendy` | `empleado123` | EMPLEADO |

> Nota: las credenciales de envío de correo (`Constantes.SMTP_USUARIO`/`SMTP_CONTRASENA`) son placeholders — reemplázalas con una cuenta y contraseña de aplicación reales para que las notificaciones por correo se envíen de verdad.

## Flujo de Git

`GitHub Flow`: una rama por caso de uso (`cuN-nombre`), con sub-ramas por feature dentro de cada una (`cuN/feature`), que se mergean a la rama del CU conforme se completan. Al terminar el CU completo, su rama se mergea a `main`.
