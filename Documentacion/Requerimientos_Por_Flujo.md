# Requerimientos por Caso de Uso y Flujo

Checklist de todo lo que debe programarse, extraido de los 5 documentos de casos de uso, organizado por flujo (Flujo Normal Basico, cada Flujo Alterno, Reglas de Negocio, Postcondiciones).

## CU1 - Mantenimiento de Usuarios

### Flujo Normal Basico
- [x] Login con usuario y contrasena
- [x] Menu con las opciones "Mantenimiento de usuario" y "Gestion de Roles"
- [x] Al entrar a "Mantenimiento de usuario": submenu con Agregar Empleado, Consultar Usuario, Solicitudes, Boton Regresar
- [x] Agregar Empleado: formulario con DPI, Nombre Completo, Usuario, Area, Turno (Matutino/Vespertino/Diurno), Rol (Empleado/Admin Area/Admin RHH), Supervisor, Correo, Contrasena, Boton Registrar, Boton Regresar
- [x] Validar que el usuario no este duplicado antes de guardar
- [ ] Preguntar "Verificar la entrada duplicada es parte del DAO o es Logica de negocio (Capa de servicio)?"
- [x] Guardar el empleado creado
- [x] Mensaje de exito "se creo correctamente"
- [x] Guardar en bitacora la accion de agregar

### FA01 - Validacion de Credenciales
- [x] Validar credenciales incorrectas
- [x] Mensaje "Credenciales incorrectas"
- [x] Retorna al login

### FA02 - Consultar Usuario
- [x] Filtro de busqueda con lista de empleados
- [x] Buscar por el filtro
- [x] Mostrar Usuario, Area, Estado (Activo/Inactivo), Acciones
- [ ] Preguntar "Acciones es solo inactivar o tambien modificar informacion del usuario? en el CU1 solo se menciona inactivar"
- [x] Boton Regresar

### FA03 - Estado Inactivo
- [x] Validar que el usuario este activo antes de mostrar la opcion
- [x] Accion "inactivar"
- [x] Combo de motivos: Permiso Personal, Vacaciones, Citas al IGSS, Licencia de cumpleanos (1 dia), Suspension Laboral, Otros
- [x] Botones Aceptar y Cancelar
- [x] Al Aceptar: mensaje "Se inactivo al empleado 000-000"
- [x] Actualizar el estado del empleado a inactivo
- [x] Guardar en bitacora

### FA04 - Error al agregar empleado
- [x] Validar que el usuario ya existe en base de datos
- [x] Mensaje "Error: Ha ocurrido un error al registrar el empleado"
- [x] Regresa al formulario de Agregar Empleado

### FA05 - Boton Cancelar
- [x] Cancelar regresa al dialogo de motivo sin hacer cambios

### FA06 - Opcion Solicitudes
- [x] Lista de solicitudes enviadas por el empleado con botones Aprobar, Rechazar, Regresar
- [x] Al Aprobar: validar que la solicitud no haya sido aprobada previamente por otro Admin RHH
- [x] Notificar por correo la confirmacion de aprobacion al empleado
- [x] Boton Regresar

### FA07 - Boton Rechazar
- [ ] Validar que la solicitud no haya sido aprobada previamente por otro Admin RHH
- [x] Notificar por correo el rechazo al empleado

### FA08 - Boton Regresar
- [x] Regresa a la pantalla principal

### FA09 - Opcion Gestion de Roles (Agregar)
- [x] Pantalla con Agregar Rol (boton Agregar), Eliminar Rol (boton Eliminar), Boton Regresar
- [ ] Preguntar "Es necesario el boton regresar si con cerrar la ventana se logra el mismo resultado"
- [x] Ingresar usuario y rol
- [x] Boton Agregar
- [x] Mensaje de exito "La asignacion de rol ha sido exitosa"

### FA09 - Opcion Eliminar Rol
- [x] Ingresar usuario y el rol a eliminar
- [x] Boton Eliminar
- [x] Mensaje de exito "La eliminacion del rol ha sido exitosa"
- [ ] Preguntar "Al eliminar el rol queda el rol mas bajo que seria "EMPLEADO" o deberia quedar un rol vacio"

### FA10 - Solicitud ya aprobada
- [x] Validar que la solicitud ya fue aprobada previamente
- [x] Bloquear la accion de aprobacion
- [ ] Mensaje "Esta solicitud ya esta siendo procesada por otro administrador RRHH"
- [ ] Preguntar " Si la solicitud ya fue aprobada pasa a solicitud respondida, debo mostrar este mensaje aun?" 

### Postcondiciones
- [x] Enviar correo electronico al empleado notificando el motivo de su inactivacion

