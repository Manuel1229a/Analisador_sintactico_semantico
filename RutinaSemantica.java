import java.util.*;

public class RutinaSemantica {
    public static void procesarArbol(Nodo raiz, String sujetoLimpio, String verbo, String complemento) {
        Map<String, List<String>> sujetoVerbo = new HashMap<>();
        sujetoVerbo.put("gato", List.of("come", "corre", "duerme"));
        sujetoVerbo.put("perro", List.of("come", "corre", "duerme"));
        sujetoVerbo.put("gatos", List.of("comen", "corren", "duermen"));
        sujetoVerbo.put("perros", List.of("comen", "corren", "duermen"));
        sujetoVerbo.put("juan", List.of("lee", "corre", "estudia"));
        sujetoVerbo.put("maria", List.of("lee", "corre", "estudia"));

        if (sujetoVerbo.containsKey(sujetoLimpio) && sujetoVerbo.get(sujetoLimpio).contains(verbo)) {
            System.out.println("El sujeto y el verbo tienen sentido.");
        } else {
            System.out.println("Error semántico: El sujeto no puede realizar la acción del verbo.");
        }

        Map<String, List<String>> verboComplemento = new HashMap<>();
        verboComplemento.put("come", List.of("pescado", "comida"));
        verboComplemento.put("corre", List.of("en el parque", "en la calle"));
        verboComplemento.put("lee", List.of("libro", "revista"));
        verboComplemento.put("duerme", List.of("en la cama", "en el sofá"));

        if (verboComplemento.containsKey(verbo) && verboComplemento.get(verbo).contains(complemento)) {
            System.out.println("El complemento tiene sentido con el verbo.");
        } else {
            System.out.println("Error semántico: El complemento no tiene sentido con el verbo.");
        }
    }
}
