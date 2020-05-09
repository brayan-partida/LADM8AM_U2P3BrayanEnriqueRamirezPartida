package mx.edu.ittepic.ladm8am_u2p3brayanenriqueramirezpartida

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var baseRomota = FirebaseFirestore.getInstance()
    var dataLista = ArrayList<String>()
    var listaId = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        chkPedido.setOnClickListener {

            txtCantidad.isEnabled = chkPedido.isChecked
            txtEntregado.isEnabled = chkPedido.isChecked
            txtPrecio.isEnabled = chkPedido.isChecked
            txtProducto.isEnabled = chkPedido.isChecked
        }//todo:btnconyugal


        btnInsertar.setOnClickListener {
            insertarRegistro()
        }

        btnMostrar.setOnClickListener {
            consultasGeneral()
        }
    }


    /*private fun construirDialog() {
        var dialogo = Dialog(this)
        dialogo.setContentView(R.layout.consulta)
        var valor = dialogo.findViewById<EditText>(R.id.txtBuscar)

        var posicion = dialogo.findViewById<Spinner>(R.id.spClave)
        var buscar = dialogo.findViewById<Button>(R.id.btnbuscar)
        var cerar = dialogo.findViewById<Button>(R.id.btncerrar)

        dialogo.show()

        cerar.setOnClickListener {
            dialogo.dismiss()
        }
        buscar.setOnClickListener {
            if (valor.text.isEmpty()) {
                Toast.makeText(this, "error buscar", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            realizarConsulta(valor.text.toString(), posicion.selectedItemPosition)
            dialogo.dismiss()
        }
    }

    private fun realizarConsulta(valor: String, clave: Int) {

        when (clave) {
            0 -> {
                consultaNombre(valor.toString())
            }
            1 -> {
                consultaSueldo(valor.toFloat())
            }
            2 -> {
                consultaEdad(valor.toInt())
            }
        }
    }
    */
    private fun consultaNombre(valor: String) {
        baseRomota.collection("trabajador")
            .whereEqualTo("nombre", valor)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {

                    txtResultado.setText("error no hay conexion")
                    return@addSnapshotListener
                }

                var res = ""
                for (documento in querySnapshot!!) {
                    res += "Id:" + documento.id + "\nNombre: " + documento.getString("nombre") +
                            "\nSueldo: " + documento.getDouble("sueldo") +
                            "\nFechaIngreso: " + documento.getDate("ingreso") +
                            "\nConyugue: " + documento.get("conyuge.nombre") +
                            "\nEdad: " + documento.get("conyuge.edad")
                }
                txtResultado.setText(res)
            }
    }


    private fun consultaSueldo(valor: Float) {

    }


    private fun consultaEdad(valor: Int) {

    }

    public fun consultasGeneral() {
        baseRomota.collection("restaurante").addSnapshotListener { querySnapshot,
                                                                   firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                //todo:error si es diferente
                Toast.makeText(this, " error no se puede acceder a la consulata", Toast.LENGTH_LONG)
                    .show()
                return@addSnapshotListener
            }
           // dataLista.clear()
            listaId.clear()

            for (documento in querySnapshot!!) {
                var cadena = "celular: " + documento.getString("celular") +
                "\ndomicilio" + documento.getString("domicilio") +
                        "\nnombre" + documento.getString("nombre") +
                        "\n:  pedido: \n  precio:" + documento.getString("pedido.precio") +
                        "\n cantidad: " + documento.getString("pedido.cantidad") +
                        "\n entregado: " + documento.getString("pedido.entregado") +
                        "\n producto :" + documento.getString("pedido.producto")


                dataLista.add(cadena)
                listaId.add(documento.id)
                txtResultado.setText(cadena)
            }//todo=for
            if (dataLista.size == 0) {
                dataLista.add("no hay data")

            }
           /* var adaptador =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataLista)
            lista.adapter = adaptador*/



        }

    }//todo funcion-============================================>

    private fun insertarRegistro() {
        var data = hashMapOf(
            "celular" to txtCelular.text.toString(),
            "domicilio" to txtDomicilio.text.toString(),
            "nombre" to txtNombre.text.toString(),
            "pedido" to hashMapOf(
                "cantidad" to txtCantidad.text.toString(),
                "entregado" to txtEntregado.text.toString(),
                "precio" to txtPrecio.text.toString(),
                "producto" to txtProducto.text.toString()
            )
        )


        baseRomota.collection("restaurante")
            .add(data as Map<String, Any>)
            .addOnSuccessListener {
                Toast.makeText(this, "se inserto", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this, "no se inserto", Toast.LENGTH_LONG).show()
            }
    }


}
