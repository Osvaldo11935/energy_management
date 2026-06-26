package visoes.componentes;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import modelos.CheckBoxNode;

public class CheckBoxTreeRenderer extends JCheckBox
        implements TreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean selected,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

        DefaultMutableTreeNode node =
                (DefaultMutableTreeNode) value;

        Object obj = node.getUserObject();

        if (obj instanceof CheckBoxNode item) {
            setText(item.getDescricao());
            setSelected(item.isSelecionado());
        } else {
            setText(obj.toString());
            setSelected(false);
        }

        return this;
    }
}