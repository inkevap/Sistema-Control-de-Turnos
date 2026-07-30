# Plan de Tareas Detallado — Sistema Control de Turnos

Checklist de ejecución para la entrega del sábado 1 de agosto de 2026, organizado **por caso de uso** (cada bloque es una entrega completa y demostrable de un CU). El Bloque 0 (infraestructura) ya está terminado; los Bloques 1-5 cubren los 5 casos de uso, en el orden que respeta las dependencias reales de datos entre ellos.

**Convención de paquetes:** `sistemacontrolturnos.{presentacion|controlador|dto|servicio|dao|entidad|util}` (proyecto NetBeans/Ant, interfaces e implementaciones en el mismo paquete, sin subpaquetes `impl/`)
**Convención de archivos de datos:** `data/*.txt`, delimitados por `|`

---

## BLOQUE 0 — Infraestructura compartida ✅ COMPLETO

Prerrequisito de los 5 CU. No es un caso de uso en sí, pero todos dependen de esto.

- [x] Proyecto NetBeans (`build.xml`, `nbproject/`), librería `javax.mail` agregada
- [x] Entidades y enums: `Usuario`, `Rol`, `TipoTurno`, `EstadoUsuario`, `Bitacora`
- [x] `util/Constantes.java` (rutas de archivo + `DELIMITADOR`) + `util/ManejadorArchivos.java` (Scanner/FileOutputStream/PrintStream)
- [x] `dao/IBitacoraDAO` + `BitacoraDAOTexto`, `servicio/IBitacoraService` + `BitacoraServiceImpl`
- [x] `dao/IUsuarioDAO` + `UsuarioDAOTexto` (`buscarPorUsuario`, `listarTodos`, `guardar`)
- [x] `dto/CredencialesDTO` + `servicio/IUsuarioService`/`UsuarioServiceImpl.autenticar(CredencialesDTO)` con hash SHA-256
- [x] `presentacion/LoginView` + `controlador/LoginController` (FA01, usa DTO Controlador→Servicio)
- [x] `presentacion/MenuPrincipalView` (opciones según Rol) + `SistemaControlTurnos.java` (Main)
- [x] Compila limpio (`javac`, 19+ archivos) + usuario de prueba sembrado en `data/usuarios.txt` (`admin`/`admin123`)

**Pendiente de este bloque:** commit inicial + push a `origin` (si no lo has hecho ya).

---

## BLOQUE 1 — CU1: Mantenimiento de Usuarios (Admin RRHH) ✅ COMPLETO

Ya tienes: autenticación/login (Bloque 0) y el DAO de Usuario con lectura/escritura básica. Falta todo el flujo propio del CU.

### 1.1 Agregar Empleado (HU1.1, FA04)
- [x] `dto/UsuarioDTO.java` — dpi, nombreCompleto, nombreUsuario, area, turno, rol, supervisorUsuario, correo, contrasena (texto plano, solo en el DTO)
- [x] `IUsuarioService.registrar(UsuarioDTO)` + impl — valida duplicado (`buscarPorUsuario` != null → error), hashea contraseña con `UsuarioServiceImpl.hashear(...)`, delega a `usuarioDAO.guardar(...)`, registra en bitácora
- [x] `presentacion/usuario/AgregarEmpleadoView.java` — formulario completo + combos (Turno, Rol, Supervisor) + botones Registrar/Regresar
- [x] `controlador/UsuarioController.java` — `registrarEmpleado(UsuarioDTO)`
- [x] Mensajes: éxito "se creó correctamente"; error si usuario duplicado

### 1.2 Consultar Usuario (HU1.2, FA02)
- [x] `IUsuarioService.buscar(filtroUsuario, filtroArea)` — filtra sobre `listarTodos()`
- [x] `presentacion/usuario/ConsultarUsuarioView.java` — tabla Usuario/Área/Estado/Acciones + filtro

### 1.3 Inactivar Usuario (HU1.3, FA03, FA05, Postcondición 1)
- [x] Diálogo de motivo (Permiso Personal, Vacaciones, Citas IGSS, Licencia cumpleaños, Suspensión Laboral, Otros)
- [x] `IUsuarioService.inactivar(nombreUsuario, motivo)` — cambia `estado` a `INACTIVO`, sobreescribe con `ManejadorArchivos.escribirTodasLasLineas(...)` (necesitas este método para actualizar una línea existente, no solo agregar)
- [x] `servicio/ICorreoService.java` + `CorreoServiceImpl.java` (JavaMail SMTP, ya está la librería agregada) — notifica al empleado
- [x] Botón "Cancelar" regresa sin cambios (FA05)

