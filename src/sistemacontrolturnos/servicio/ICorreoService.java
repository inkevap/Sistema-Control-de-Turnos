package sistemacontrolturnos.servicio;

public interface ICorreoService {

    void enviarCorreo(String destinatario, String asunto, String cuerpo);
}
