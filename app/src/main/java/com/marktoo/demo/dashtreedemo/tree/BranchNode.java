package com.marktoo.demo.dashtreedemo.tree;

import java.util.ArrayList;

/**
 * @author Administrator
 */
public class BranchNode extends BaseNode<String> {
    public BranchNode(TreeNode parent, String data, ArrayList<TreeNode<String>> children) {
        super(parent, data, children);
    }

    public BranchNode(String data) {
        super(data);
    }

    @Override
    public String toString() {
        return "BranchNode{" +
                "parent=" + parent +
                ", data=" + data +
                ", children=" + children.size() +
                '}';
    }
}
