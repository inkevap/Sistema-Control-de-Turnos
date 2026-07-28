# Product Backlog y Plan de Sprints — Sistema Control de Turnos

## Alcance de esta entrega (sábado 1 de agosto de 2026)

Esta entrega cubre **los 5 Casos de Uso completos**, en el orden que respeta las dependencias de datos entre ellos (ver `Plan_Tareas_Detallado.md` para el detalle bloque por bloque, organizado por CU en vez de por día):

1. **CU1 — Mantenimiento de Usuarios** (base: sin usuarios no hay nada más) — *en progreso, login/autenticación ya completo*
2. **CU3 — Asignación de Turnos** (depende de que existan empleados)
3. **CU2 — Marcaje** (depende de que el empleado tenga turno asignado)
4. **CU5 — Gestión del Empleado** (solicitudes de permisos/cambio de turno)
5. **CU4 — Gestión de solicitudes** (aprobar/rechazar lo que el empleado envía en CU5, y las solicitudes que llegan a RRHH desde CU1)

---

## Roles Scrum (adaptado a trabajo individual)

Al ser un proyecto individual, una sola persona cubre los 3 roles:

| Rol | Responsabilidad | Quién |
|---|---|---|
| Product Owner | Prioriza el backlog, decide qué CU va primero | Estudiante |
| Scrum Master | Vigila el avance diario, ajusta el plan si algo se atrasa | Estudiante |
| Equipo de Desarrollo | Construye el sistema | Estudiante |

---

## Product Backlog (Historias de Usuario)

Formato de criterios de aceptación: **Dado / Cuando / Entonces**, basado directamente en los Flujos Alternos (FA) y Reglas de Negocio (RN) de cada documento de caso de uso.

### Épica 1 — CU1: Mantenimiento de Usuarios (Prioridad 1)

**HU1.1 — Agregar Empleado**
> Como Admin RRHH, quiero registrar un nuevo empleado con sus datos, turno, rol y supervisor, para llevar el control del personal.
- Dado que el Admin RRHH llena el formulario con DPI, Nombre, Usuario, Área, Turno, Rol, Supervisor, Correo y Contraseña, cuando presiona "Registrar", entonces el sistema valida que el usuario no esté duplicado y guarda el empleado (FA04).
- Dado que el usuario ya existe en el sistema, cuando se intenta registrar, entonces se muestra "Error: Ha ocurrido un error al registrar el empleado" (FA04).
- Dado que el registro fue exitoso, entonces se muestra "se creó correctamente" y se guarda en bitácora.

**HU1.2 — Consultar Usuario**
> Como Admin RRHH, quiero buscar y filtrar empleados por usuario o área, para ver su estado actual.
- Dado que hay empleados registrados, cuando el admin busca por usuario o área, entonces el sistema muestra Usuario / Área / Estado / Acciones (FA02).

**HU1.3 — Inactivar Usuario**
> Como Admin RRHH, quiero inactivar a un empleado indicando un motivo, para reflejar su situación real (vacaciones, permiso, suspensión, etc.).
- Dado un empleado activo, cuando el admin selecciona "Inactivar" y elige un motivo (Permiso Personal, Vacaciones, Citas IGSS, Licencia cumpleaños, Suspensión Laboral, Otros), entonces el sistema cambia el estado a inactivo y notifica por correo al empleado (FA03, Postcondición 1).
- Dado que el admin selecciona "Cancelar" en el diálogo de motivo, entonces el sistema regresa sin hacer cambios (FA05).

**HU1.4 — Gestión de Roles**
> Como Admin RRHH, quiero agregar o eliminar un rol a un usuario, para ajustar sus permisos en el sistema.
- Dado un usuario existente, cuando el admin ingresa usuario + rol y presiona "Agregar", entonces se muestra "La asignación de rol ha sido exitosa" (FA09).
- Dado un usuario con un rol asignado, cuando el admin lo elimina, entonces se muestra "La eliminación del rol ha sido exitosa".

**HU1.5 — Aprobar/Rechazar Solicitudes de RRHH**
> Como Admin RRHH, quiero aprobar o rechazar solicitudes enviadas por el Admin de Área, para formalizar licencias/vacaciones a nivel de RRHH.
- Dado que una solicitud está "pendiente autorizar", cuando el admin la aprueba, entonces el sistema valida que no haya sido aprobada previamente por otro Admin RHH (RN02) y notifica por correo al empleado.
- Dado que la solicitud ya fue procesada por otro Admin RHH, cuando un segundo admin intenta aprobarla, entonces el sistema bloquea la acción con el mensaje "Esta solicitud ya está siendo procesada por otro administrador RHH" (FA10).

---

### Épica 2 — CU3: Asignación de Turnos (Prioridad 2)

**HU3.1 — Asignar Turno y Supervisor**
> Como Admin de Área, quiero asignar un turno y supervisor a un empleado en un rango de fechas, para cubrir las necesidades operativas.
- Dado un empleado registrado, cuando el admin selecciona fecha inicio/fin, turno (Matutino/Vespertino/Nocturno) y supervisor, entonces el sistema valida: horario de 8 horas (RN02), que el empleado pertenezca al supervisor del admin (RN03), que el supervisor sea de la misma área (RN04) y que corresponda al turno asignado (RN04).
- Dado que todas las validaciones pasan, cuando se guarda, entonces se muestra "Asignación creada con éxito" y se registra en bitácora.

---

### Épica 3 — CU2: Marcaje (Prioridad 3)

