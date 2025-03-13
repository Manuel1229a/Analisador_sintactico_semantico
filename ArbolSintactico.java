import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

class NodoSemantico {
    private String valor;
    private List<NodoSemantico> hijos;

    public NodoSemantico(String valor) {
        this.valor = valor;
        this.hijos = new ArrayList<>();
    }

    public void agregarHijo(NodoSemantico hijo) {
        hijos.add(hijo);
    }

    public void imprimirArbolSemantico(String prefijo) {
        System.out.println(prefijo + valor);
        for (NodoSemantico hijo : hijos) {
            hijo.imprimirArbolSemantico(prefijo + "  ");
        }
    }
}

public class ArbolSintactico {

    
    public static boolean verificarConcordancia(String sujeto, String verbo) {
        if (sujeto.endsWith("s")) {
            return verbo.endsWith("n");  // Plural
        } else {
            return !verbo.endsWith("n");  // Singular
        }
    }

    public static String limpiarSujeto(String sujeto) {
        String[] partes = sujeto.split("\\s+");
        return partes.length > 1 ? partes[1] : sujeto;
    }

    public static String normalizarVerbo(String verbo) {
        Map<String, String> verbosSingulares = Map.of(
            "comen", "come",
            "corren", "corre",
            "leen", "lee",
            "duermen", "duerme"
        );
        return verbosSingulares.getOrDefault(verbo, verbo);
    }

    public static void visualizarArbolSemantico(String sujeto, String verbo, String complemento) {
        List<String> personas = List.of("juan", "maria");
        NodoSemantico raiz;

        if (personas.contains(sujeto.toLowerCase())) {
            raiz = new NodoSemantico("Persona");
        } else {
            raiz = new NodoSemantico("Animal");
            NodoSemantico mamifero = new NodoSemantico("Mamífero");
            NodoSemantico felino = new NodoSemantico("Felino");
            raiz.agregarHijo(mamifero);
            mamifero.agregarHijo(felino);
        }

        NodoSemantico nodoSujeto = new NodoSemantico(sujeto.substring(0, 1).toUpperCase() + sujeto.substring(1));
        raiz.agregarHijo(nodoSujeto);

        System.out.println("\n--- Arbol Semántico ---");
        raiz.imprimirArbolSemantico("");
    }

    public static void main(String[] args) {
        List<String> sujetos = List.of("gato", "perro", "juan", "maria", "gatos", "perros");
        List<String> verbos = List.of("come", "corre", "lee", "duerme", "comen", "corren", "leen", "duermen");
        List<String> complementos = List.of("pescado", "libro", "parque", "pelota", "comida", "revista");
        List<String> preposiciones = List.of("en", "con", "a", "sobre", "por");
        List<String> determinantes = List.of("el", "la", "un", "una", "su", "mi", "tu", "los", "las");
    
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa una oración, por ejemplo: \n 'El gato come pescado'");
        System.out.println("Palabras permitidas para formar una oración: ");
        System.out.println("Sujeto: " + sujetos);
        System.out.println("Verbos: " + verbos);
        System.out.println("Complementos: " + complementos);
        System.out.println("Preposiciones: " + preposiciones);
        System.out.println("Determinantes: " + determinantes);
        String oracion = scanner.nextLine().toLowerCase().trim();
    
        
        boolean esInterrogativa = oracion.endsWith("?");
    
        
        oracion = oracion.replace("¿", "").replace("?", "");
    
        String[] palabras = oracion.split("\\s+");
    
        String sujeto = "", verbo = "", complemento = "";
        boolean preposicionEncontrada = false;
    
        for (int i = 0; i < palabras.length; i++) {
            String palabra = palabras[i];
    
            if (i < palabras.length - 1 && determinantes.contains(palabra)) {
                String posibleSujeto = palabras[i + 1];
                if (sujetos.contains(posibleSujeto)) {
                    sujeto = palabra + " " + posibleSujeto;
                    i++;
                    continue;
                }
            } else if (sujetos.contains(palabra) && sujeto.isEmpty()) {
                sujeto = palabra;
                continue;
            } else if (verbos.contains(palabra) && !sujeto.isEmpty() && verbo.isEmpty()) {
                verbo = palabra;
                continue;
            } else if (preposiciones.contains(palabra) && !verbo.isEmpty()) {
                preposicionEncontrada = true;
                complemento = palabra;
                continue;
            } else if (preposicionEncontrada && determinantes.contains(palabra)) {
                complemento += " " + palabra;
                continue;
            } else if (preposicionEncontrada) {
                complemento += " " + palabra;
                continue;
            } else if (complementos.contains(palabra) && complemento.isEmpty() && !verbo.isEmpty()) {
                complemento = palabra;
                continue;
            }
        }
    
        if (!sujeto.isEmpty() && !verbo.isEmpty() && !complemento.isEmpty()) {
            Nodo raiz = new Nodo("Oracion");
            Nodo nodoSujeto = new Nodo("Sujeto");
            String[] partesSujeto = sujeto.split("\\s+");
            if (partesSujeto.length > 1) {
                nodoSujeto.agregarHijo(new Nodo("Determinante: " + partesSujeto[0]));
                nodoSujeto.agregarHijo(new Nodo("Sujeto: " + partesSujeto[1]));
            } else {
                nodoSujeto.agregarHijo(new Nodo("Sujeto: " + sujeto));
            }
            Nodo nodoVerbo = new Nodo("Verbo: " + verbo);
            Nodo nodoComplemento = new Nodo("Complemento: " + complemento);
    
            raiz.agregarHijo(nodoSujeto);
            raiz.agregarHijo(nodoVerbo);
            raiz.agregarHijo(nodoComplemento);
    
            System.out.println("\n--- Arbol Sintáctico ---");
            raiz.imprimirArbol("");
    
            String sujetoLimpio = limpiarSujeto(sujeto);
            String verboNormalizado = normalizarVerbo(verbo);
    
            RutinaSemantica.procesarArbol(raiz, sujetoLimpio, verboNormalizado, complemento);
    
            
            if (esInterrogativa) {
                System.out.println("\n--- Oración Interrogativa Detectada ---");
            }
    
            visualizarArbolSemantico(sujetoLimpio, verboNormalizado, complemento);
    
        } else {
            System.out.println("La oración no es válida.");
        }
        scanner.close();
    }
}    