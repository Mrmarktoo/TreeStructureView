package com.marktoo.demo.dashtreedemo.tree;

import java.util.ArrayList;

/**
 * @author Administrator
 */
public interface TreeNode<D> {

    TreeNode<D> getParent();

    /**
     * 获取当前的数据项
     */
    D getData();

    /**
     * 添加子节点
     */
    void addChild(TreeNode node);

    /**
     * 添加父节点
     */
    void addParent(TreeNode node);

    /**
     * 获取子节点数据
     */
    ArrayList<TreeNode<D>> getChildren();

    /**
     * 是否存在子节点
     */
    boolean hasChild();

    /**
     * 是否存在父节点
     */
    boolean hasParent();

    /**
     * 下级列表项是否已显示
     */
    boolean isOpen();

    /**
     * 获取下级可显示的列表项
     */
    ArrayList<TreeNode<D>> open();

    /**
     * 获取要关闭显示的下级列表项
     */
    ArrayList<TreeNode<D>> close();
}
