package com.marktoo.demo.dashtreedemo.tree;

/**
 * @author Administrator
 */
public class LeafNode extends BaseNode<String> {
    public LeafNode(TreeNode parent, String data) {
        super(parent, data);
    }

    public LeafNode(String data) {
        super(data);
    }

    @Override
    public String toString() {
        return "LeafNode{" +
                "data=" + data +
                '}';
    }
}
