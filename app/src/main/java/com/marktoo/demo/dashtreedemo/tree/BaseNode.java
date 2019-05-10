package com.marktoo.demo.dashtreedemo.tree;

import java.util.ArrayList;

/**
 * @author Administrator
 */
public class BaseNode<D> implements TreeNode<D> {

    private boolean isOpened = false;
    protected TreeNode parent = null;
    protected D data = null;

    protected ArrayList<TreeNode<D>> children = new ArrayList<>();


    public BaseNode(TreeNode parent, D data, ArrayList<TreeNode<D>> children) {
        this.parent = parent;
        this.data = data;
        this.children = children;
    }

    public BaseNode(TreeNode parent, D data) {
        this.parent = parent;
        this.data = data;
    }

    public BaseNode(D data) {
        this.data = data;
    }

    public void setChildren(ArrayList<TreeNode<D>> children) {
        this.children = children;
    }

    @Override
    public TreeNode<D> getParent() {
        return parent;
    }

    @Override
    public D getData() {
        return data;
    }

    @Override
    public void addChild(TreeNode node) {
        if (children == null) {
            children = new ArrayList<>();
        }
        node.addParent(this);
        children.add(node);
    }

    @Override
    public void addParent(TreeNode node) {
        this.parent = node;
    }

    @Override
    public ArrayList<TreeNode<D>> getChildren() {
        return children;
    }

    @Override
    public boolean hasChild() {
        return children != null && children.size() > 0;
    }

    @Override
    public boolean hasParent() {
        return parent != null;
    }


    @Override
    public boolean isOpen() {
        return isOpened;
    }

    @Override
    public ArrayList<TreeNode<D>> open() {
        isOpened = true;
        return children;
    }

    @Override
    public ArrayList<TreeNode<D>> close() {
        isOpened = false;
        ArrayList<TreeNode<D>> subList = new ArrayList<>();
        for (TreeNode node : children) {
            subList.add(node);
            if (node.hasChild() && node.isOpen()) {
                subList.addAll(node.getChildren());
            }
        }
        return subList;
    }

    @Override
    public String toString() {
        return "BaseNode{" +
                "isOpened=" + isOpened +
                ", parent=" + parent +
                ", data=" + data +
                ", children=" + children.size() +
                '}';
    }
}
