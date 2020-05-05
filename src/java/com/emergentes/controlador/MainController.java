package com.emergentes.controlador;

import com.emergentes.modelo.Contacto;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mario Torrez
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Variables para acceder a la base de datos
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bd_agenda";
        String usuario = "root";
        String password = "";

        Connection conn = null;
        String sql = "";
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Contacto> lista = new ArrayList<Contacto>();

        try {
            // Especificación del driver
            Class.forName(driver);
            // Establece la conexión a la base de datos
            conn = DriverManager.getConnection(url, usuario, password);
            // Especificamos la consulta a la base de datos
            sql = "select * from contactos";

            // Preparamos la consulta
            ps = conn.prepareStatement(sql);

            // Ejecutar la consulta
            rs = ps.executeQuery();
            //
            while (rs.next()) {
                Contacto c = new Contacto();
                c.setId(rs.getInt("id"));
                c.setCorreo(rs.getString("correo"));
                c.setTelefono(rs.getString("telefono"));
                c.setNombre(rs.getString("nombre"));
                lista.add(c);
            }
            // colocar la lista como atributo de request
            request.setAttribute("lista", lista);
            // Transfiere el control a index.jsp
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (ClassNotFoundException e) {
            System.out.println("Error en driver " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al conectar " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Process POST
    }

}
