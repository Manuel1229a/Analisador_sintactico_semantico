import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public List<Nodo> getHijos() {
        return hijos;
    }

    public String getValor() {
        return valor;
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
        // Listas de palabras
        List<String> sujetos = List.of("gato", "perro", "juan", "maria");
        List<String> verbos = List.of("come", "corre", "lee", "duerme");
        List<String> complementos = List.of("pescado", "libro", "parque", "pelota");
        List<String> preposiciones = List.of("en", "con", "a", "sobre", "por");
        List<String> determinantes = List.of("el", "la", "un", "una", "su", "mi", "tu");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa una oración (Ejemplo: 'El gato come pescado'): ");
        String oracion = scanner.nextLine().toLowerCase();

        String[] palabras = oracion.split("\\s+");

        String sujeto = "", verbo = "", complemento = "";
        boolean preposicionEncontrada = false;

        for (int i = 0; i < palabras.length; i++) {
            String palabra = palabras[i];

            // Verificar si la palabra es un determinante seguido de un sujeto
            if (i < palabras.length - 1 && determinantes.contains(palabra) && sujetos.contains(palabras[i + 1])) {
                sujeto = palabra + " " + palabras[i + 1]; // "El gato"
                i++; // Saltar la siguiente palabra ya que es parte del sujeto
            }
            // Si la palabra es un sujeto sin determinante (ej. "Juan", "María"), se acepta directamente
            else if (sujetos.contains(palabra) && sujeto.isEmpty()) {
                sujeto = palabra;
            }
            // Verificar si la palabra es un verbo
            else if (verbos.contains(palabra) && sujeto.isEmpty() == false) {
                verbo = palabra;
            }
            // Verificar si es una preposición para construir el complemento
            else if (preposiciones.contains(palabra) && verbo.isEmpty() == false) {
                preposicionEncontrada = true;
                complemento = palabra; // "en"
            }
            // Si ya encontró una preposición, el complemento puede incluir un determinante y sustantivo
            else if (preposicionEncontrada) {
                complemento += " " + palabra; // "en el parque"
            }
            // Si la palabra es un complemento sin preposición
            else if (complementos.contains(palabra) && complemento.isEmpty() && verbo.isEmpty() == false) {
                complemento = palabra; // "pescado"
            }
        }

        // Validar estructura: Sujeto → Verbo → Complemento
        if (!sujeto.isEmpty() && !verbo.isEmpty() && !complemento.isEmpty()) {
            Nodo raiz = new Nodo("Oracion");
            raiz.agregarHijo(new Nodo("Sujeto: " + sujeto));
            raiz.agregarHijo(new Nodo("Verbo: " + verbo));
            raiz.agregarHijo(new Nodo("Complemento: " + complemento));

            // Imprimir el árbol sintáctico
            raiz.imprimirArbol("");

            RutinaSemantica.procesarArbol(raiz);

        } else {
            System.out.println("La oración no es válida. Asegúrate de incluir un sujeto, un verbo y un complemento.");
        }

        scanner.close();
    }
}
