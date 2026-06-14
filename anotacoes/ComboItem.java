package anotacoes;
public class ComboItem {

    private Object id;
    private String descricao;

    public ComboItem(Object id, String descricao) {
        this.id = id;
        this.descricao = descricao;
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