**HU2.1 — Marcar Entrada**
> Como Empleado, quiero marcar mi hora de entrada, para registrar el inicio de mi jornada.
- Dado que el empleado no ha marcado entrada hoy, cuando selecciona "Marcar Entrada", entonces el sistema valida si es después de las 8:00 am (RN01) y guarda el marcaje.
- Dado que el empleado ya marcó entrada hoy, cuando intenta marcarla de nuevo, entonces el sistema muestra "No puede repetir el mismo marcaje" (FA10, RN02).

**HU2.2 — Marcar Primer y Segundo Descanso**
> Como Empleado, quiero marcar mis descansos en orden, para llevar control de mi jornada.
- Dado que no se ha marcado la entrada, cuando el empleado intenta marcar el primer descanso, entonces el sistema muestra "Debe marcar la entrada antes de registrar el descanso" (FA05).
- Dado que no se ha marcado el primer descanso, cuando se intenta marcar el segundo, entonces el sistema muestra "Debe marcar el primer descanso antes de registrar el segundo descanso" (FA06).

**HU2.3 — Marcar Salida**
> Como Empleado, quiero marcar mi salida al final de la jornada.
- Dado que no se han marcado ambos descansos, cuando el empleado intenta marcar salida, entonces el sistema muestra el mensaje correspondiente (FA08/FA09) y no permite el registro.

**HU2.4 — Información del Marcaje**
> Como Empleado, quiero ver mis marcajes del día, para verificar mi registro.
- Dado que el empleado tiene marcajes registrados hoy, cuando entra a "Información del Marcaje", entonces ve Usuario / Entrada / 1er Descanso / 2do Descanso / Salida.

---

### Épica 4 — CU5: Gestión del Empleado (Prioridad 4)

**HU5.1 — Solicitud de Gestión (Vacaciones/Permiso/IGSS/etc.)**
> Como Empleado, quiero enviar una solicitud de vacaciones, permiso, cita al IGSS, licencia de cumpleaños, suspensión u otro motivo, para formalizar mi ausencia.
- Dado que el empleado llena tipo de gestión, fecha inicio, fecha fin y motivo, cuando envía, entonces el sistema valida que la fecha inicio no sea menor a hoy y que la fecha fin no sea menor a la fecha inicio (RN02); si falla, no permite continuar (FA05).
- Dado que las fechas son válidas, cuando se envía, entonces queda "pendiente de aprobación" y se muestra "Gestión creada con éxito".

**HU5.2 — Solicitud de Cambio de Turno**
> Como Empleado, quiero solicitar un cambio de turno con justificación, para ajustar mi horario.
- Dado que el empleado llena fecha inicial, turno inicial, fecha nueva, turno nuevo y justificación, cuando envía, entonces el sistema valida las fechas (RN02) y registra la solicitud con estado "pendiente aprobar turno".

---

### Épica 5 — CU4: Gestión de solicitudes (Prioridad 5)

**HU4.1 — Aprobar/Rechazar Cambio de Turno** (Admin Área)
**HU4.2 — Aprobar/Rechazar Licencias/Vacaciones** (Admin Área, con envío a Admin RRHH)
- Ambas requieren validar que la solicitud no esté siendo procesada simultáneamente por otro administrador (RN02, mensaje "Esta solicitud ya está siendo procesada por otro administrador").

---

## Plan de Sprints comprimido (6 días — 27 jul al 1 ago 2026)

Dado el tiempo real disponible, cada "sprint" aquí es de 1 día. El objetivo diario es cerrar con algo que compile y se pueda demostrar (Definition of Done abajo).

| Día | Fecha | Objetivo | Horas est. |
|---|---|---|---|
| 1 | Lun 27 jul | Setup: estructura de paquetes, `Main`, `ManejadorArchivos`, entidades base, Login + Menú principal | ~8 h |
| 2 | Mar 28 jul | CU1: DTO/Servicio/DAO de Usuario + vista "Agregar Empleado" + "Consultar Usuario" | ~8 h |
| 3 | Mié 29 jul | CU1: terminar (Inactivar, Gestión de Roles, Solicitudes) + pruebas del flujo completo | ~8 h |
| 4 | Jue 30 jul | CU3: Asignación de Turnos completo (con las 4 validaciones RN02-RN04) | ~7 h |
| 5 | Vie 31 jul | CU2: Marcaje completo (entrada, descansos, salida, info, validaciones de duplicado/hora) | ~9 h |
| 6 | Sáb 1 ago | CU5: Gestión del Empleado (ambos formularios) + pruebas finales + empaquetar entrega | ~8 h |

**Total: ~48 horas en 6 días (~8 h/día).** Sigue siendo exigente — para aligerar carga sin perder alcance, considera:
- Simular el envío de correo (registrar en bitácora "correo enviado a X") en vez de integrar SMTP real por ahora; el SMTP real se puede añadir después sin tocar el resto (ver Documentacion_Tecnica.md, capa de Servicio).
- No escribir pruebas unitarias exhaustivas todavía — priorizar que cada CU funcione de punta a punta.

---

## Definition of Done (DoD)

Una historia se considera terminada cuando:
1. Compila sin errores.
2. El flujo normal básico funciona de punta a punta (Vista → Controlador → DTO → Servicio → DAO → archivo).
3. Al menos un flujo alterno/validación de negocio está probado manualmente.
4. Se registra la acción en bitácora.
5. El código está commiteado en su rama `feature/*` y mergeado a `main`.

## Tablero sugerido (Trello / GitHub Projects)

Columnas: **Backlog** | **En progreso** | **Hecho**

Estado inicial sugerido:
- **Backlog:** HU1.1–HU1.5, HU3.1, HU2.1–HU2.4, HU5.1–HU5.2, HU4.1–HU4.2 (futuro)
- **En progreso / Hecho:** se van moviendo conforme avanzas cada día del plan de arriba.
