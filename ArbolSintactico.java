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
        // Listas de palabras (en singular)
        List<String> sujetos = List.of("gato", "perro", "juan", "maria");
        List<String> verbos = List.of("come", "corre", "lee", "duerme");
        List<String> complementos = List.of("pescado", "libro", "parque", "pelota");
        List<String> preposiciones = List.of("en", "con", "a", "sobre", "por");
        // Incluir determinantes, tanto en singular como plural
        List<String> determinantes = List.of("el", "la", "un", "una", "su", "mi", "tu", "los", "las");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa una oración (Ejemplo: 'El gato come pescado'): ");
        String oracion = scanner.nextLine().toLowerCase();
        String[] palabras = oracion.split("\\s+");

        String sujeto = "", verbo = "", complemento = "";
        boolean preposicionEncontrada = false;

        // Procesar las palabras para extraer sujeto, verbo y complemento
        for (int i = 0; i < palabras.length; i++) {
            String palabra = palabras[i];
            
            // Si la palabra es un determinante seguido de un sujeto
            if (i < palabras.length - 1 && determinantes.contains(palabra)) {
                String posibleSujeto = palabras[i + 1];
                // Si el determinante es plural ("los", "las"), 
                // convertimos el posible sujeto a singular (quitando la 's') para la comparación.
                if ((palabra.equals("los") || palabra.equals("las")) && posibleSujeto.endsWith("s")) {
                    String singular = posibleSujeto.substring(0, posibleSujeto.length() - 1);
                    if (sujetos.contains(singular)) {
                        sujeto = palabra + " " + singular; // Ejemplo: "los gato"
                        i++; // Saltar el siguiente porque ya se usó
                        continue;
                    }
                }
                // Si el posible sujeto ya está en la lista (en singular)
                if (sujetos.contains(posibleSujeto)) {
                    sujeto = palabra + " " + posibleSujeto; // Ejemplo: "el gato"
                    i++; // Saltar el siguiente
                    continue;
                }
            }
            // Si la palabra es un sujeto sin determinante
            else if (sujetos.contains(palabra) && sujeto.isEmpty()) {
                sujeto = palabra;
                continue;
            }
            // Si la palabra es un verbo (y ya se tiene sujeto)
            else if (verbos.contains(palabra) && !sujeto.isEmpty() && verbo.isEmpty()) {
                verbo = palabra;
                continue;
            }
            // Si la palabra es una preposición para comenzar el complemento (después del verbo)
            else if (preposiciones.contains(palabra) && !verbo.isEmpty()) {
                preposicionEncontrada = true;
                complemento = palabra;
                continue;
            }
            // Si ya se encontró una preposición, se construye el complemento
            else if (preposicionEncontrada) {
                complemento += " " + palabra;
                continue;
            }
            // Si la palabra es un complemento sin preposición y aún no se ha asignado
            else if (complementos.contains(palabra) && complemento.isEmpty() && !verbo.isEmpty()) {
                complemento = palabra;
                continue;
            }
        }

        // Validar que se hayan encontrado las tres partes
        if (!sujeto.isEmpty() && !verbo.isEmpty() && !complemento.isEmpty()) {
            Nodo raiz = new Nodo("Oracion");
            Nodo nodoSujeto = new Nodo("Sujeto");
            nodoSujeto.agregarHijo(new Nodo("Determinante: " + sujeto.split(" ")[0]));
            nodoSujeto.agregarHijo(new Nodo("Sujeto: " + sujeto.split(" ")[1]));
            
            Nodo nodoVerbo = new Nodo("Verbo: " + verbo);
            Nodo nodoComplemento = new Nodo("Complemento: " + complemento);
            
            raiz.agregarHijo(nodoSujeto);
            raiz.agregarHijo(nodoVerbo);
            raiz.agregarHijo(nodoComplemento);
            
            raiz.imprimirArbol("");

            // Llamamos al procesamiento semántico con el sujeto limpio
            RutinaSemantica.procesarArbol(raiz, sujeto.split(" ")[1]);  // Sujeto limpio es el segundo elemento
        } else {
            System.out.println("La oración no es válida. Asegúrate de incluir un sujeto, un verbo y un complemento.");
        }

        scanner.close();
    }
}