### Reglas de Negocio
- [ ] RN01: solo el administrador modifica a los empleados
- [ ] RN02: una solicitud solo puede ser aprobada por un Admin RHH; bloquear que una solicitud ya aprobada o rechazada se vuelva a procesar
- [ ] Preguntar "RN02: esto aplica? porque yo estoy moviendo las solicitudes respondidas a una tabla no modificable"

---

## CU2 - Marcaje

### Flujo Normal Basico
- [ ] Login con usuario y contrasena
- [ ] Opcion Marcaje
- [ ] Pantalla con opciones: Timer, Marcar Entrada, Marcar Primer descanso, Marcar Segundo descanso, Marcar salida, Informacion del Marcaje, Boton Regresar
- [ ] Marcar Entrada valida la hora de entrada
- [ ] Mensaje "Marcaje realizado con exito"
- [ ] Guardar en bitacora

### FA01 - Validacion de Credenciales
- [ ] Validar credenciales incorrectas
- [ ] Mensaje "Credenciales incorrectas"
- [ ] Retorna al login

### FA02 - Validacion entrada tarde
- [ ] Validar que la entrada se haya realizado despues de las 8:00 am

### FA03 - Marcaje primer Descanso
- [ ] Seleccionar marcar descanso
- [ ] Validar que se haya marcado la entrada
- [ ] Mensaje de exito al registrar

### FA04 - Marcaje segundo Descanso
- [ ] Seleccionar marcar descanso
- [ ] Validar que se haya marcado el primer descanso
- [ ] Mensaje de exito al registrar

### FA05 - Mensaje de alerta Primer descanso
- [ ] Validar que no se ha marcado la entrada
- [ ] Mensaje "Debe marcar la entrada antes de registrar el descanso."

### FA06 - Mensaje de alerta segundo descanso
- [ ] Validar que no se ha marcado el primer descanso
- [ ] Mensaje "Debe marcar el primer descanso antes de registrar el segundo descanso."

### FA07 - Marcar salida
- [ ] Seleccionar marcar salida
- [ ] Validar que se hayan marcado ambos descansos
- [ ] Mensaje de exito al registrar

### FA08 - Mensaje de alerta Salida (falta primer descanso)
- [ ] Validar que no se ha marcado el primer descanso
- [ ] Mensaje "Debe marcar el primer descanso antes de registrar la salida."

### FA09 - Mensaje de alerta Salida (falta segundo descanso)
- [ ] Validar que no se ha marcado el segundo descanso
- [ ] Mensaje "Debe marcar el segundo descanso antes de registrar la salida."

### FA10 - Marcaje repetido
- [ ] Validar que ya se realizo el mismo tipo de marcaje en la jornada actual
- [ ] Bloquear el nuevo registro
- [ ] Mensaje "No puede repetir el mismo marcaje"

### Informacion del Marcaje
- [ ] Opcion Informacion del marcaje
- [ ] Mostrar la informacion de los marcajes realizados
- [ ] Boton regresar

### Reglas de Negocio
- [ ] RN01: la entrada es valida antes o a las 8:00 am; despues de las 8:01 am se marca como tardia
- [ ] RN02: no permitir registrar el mismo tipo de marcaje mas de una vez en la misma jornada

---

## CU3 - Asignacion de Turnos

### Flujo Normal Basico
- [ ] Login con usuario y contrasena
- [ ] Entrar al modulo de asignacion de turnos
- [ ] Pantalla con Fecha Inicio, Fecha Fin, combo Empleados, combo Turno (Matutino/Vespertino/Diurno), combo Supervisor, Boton Asignar, Boton Regresar
- [ ] Seleccionar empleado, fechas, turno y supervisor
- [ ] Boton Guardar
- [ ] Validar que la asignacion cumpla con los horarios laborales
- [ ] Validar que el empleado pertenezca al supervisor del administrador del area
- [ ] Validar que el supervisor seleccionado pertenezca a la misma area del empleado
- [ ] Validar que el supervisor seleccionado corresponda al turno asignado
- [ ] Guardar el turno asignado
- [ ] Mensaje "Asignacion creada con exito"
- [ ] Guardar en bitacora

### FA01 - Validacion de Credenciales
- [ ] Validar credenciales incorrectas
- [ ] Mensaje "Credenciales incorrectas"
- [ ] Retorna al login

### FA02 - Boton Regresar
- [ ] Regresa a la pantalla principal

### Postcondiciones
- [ ] El turno y supervisor quedan asignados correctamente y registrados
- [ ] El empleado puede visualizar su nuevo turno asignado
- [ ] Bitacora de la asignacion

### Reglas de Negocio
- [ ] RN01: solo el administrador modifica turnos que perjudiquen los horarios de los empleados
- [ ] RN02: turno de 8 horas; turnos asignables son Matutino, Vespertino, Nocturno
- [ ] RN03: el Admin Area solo puede asignar turnos a empleados que lo tengan como supervisor
- [ ] RN04: el supervisor debe pertenecer a la misma area del empleado y corresponder al turno asignado

