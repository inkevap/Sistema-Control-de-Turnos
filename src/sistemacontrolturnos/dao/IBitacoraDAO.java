package sistemacontrolturnos.dao;

import java.util.List;
import sistemacontrolturnos.entidad.Bitacora;

public interface IBitacoraDAO {

    void guardar(Bitacora bitacora);

    List<Bitacora> listarTodos();
}