### 1.4 Gestión de Roles (HU1.4, FA09)
- [x] `presentacion/usuario/GestionRolesView.java` — usuario + combo rol + Agregar/Eliminar
- [x] `IUsuarioService.agregarRol(...)`/`eliminarRol(...)` — mensajes de éxito específicos

### 1.5 Solicitudes RRHH — Aprobar/Rechazar (HU1.5, FA10, RN02)
- [x] Reutiliza entidad `SolicitudGestionEmpleado` (se construye en Bloque 4, CU5) para las solicitudes que llegan a RRHH
- [x] Validación: la solicitud no debe estar ya procesada por otro Admin RHH — mensaje **"Esta solicitud ya está siendo procesada por otro administrador RHH"**
- [x] Notifica por correo al aprobar/rechazar

### Cierre del bloque
- [x] Prueba manual de punta a punta de los 5 sub-módulos
- [x] Commit en rama `feature/cu1-mantenimiento-usuarios`, push, PR a `main`

---

## BLOQUE 2 — CU3: Asignación de Turnos (Admin Área) (~5 h)

Depende de que existan empleados (Bloque 1).

- [ ] `entidad/Turno.java` — idTurno, nombreUsuarioEmpleado, nombreUsuarioSupervisor, tipo (TipoTurno), fechaInicio, fechaFin
- [ ] `dto/TurnoDTO.java`
- [ ] `dao/ITurnoDAO.java` + `TurnoDAOTexto.java` — formato: `idTurno|empleado|supervisor|tipo|fechaInicio|fechaFin`
- [ ] `servicio/ITurnoService.java` + `TurnoServiceImpl.java`, validando:
  - [ ] Turno de 8 horas / solo Matutino, Vespertino, Nocturno (RN02)
  - [ ] El empleado debe pertenecer al supervisor del Admin Área (RN03)
  - [ ] El supervisor debe ser de la misma área del empleado (RN04.1)
  - [ ] El supervisor debe corresponder al turno asignado (RN04.2)
- [ ] `presentacion/turno/AsignacionTurnoView.java` — Fecha Inicio/Fin, combos Empleado/Turno/Supervisor, botones Asignar/Regresar
- [ ] Mensaje de éxito "Asignación creada con éxito"
- [ ] Prueba manual (caso válido + casos que deben fallar) + commit/push + PR

---

## BLOQUE 3 — CU2: Marcaje (Empleado) ✅ COMPLETO

No depende realmente de CU3 (la validacion de hora usa un limite fijo de 8:00am, no el turno asignado) - se construyo antes por eso.

- [x] `entidad/Marcaje.java` — idMarcaje, nombreUsuario, tipo (ENTRADA, DESCANSO_1, DESCANSO_2, SALIDA), fechaHora
- [x] `dto/MarcajeDTO.java`
- [x] `dao/IMarcajeDAO.java` + `MarcajeDAOTexto.java` — formato: `idMarcaje|nombreUsuario|tipo|fechaHora`
- [x] `servicio/IMarcajeService.java` + `MarcajeServiceImpl.java`, validando:
  - [x] Entrada después de las 8:00 am se marca como tardía (RN01)
  - [x] No repetir el mismo tipo de marcaje el mismo día — mensaje "No puede repetir el mismo marcaje" (RN02)
  - [x] Orden obligatorio: sin entrada no hay descanso 1 (FA05); sin descanso 1 no hay descanso 2 (FA06); sin ambos descansos no hay salida (FA08/FA09)
- [x] `presentacion/marcaje/MarcajeView.java` — botones Entrada/Descanso 1/Descanso 2/Salida
- [x] Vista "Información del Marcaje" — tabla Usuario/Entrada/1er Descanso/2do Descanso/Salida del día
- [x] Prueba manual (fuera de orden debe bloquear, día completo debe funcionar) + commit/push + PR

---