---

## CU4 - Gestion de solicitudes

### Flujo Normal Basico
- [ ] Login con usuario y contrasena
- [ ] Entrar al modulo de gestion de solicitudes
- [ ] Pantalla con las opciones Solicitudes Cambio de Turno y Solicitudes Licencias y Vacaciones
- [ ] Solicitudes Cambio de Turno: lista de todas las solicitudes con Aprobar, Rechazar, Boton Guardar
- [ ] Al Aprobar: validar que la solicitud no este siendo procesada por otro administrador
- [ ] Cambiar el estado a "turno Aprobado"
- [ ] Ejecutar el cambio de turno real
- [ ] Notificar al empleado por correo
- [ ] Guardar en bitacora

### FA01 - Validacion de Credenciales
- [ ] Validar credenciales incorrectas
- [ ] Mensaje "Credenciales incorrectas"
- [ ] Retorna al login

### FA02 - Opcion Rechazar Turno
- [ ] Seleccionar Rechazar
- [ ] Cambiar el estado a "rechazada administrador"
- [ ] Notificar al empleado por correo el rechazo

### FA03 - Solicitudes Licencias y Vacaciones
- [ ] Lista de todas las solicitudes de licencias y vacaciones enviadas por el empleado
- [ ] Opciones Aprobar, Rechazar, Boton Regresar por cada solicitud
- [ ] Al Aprobar: cambiar estado a "Aprobada administrador area" y enviar al administrador de RRHH
- [ ] Notificar al empleado por correo electronico la aprobacion
- [ ] Boton Guardar
- [ ] Guardar en bitacora

### FA04 - Opcion Rechazar Licencias
- [ ] Validar que la solicitud no este siendo procesada por otro administrador
- [ ] Cambiar el estado a "Rechazada administrador area"
- [ ] Notificar al empleado por correo el rechazo

### FA05 - Solicitud en proceso
- [ ] Validar que la solicitud ya esta siendo procesada por otro administrador
- [ ] Bloquear la accion de aprobacion o rechazo
- [ ] Mensaje "Esta solicitud ya esta siendo procesada por otro administrador"

### Postcondiciones
- [ ] Enviar correo electronico al empleado notificando el resultado de su solicitud

### Reglas de Negocio
- [ ] RN01: los cambios a las solicitudes de empleados los aprueba o rechaza el administrador
- [ ] RN02: impedir que dos administradores procesen simultaneamente la misma solicitud

---

## CU5 - Gestion del Empleado

### Flujo Normal Basico
- [ ] Login con usuario y contrasena
- [ ] Pantalla con las opciones Gestiones del Empleado, Cambios de Turno, Boton Salir
- [ ] Gestiones del Empleado: combo Gestiones (Vacaciones, Permiso personal, Citas al IGSS, Licencia de cumpleanos, Suspension Laboral, Otros), Fecha Inicio, Fecha Fin, Motivo de solicitud, Boton Guardar, Boton Regresar
- [ ] Elegir tipo de gestion, fechas y motivo
- [ ] Enviar la solicitud al administrador del area con estado "pendiente de aprobacion"
- [ ] Mensaje "Gestion creada con exito"
- [ ] Guardar en bitacora

### FA01 - Validacion de Credenciales
- [ ] Validar credenciales incorrectas
- [ ] Mensaje "Credenciales incorrectas"
- [ ] Retorna al login

### FA02 - Opcion Cambios de turno
- [ ] Formulario con Fecha Inicial, Turno Inicial, Fecha Nueva, Turno Nuevo, Justificacion, Boton Guardar, Boton Limpiar, Boton Regresar
- [ ] Ingresar fecha inicial, turno inicial, fecha nueva, turno nuevo, justificacion
- [ ] Enviar la solicitud al administrador del area con estado "pendiente aprobar turno"
- [ ] Mensaje "cambio de turno solicitado con exito"
- [ ] Registrar la solicitud en base de datos
- [ ] Guardar en bitacora

### FA04 - Boton Regresar
- [ ] Regresa a la pantalla principal

### FA05 - Validacion de fechas
- [ ] Validar que la fecha de inicio no sea menor a la fecha actual
- [ ] Validar que la fecha fin no sea menor a la fecha de inicio
- [ ] No permite continuar si la validacion falla

### Postcondiciones
- [ ] La solicitud queda registrada con estado pendiente de aprobacion
- [ ] Se almacenan las fechas y el motivo de la solicitud
- [ ] Bitacora de las acciones del empleado

### Reglas de Negocio
- [ ] RN01: la solicitud de licencias y vacaciones solo la puede enviar el empleado si cuenta con dias de vacaciones disponibles
- [ ] RN02: fecha de inicio no menor a la fecha actual; fecha fin no menor a la fecha de inicio
