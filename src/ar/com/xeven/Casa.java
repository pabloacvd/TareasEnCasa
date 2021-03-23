package ar.com.xeven;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Casa {

    private String dbName = "casa";
    private String dbUser = "root";
    private String dbPwd = "unafacil";

    public void buscarYMostrar(String sql){
        ConexionDB conexionDB = new ConexionDB(dbName,dbUser,dbPwd);
        ResultSet resultados = conexionDB.consultar(sql);
        try{
            mostrarResultados(resultados);
        }catch(SQLException e){
            System.out.println("No se encontraron resultados.");
        }finally {
            conexionDB.cerrar();
        }
    }

    private void mostrarResultados(ResultSet resultados) throws SQLException {
        if(resultados != null)
            while(resultados.next())
                System.out.println("Nombre: "+resultados.getString("nombre"));
    }

    public void mostrarTareaPorID(int id){
        buscarYMostrar("SELECT * FROM tareas WHERE id = "+id);
    }
    public void listarTareas() {
        buscarYMostrar("SELECT * FROM tareas;");
    }

    public boolean agregarTarea(Tarea tarea) {
        ConexionDB conexionDB = new ConexionDB(dbName,dbUser,dbPwd);
        String nombre = tarea.getNombre();
        String descripcion = tarea.getDescripcion();
        String responsable = tarea.getResponsable();
        String estado = tarea.getEstado().name();

        String sql = "INSERT INTO tareas (nombre, descripcion, responsable, estado) VALUES "+
                "('"+nombre+"','"+descripcion+"','"+responsable+"','"+estado+"');";
        boolean respuesta = false;
        try {
            respuesta = conexionDB.insertar(sql);
        }catch(SQLException e){
            System.out.println("No se pudo agregar la tarea.");
        }finally {
            conexionDB.cerrar();
        }
        return respuesta;
    }
}