## BLOQUE 4 — CU5: Gestión del Empleado (Empleado) (~6 h)

- [ ] `entidad/SolicitudGestionEmpleado.java` — idSolicitud, nombreUsuarioEmpleado, tipoGestion (enum: VACACIONES, PERMISO_PERSONAL, CITA_IGSS, LICENCIA_CUMPLEANOS, SUSPENSION_LABORAL, OTROS), fechaInicio, fechaFin, motivo, estado
- [ ] `dto/SolicitudGestionEmpleadoDTO.java`
- [ ] `dao/ISolicitudEmpleadoDAO.java` + impl
- [ ] `servicio/ISolicitudEmpleadoService.java` + impl — valida fecha inicio ≥ hoy y fecha fin ≥ fecha inicio (RN02); si falla, no permite continuar (FA05)
- [ ] `presentacion/empleado/GestionEmpleadoView.java` — combo Gestión, Fecha Inicio/Fin, Motivo, Guardar/Regresar. Mensaje: "Gestión creada con éxito"
- [ ] `entidad/SolicitudCambioTurno.java` — idSolicitud, nombreUsuarioEmpleado, turnoActual, turnoNuevo, fechaInicial, fechaNueva, justificacion, estado
- [ ] `presentacion/empleado/SolicitudCambioTurnoView.java` — Fecha Inicial/Turno Inicial/Fecha Nueva/Turno Nuevo/Justificación, Guardar/Limpiar/Regresar. Mensaje: "cambio de turno solicitado con éxito"
- [ ] Prueba manual de ambas solicitudes + commit/push + PR

---

## BLOQUE 5 — CU4: Gestión de solicitudes (Admin Área) (~5 h)

Consume lo que genera el Bloque 4 (solicitudes de empleado) y lo que ya existe de CU1 (solicitudes hacia RRHH).

- [ ] `presentacion/solicitud/SolicitudesCambioTurnoView.java` — lista de solicitudes de cambio de turno pendientes, botones Aprobar/Rechazar
- [ ] `presentacion/solicitud/SolicitudesLicenciaView.java` — lista de solicitudes de licencias/vacaciones pendientes, botones Aprobar/Rechazar
- [ ] `servicio/ISolicitudService.java` + impl:
  - [ ] Aprobar cambio de turno → cambia estado a Aprobado, ejecuta el cambio de turno real, notifica por correo
  - [ ] Rechazar → cambia estado a Rechazada, notifica por correo
  - [ ] Aprobar licencia/vacaciones → cambia estado a "Aprobada administrador área", se envía a RRHH (alimenta el Bloque 1.5)
  - [ ] **Validación crítica (RN02):** impedir que dos administradores procesen la misma solicitud simultáneamente — mensaje "Esta solicitud ya está siendo procesada por otro administrador". Usa un campo `estado`/lock en la entidad de solicitud (ej. marcar "EN_PROCESO" al abrir la acción de aprobar/rechazar antes de confirmar)
- [ ] Prueba manual: dos solicitudes distintas, y el caso de bloqueo simultáneo + commit/push + PR

---

## Definition of Done (aplica a cada bloque)

1. Compila sin errores (`javac`/Clean and Build en NetBeans).
2. El flujo normal básico funciona de punta a punta (Vista → Controlador → DTO → Servicio → DAO → archivo).
3. Al menos un flujo alterno/validación de negocio está probado manualmente.
4. Se registra la acción en bitácora.
5. Código commiteado en su rama `feature/*` y mergeado a `main`.

## Resumen de avance

| Bloque | CU | Estado | Horas restantes est. |
|---|---|---|---|
| 0 | Infraestructura | ✅ Completo | 0 h |
| 1 | CU1 - Mantenimiento de Usuarios | ✅ Completo (rama cu1-mantenimiento-usuarios, 5 features) | 0 h |
| 2 | CU3 - Asignación de Turnos | ⬜ Pendiente | ~5 h |
| 3 | CU2 - Marcaje | ✅ Completo (rama cu2-marcaje, 3 features) | 0 h |
| 4 | CU5 - Gestión del Empleado | ⬜ Pendiente | ~6 h |
| 5 | CU4 - Gestión de solicitudes | ⬜ Pendiente | ~5 h |
| | **Total restante** | | **~33-34 h** |
