import java.util.*;

class RutinaSemantica {

    public static void procesarArbol(Nodo raiz) {
        Nodo nodoSujeto = raiz.getHijos().get(0);
        Nodo nodoVerbo = raiz.getHijos().get(1);
        Nodo nodoComplemento = raiz.getHijos().get(2);

        String sujeto = nodoSujeto.getValor().split(":")[1].trim();
        String verbo = nodoVerbo.getValor().split(":")[1].trim();
        String complemento = nodoComplemento.getValor().split(":")[1].trim();

        // Mapa de sujetos y los verbos que pueden realizar
        Map<String, List<String>> sujetoVerbo = new HashMap<>();
        sujetoVerbo.put("gato", List.of("come", "corre", "duerme"));
        sujetoVerbo.put("perro", List.of("come", "corre", "duerme"));
        sujetoVerbo.put("juan", List.of("lee", "corre", "estudia"));
        sujetoVerbo.put("maria", List.of("lee", "corre", "estudia"));

        // Mapa de verbos y los complementos compatibles
        Map<String, List<String>> verboComplemento = new HashMap<>();
        verboComplemento.put("come", List.of("pescado", "comida"));
        verboComplemento.put("lee", List.of("libro", "revista"));
        verboComplemento.put("corre", List.of("en el parque", "en la calle"));
        verboComplemento.put("duerme", List.of("en la cama", "en el sofá"));

        // Limpiar el sujeto para eliminar determinantes y gestionar plurales
        String sujetoLimpiado = limpiarSujeto(sujeto);

        // Verificar si el sujeto es plural (los gatos -> gato) y si el verbo está en plural
        boolean esPlural = sujeto.startsWith("los") || sujeto.startsWith("las");
        if (esPlural && !verbo.endsWith("n")) { // El verbo debería terminar en "n" para ser plural
            System.out.println("❌ Error semántico: El sujeto plural debe coincidir con un verbo plural.");
        }

        // Validar relación sujeto-verbo
        if (sujetoVerbo.containsKey(sujetoLimpiado) && sujetoVerbo.get(sujetoLimpiado).contains(verbo)) {
            System.out.println("✔ El sujeto y el verbo tienen sentido.");
        } else {
            System.out.println("❌ Error semántico: El sujeto no puede realizar la acción del verbo.");
        }

        // Validar relación verbo-complemento
        if (verboComplemento.containsKey(verbo) && verboComplemento.get(verbo).contains(complemento)) {
            System.out.println("✔ El complemento tiene sentido con el verbo.");
        } else {
            System.out.println("❌ Error semántico: El complemento no tiene sentido con el verbo.");
        }
    }

    // Función para limpiar el sujeto y eliminar determinantes y gestionar plurales
    public static String limpiarSujeto(String sujeto) {
        List<String> determinantes = List.of("el", "la", "un", "una", "los", "las");
        for (String determinante : determinantes) {
            if (sujeto.startsWith(determinante)) {
                // Si el sujeto es plural, eliminar "los" o "las" y devolver el singular (ej. "gatos" -> "gato")
                if (sujeto.startsWith("los") || sujeto.startsWith("las")) {
                    return sujeto.substring(determinante.length()).trim() + "s"; // "gatos"
                }
                return sujeto.substring(determinante.length()).trim(); // "gato"
            }
        }
        return sujeto; // Si no tiene determinante, devolver el sujeto original
    }
}
