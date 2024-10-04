/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import com.mycompany.primera_actividad_arq.Alumno;
import com.mycompany.primera_actividad_arq.Metodos;
import java.text.SimpleDateFormat; // Asegúrate de importar esta clase
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import javax.swing.JOptionPane;
import java.util.Calendar;
/**
 *
 * @author ADMIN
 */
public class Registro extends javax.swing.JFrame {

     // Crear una instancia de la clase Metodos para manejar el ArrayList
    Metodos metodos = new Metodos();

    /**
     * Creates new form Registro
     */
    // Variables para control de modificación
    private boolean esModificacion = false;
    private int filaSeleccionada = -1; // Para almacenar el índice de la fila seleccionada para modificar
    
     
    public Registro() {
        initComponents();
        configurarValidaciones(); 
        configurarAccionesBotones();
        actualizarTabla(); // Inicializa la tabla con los datos actuales
        configurarRestriccionesFecha(); // Establece restricciones de fechas en el JDateChooser
        agregarDatosPredeterminados();
        setLocationRelativeTo (null);
    }
    
    
    // Configurar los KeyListeners para validación de campos
    private void configurarValidaciones() {
        // Para el campo Código (7 caracteres, letras y números)
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if (txtCodigo.getText().length() >= 7) {
                    evt.consume(); // Limitar a 7 caracteres
                }
                char c = evt.getKeyChar();
                if (!Character.isLetterOrDigit(c)) {
                    evt.consume(); // Solo letras y números
                }
            }
        });

        // Para el campo DNI (8 dígitos)
        txtDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if (txtDni.getText().length() >= 8) {
                    evt.consume(); // Limitar a 8 dígitos
                }
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume(); // Solo dígitos
                }
            }
        });

        // Para el campo Teléfono (9 dígitos)
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if (txtTelefono.getText().length() >= 9) {
                    evt.consume(); // Limitar a 9 dígitos
                }
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume(); // Solo dígitos
                }
            }
        });
    }

    private void configurarAccionesBotones() {
    // Acción del botón Agregar
    btnAgregar.addActionListener(evt -> {
        limpiarCampos();  // Limpiar los campos del formulario
        esModificacion = false;  // Indicar que no se está en modo de modificación
    });

    // Acción del botón Guardar
    btnGuardar.addActionListener(evt -> guardarAlumno());

    // Acción del botón Eliminar
    btnEliminar.addActionListener(evt -> eliminarAlumno());

    btnModificar.addActionListener(evt -> {
    filaSeleccionada = VistaRegistro.getSelectedRow(); // Obtener la fila seleccionada
    if (filaSeleccionada >= 0) {
        esModificacion = true;  // Cambiar el estado a "modificación"
        cargarDatosParaModificar(filaSeleccionada); // Cargar los datos del alumno en el formulario
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione un alumno para modificar");
    }
});

    // Acción para la búsqueda en tiempo real
    txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            buscarAlumno(txtBuscar.getText());
        }
    });
}

    private void guardarAlumno() {
    String nombre = txtNombre.getText();
    String apellido = txtApellido.getText();
    String codigo = txtCodigo.getText();
    String dni = txtDni.getText();
    String telefono = txtTelefono.getText();
    Date fechaNac = dateFechaN.getDate(); // Captura la fecha completa desde JDateChooser

    // Verificar que todos los campos estén completos
    if (nombre.isEmpty() || apellido.isEmpty() || codigo.isEmpty() || dni.isEmpty() || telefono.isEmpty() || fechaNac == null) {
        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
        return;
    }

    // Verificación adicional del formato de fecha
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    System.out.println("Fecha capturada: " + sdf.format(fechaNac)); // Verificar en consola

    if (esModificacion) {
        // Si es modificación, actualiza el alumno seleccionado
        Alumno alumnoModificado = metodos.getListaAlumnos().get(filaSeleccionada);  // Obtener el objeto Alumno original
        alumnoModificado.setNombre(nombre);
        alumnoModificado.setApellido(apellido);
        alumnoModificado.setCodigo(codigo);
        alumnoModificado.setDni(dni);
        alumnoModificado.setTelefono(telefono);
        alumnoModificado.setFechaNacimiento(fechaNac); // Actualizar la fecha completa (día, mes y año)

        esModificacion = false;  // Restablecer el estado
        filaSeleccionada = -1;   // Restablecer la fila seleccionada
        JOptionPane.showMessageDialog(this, "Alumno modificado correctamente.");
    } else {
        // Si no es modificación, agregar un nuevo alumno
        Alumno nuevoAlumno = new Alumno(codigo, nombre, apellido, dni, telefono, fechaNac);
        metodos.agregar(nuevoAlumno);
        JOptionPane.showMessageDialog(this, "Alumno agregado correctamente.");
    }

    actualizarTabla();  // Actualizar la tabla para reflejar los cambios
    limpiarCampos();    // Limpiar los campos del formulario
}



    private void agregarDatosPredeterminados() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    try {
        // Agregar 5 alumnos con datos de ejemplo
        metodos.agregar(new Alumno("001", "Carlos", "Pérez", "12345678", "987654321", sdf.parse("01-01-2000")));
        metodos.agregar(new Alumno("002", "María", "Gómez", "87654321", "987123456", sdf.parse("02-02-1999")));
        metodos.agregar(new Alumno("003", "Juan", "Lopez", "12341234", "912345678", sdf.parse("03-03-1998")));
        metodos.agregar(new Alumno("004", "Ana", "Martínez", "43214321", "956789123", sdf.parse("04-04-1997")));
        metodos.agregar(new Alumno("005", "Luis", "Sánchez", "56785678", "987456123", sdf.parse("05-05-1996")));

        // Después de agregar los alumnos, actualizar la tabla
        actualizarTabla();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    // Método para eliminar un alumno de la lista y la tabla
    private void eliminarAlumno() {
        int filaSeleccionada = VistaRegistro.getSelectedRow();
        if (filaSeleccionada >= 0) {
            metodos.eliminar(filaSeleccionada);
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un alumno para eliminar");
        }
    }

    // Método para buscar alumno por primera letra
    private void buscarAlumno(String texto) {
        DefaultTableModel model = (DefaultTableModel) VistaRegistro.getModel();
        model.setRowCount(0);
        for (Alumno alumno : metodos.getListaAlumnos()) {
            if (alumno.getNombre().toLowerCase().startsWith(texto.toLowerCase()) ||
                alumno.getApellido().toLowerCase().startsWith(texto.toLowerCase()) ||
                alumno.getCodigo().toLowerCase().startsWith(texto.toLowerCase()) ||
                alumno.getDni().toLowerCase().startsWith(texto.toLowerCase())) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String fechaNacimiento = sdf.format(alumno.getFechaNacimiento());
                model.addRow(new Object[]{alumno.getNombre(), alumno.getApellido(), alumno.getCodigo(), alumno.getDni(), alumno.getTelefono(), fechaNacimiento});
            }
        }
    }

    private void cargarDatosParaModificar(int fila) {
    Alumno alumno = metodos.getListaAlumnos().get(fila);  // Obtener el alumno de la lista
    txtNombre.setText(alumno.getNombre());
    txtApellido.setText(alumno.getApellido());
    txtCodigo.setText(alumno.getCodigo());
    txtDni.setText(alumno.getDni());
    txtTelefono.setText(alumno.getTelefono());
    dateFechaN.setDate(alumno.getFechaNacimiento());  // Establecer la fecha en el JDateChooser
}

    
   private void actualizarTabla() {
    DefaultTableModel model = (DefaultTableModel) VistaRegistro.getModel();
    model.setRowCount(0);  // Limpiar la tabla antes de llenarla nuevamente

    for (Alumno alumno : metodos.getListaAlumnos()) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String fechaNacimiento = sdf.format(alumno.getFechaNacimiento());
        model.addRow(new Object[]{alumno.getNombre(), alumno.getApellido(), alumno.getCodigo(), alumno.getDni(), alumno.getTelefono(), fechaNacimiento});
    }
}





    // Método para limpiar los campos de texto
    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtCodigo.setText("");
        txtDni.setText("");
        txtTelefono.setText("");
        dateFechaN.setDate(null); // Limpiar la fecha
    }

     // Método para establecer restricciones de fecha en el JDateChooser
        private void configurarRestriccionesFecha() {
        Calendar cal = Calendar.getInstance();
        Date fechaMaxima = cal.getTime();  // Fecha actual (no permitir fechas futuras)

        cal.add(Calendar.YEAR, -17);  // Restar 17 años para obtener la fecha mínima permitida
        Date fechaMinima = cal.getTime();

        dateFechaN.setMinSelectableDate(fechaMinima);  // Establece la fecha mínima (17 años atrás)
        dateFechaN.setMaxSelectableDate(fechaMaxima);  // Establece la fecha máxima (hoy)
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        VistaRegistro = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        dateFechaN = new com.toedter.calendar.JDateChooser();
        btnAgregar = new javax.swing.JButton();
        txtCodigo = new javax.swing.JTextField();
        txtDni = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        btnModificar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtNombre.setMinimumSize(null);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 153));
        jLabel8.setText("BUSCAR ALUMNO");

        VistaRegistro.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        VistaRegistro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Apellido", "Codigo", "Dni", "Telefono", "Fecha de nacimiento"
            }
        ));
        jScrollPane1.setViewportView(VistaRegistro);

        txtBuscar.setMinimumSize(null);

        dateFechaN.setMinimumSize(null);

        btnAgregar.setFont(new java.awt.Font("Adobe Myungjo Std M", 1, 18)); // NOI18N
        btnAgregar.setText("AGREGAR");

        txtCodigo.setMinimumSize(null);

        txtDni.setMinimumSize(null);

        txtTelefono.setMinimumSize(null);

        btnGuardar.setFont(new java.awt.Font("Adobe Myungjo Std M", 1, 18)); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 153));
        jLabel2.setText("CODIGO");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 153));
        jLabel3.setText("NOMBRE");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 153));
        jLabel4.setText("APELLIDO");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 153));
        jLabel5.setText("DNI");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 153));
        jLabel6.setText("TELEFONO");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 153));
        jLabel7.setText("FECHA DE NACIMIENTO");

        txtApellido.setMinimumSize(null);

        btnModificar.setFont(new java.awt.Font("Adobe Myungjo Std M", 1, 18)); // NOI18N
        btnModificar.setText("MODIFICAR");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 255));
        jLabel1.setText("REGISTRO ");

        btnEliminar.setFont(new java.awt.Font("Adobe Myungjo Std M", 1, 18)); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(385, 385, 385)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dateFechaN, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 774, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(btnAgregar)
                        .addGap(48, 48, 48)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnModificar)
                        .addGap(38, 38, 38)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel6)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(dateFechaN, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable VistaRegistro;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private com.toedter.calendar.JDateChooser dateFechaN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
