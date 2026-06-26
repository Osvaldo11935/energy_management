package anotacoes;
public class ComboItem {

    private Object id;
    private String descricao;
    private Object paiId;

    public ComboItem(Object id, String descricao, Object paiId) {
        this.id = id;
        this.paiId = paiId;
        this.descricao = descricao;
    }

    public Object getPaiId() {
        return paiId;
    }

    public Object getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
