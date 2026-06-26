package modelos;

public class CheckBoxNode {

    private int id;
    private String descricao;
    private boolean selecionado;

    public CheckBoxNode(int id,String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(
            boolean selecionado) {

        this.selecionado = selecionado;
    }

    @Override
    public String toString() {
        return descricao;
    }
}