import java.util.ArrayList;
import java.util.List;

class Nodo {
    private String valor;
    private List<Nodo> hijos;

    public Nodo(String valor) {
        this.valor = valor;
        this.hijos = new ArrayList<>();
    }

    public void agregarHijo(Nodo hijo) {
        hijos.add(hijo);
    }

    public void imprimirArbol(String prefijo) {
        System.out.println(prefijo + valor);
        for (Nodo hijo : hijos) {
            hijo.imprimirArbol(prefijo + "  ");
        }
    }
}

public class ArbolSintactico {
    public static void main(String[] args) {
        Nodo raiz = new Nodo("Oracion");
        Nodo sujeto = new Nodo("Sujeto");
        Nodo predicado = new Nodo("Predicado");

        Nodo articulo = new Nodo("Articulo: El");
        Nodo sustantivo = new Nodo("Sustantivo: gato");
        Nodo verbo = new Nodo("Verbo: come");
        Nodo objeto = new Nodo("Objeto");
        Nodo sustantivo2 = new Nodo("Sustantivo: pescado");

        sujeto.agregarHijo(articulo);
        sujeto.agregarHijo(sustantivo);
        predicado.agregarHijo(verbo);
        objeto.agregarHijo(sustantivo2);
        predicado.agregarHijo(objeto);

        raiz.agregarHijo(sujeto);
        raiz.agregarHijo(predicado);
        raiz.imprimirArbol("");
    }
}