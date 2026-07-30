package sistemacontrolturnos.dao;

import java.time.LocalDate;
import java.util.List;
import sistemacontrolturnos.entidad.Marcaje;

public interface IMarcajeDAO {

    void guardar(Marcaje marcaje);

    List<Marcaje> listarPorUsuarioYFecha(String nombreUsuario, LocalDate fecha);

    List<Marcaje> listarTodos();
}
