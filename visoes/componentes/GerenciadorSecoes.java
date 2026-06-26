package visoes.componentes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class GerenciadorSecoes {
    private final Map<String, Boolean> estados = new HashMap<>();
    private final Map<String, Consumer<Boolean>> listeners = new HashMap<>();
    
    public void registrarSecao(String id, boolean expandidoPorPadrao) {
        estados.put(id, expandidoPorPadrao);
    }
    
    public boolean isExpandido(String id) {
        return estados.getOrDefault(id, false);
    }
    
    public void setExpandido(String id, boolean expandido) {
        estados.put(id, expandido);
        if (listeners.containsKey(id)) {
            listeners.get(id).accept(expandido);
        }
    }
    
    public void toggle(String id) {
        setExpandido(id, !isExpandido(id));
    }
    
    public void adicionarListener(String id, Consumer<Boolean> listener) {
        listeners.put(id, listener);
    }
    
    public void salvarEstado(String arquivo) {
        // Implementar persistência se necessário
    }
    
    public void carregarEstado(String arquivo) {
        // Implementar carregamento se necessário
    }
}