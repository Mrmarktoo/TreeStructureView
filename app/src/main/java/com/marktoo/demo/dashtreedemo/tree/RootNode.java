package com.marktoo.demo.dashtreedemo.tree;

/**
 * @author Administrator
 */
public class RootNode extends BaseNode<String> {
    public RootNode(String data) {
        super(data);
    }

    @Override
    public String toString() {
        return "RootNode{" +
                "data=" + data +
                ", children=" + children.size() +
                '}';
    }
}
