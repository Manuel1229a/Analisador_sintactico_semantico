import java.util.*;

public class RutinaSemantica {
    // Método estático para detectar si una palabra está en plural
    public static boolean esPlural(String palabra) {
        return palabra.endsWith("s");
    }

    // Nuevo método que recibe el sujeto limpio
    public static void procesarArbol(Nodo raiz, String sujetoLimpio) {
        Nodo nodoVerbo = raiz.getHijos().get(1);
        Nodo nodoComplemento = raiz.getHijos().get(2);
        
        String verbo = nodoVerbo.getValor().split(":")[1].trim();
        String complemento = nodoComplemento.getValor().split(":")[1].trim();
        
        // Detectar pluralidad en verbo
        boolean verboPlural = esPlural(verbo);
        
        // Asumimos que el sujeto ya viene en singular (por la función limpiarSujeto)
        boolean sujetoPlural = false; // Ya no se considera, pues forzamos a singular para la comparación
        
        // Validar relación sujeto-verbo usando el sujeto limpio
        Map<String, List<String>> sujetoVerbo = new HashMap<>();
        sujetoVerbo.put("gato", List.of("come", "corre", "duerme"));
        sujetoVerbo.put("perro", List.of("come", "corre", "duerme"));
        sujetoVerbo.put("juan", List.of("lee", "corre", "estudia"));
        sujetoVerbo.put("maria", List.of("lee", "corre", "estudia"));
        
        if (sujetoVerbo.containsKey(sujetoLimpio) && sujetoVerbo.get(sujetoLimpio).contains(verbo)) {
            System.out.println("✔ El sujeto y el verbo tienen sentido.");
        } else {
            System.out.println("❌ Error semántico: El sujeto no puede realizar la acción del verbo.");
        }
        
        // Validar relación verbo-complemento
        Map<String, List<String>> verboComplemento = new HashMap<>();
        verboComplemento.put("come", List.of("pescado", "comida"));
        verboComplemento.put("lee", List.of("libro", "revista"));
        verboComplemento.put("corre", List.of("en el parque", "en la calle"));
        verboComplemento.put("duerme", List.of("en la cama", "en el sofá"));
        
        if (verboComplemento.containsKey(verbo) && verboComplemento.get(verbo).contains(complemento)) {
            System.out.println("✔ El complemento tiene sentido con el verbo.");
        } else {
            System.out.println("❌ Error semántico: El complemento no tiene sentido con el verbo.");
        }
    }

    public static void main(String[] args) {
        // Prueba del código

        // Crear un nodo raíz de ejemplo con una oración "El gato come pescado"
        Nodo raiz = new Nodo("Oración");

        // Crear nodos para el sujeto (con determinante)
        Nodo nodoSujeto = new Nodo("Sujeto");
        nodoSujeto.agregarHijo(new Nodo("Determinante: el"));
        nodoSujeto.agregarHijo(new Nodo("Sujeto: gato"));

        // Crear nodo para el verbo
        Nodo nodoVerbo = new Nodo("Verbo: come");

        // Crear nodo para el complemento
        Nodo nodoComplemento = new Nodo("Complemento: pescado");

        // Agregar nodos al árbol principal
        raiz.agregarHijo(nodoSujeto);
        raiz.agregarHijo(nodoVerbo);
        raiz.agregarHijo(nodoComplemento);
        
        // Llamar a la función de procesamiento semántico
        // Extraer el sujeto limpio (sin determinante)
        String sujetoLimpio = "gato"; // Se obtiene después de procesar la oración
        procesarArbol(raiz, sujetoLimpio);
    }
}
