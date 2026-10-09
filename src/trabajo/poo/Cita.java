package trabajo.poo;

public class Cita {

    private int id;
    private String dniPaciente;
    private String codigoMedico;
    private String fecha;
    private String hora;
    private String motivo;

    public Cita(int id, String dniPaciente, String codigoMedico,
                String fecha, String hora, String motivo) {
        this.id = id;
        this.dniPaciente = dniPaciente;
        this.codigoMedico = codigoMedico;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
    }

    public int getId() {
        return id;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public String getCodigoMedico() {
        return codigoMedico;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}