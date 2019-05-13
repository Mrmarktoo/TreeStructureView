package com.marktoo.demo.dashtreedemo

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.marktoo.demo.dashtreedemo.tree.BranchNode
import com.marktoo.demo.dashtreedemo.tree.LeafNode
import com.marktoo.demo.dashtreedemo.tree.RootNode
import com.marktoo.demo.dashtreedemo.tree.TreeNode
import com.marktoo.widget.treebranchview.TreeBranchView

class MainActivity : AppCompatActivity() {

    lateinit var rv_tree_list: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tree)
        rv_tree_list = findViewById(R.id.rv_tree_list)
        val adapter = MyAdapter(mockData(), this, true)
        rv_tree_list.layoutManager = LinearLayoutManager(this)
        rv_tree_list.adapter = adapter
    }

    fun mockData(): ArrayList<TreeNode<String>> {


        val beijing = RootNode("北京")
        val chaoyang: TreeNode<String> = BranchNode("朝阳")
        chaoyang.addChild(LeafNode("十里河"))
        chaoyang.addChild(LeafNode("望京"))
        beijing.addChild(chaoyang)
        val haidian: TreeNode<String> = BranchNode("海淀")
        haidian.addChild(LeafNode("中关村"))
        haidian.addChild(LeafNode("保福寺"))
        beijing.addChild(haidian)

        val hebei: TreeNode<String> = RootNode("河北")
        val baoding: TreeNode<String> = BranchNode("保定")
        baoding.addChild(LeafNode("安新"))
        baoding.addChild(LeafNode("雄县"))
        hebei.addChild(baoding)

        val hengshui: TreeNode<String> = BranchNode("衡水")
        hengshui.addChild(LeafNode("深州"))
        hengshui.addChild(LeafNode("冀州"))
        hebei.addChild(hengshui)

        val nodeList: ArrayList<TreeNode<String>> = arrayListOf(beijing, hebei)
        showLog("mockList : size=" + nodeList.size + ",\n" + Gson().toJson(nodeList))
        return nodeList
    }

    class MyAdapter(
        private val source: ArrayList<TreeNode<String>>,
        private val mContext: Context,
        private val animateChange: Boolean = false
    ) :
        RecyclerView.Adapter<MyHolder>() {

        private var nodeList = ArrayList<TreeNode<String>>()

        init {
            nodeList.clear()
            nodeList.addAll(source)
        }

        private val onTreeClickListener = object : OnTreeClickListener<TreeNode<String>> {
            override fun onChildClick(node: TreeNode<String>) {
                Toast.makeText(mContext, node.data, Toast.LENGTH_SHORT).show()
            }

            override fun onNodeClick(node: TreeNode<String>) {
                val animateIndex = findIndex(node)

                if (node.isOpen) {
                    val removeList = node.close()
                    nodeList.removeAll(removeList)
                    if (animateChange && animateIndex >= 0) {
                        notifyItemRangeRemoved(animateIndex + 1, removeList.size)
                    }
                } else {
                    val addList: ArrayList<TreeNode<String>> = node.open()
                    nodeList.addAll(animateIndex + 1, addList)
                    if (animateChange && animateIndex >= 0) {
                        notifyItemRangeInserted(animateIndex + 1, addList.size)
                    }
                }
                if (!animateChange || animateIndex == -1) {
                    notifyDataSetChanged()
                }
            }
        }

        fun findIndex(node: TreeNode<String>): Int {
            for (i in 0 until nodeList.size) {
                if (node == nodeList[i]) {
                    return i
                }
            }
            return -1
        }

        override fun getItemViewType(position: Int): Int {
            val node = nodeList[position]
            return when {
                node.hasParent() && node.hasChild() -> {
                    BRANCH_LEVEL
                }
                node.hasParent() && !node.hasChild() -> {
                    LEAF_LEVEL
                }
                else -> {
                    ROOT_LEVEL
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            val itemView = LayoutInflater.from(mContext).inflate(getItemLayout(viewType), parent, false)
            return MyHolder(itemView, viewType)
        }

        private fun getItemLayout(viewType: Int): Int {
            return when (viewType) {
                BRANCH_LEVEL -> {
                    R.layout.item_area
                }
                LEAF_LEVEL -> {
                    R.layout.item_street
                }
                else -> {
                    R.layout.item_city
                }
            }
        }

        override fun getItemCount(): Int {
            return if (nodeList == null) 0 else nodeList.size
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            showLog("onBindViewHolder position = $position ,data = ${nodeList[position].data}")
            holder.tvTitle?.text = nodeList[position].data
            holder.initMode(nodeList[position])
            holder.itemView.setOnClickListener {
                val node = holder.data
                if (node.hasChild()) {
                    onTreeClickListener.onNodeClick(node)
                } else {
                    onTreeClickListener.onChildClick(node)
                }
                holder.update(node)
            }
        }

    }

    class MyHolder(itemView: View, private val viewType: Int) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView? = null
        var tree: TreeBranchView? = null
        var tree2: TreeBranchView? = null

        lateinit var data: TreeNode<String>

        init {
            itemView?.apply {
                tvTitle = this.findViewById(R.id.tv_title)
                tree = this.findViewById(R.id.tree)
                if (viewType == LEAF_LEVEL) {
                    tree2 = this.findViewById(R.id.tree2)
                }
            }
        }

        fun initMode(node: TreeNode<String>) {
            this.data = node
            update(node)
        }

        private fun isLastOne(node: TreeNode<String>): Boolean {
            if (node.parent == null) {
                return false
            }
            return node.parent.children.last() == node
        }

        fun update(node: TreeNode<String>) {
            when (viewType) {
                ROOT_LEVEL -> {
                    if (node.isOpen) {
                        tree?.changeMode(1)
                    } else {
                        tree?.changeMode(0)
                    }
                }
                BRANCH_LEVEL -> {
                    if (isLastOne(node)) {
                        tree?.changeMode(3)
                    } else {
                        tree?.changeMode(2)
                    }
                }

                LEAF_LEVEL -> {
                    if (isLastOne(node)) {
                        tree2?.changeMode(3)
                    } else {
                        tree2?.changeMode(2)
                    }
                    if (isLastOne(node.parent)) {
                        tree?.changeMode(5)
                    } else {
                        tree?.changeMode(4)
                    }
                }
            }
        }
    }

    interface OnTreeClickListener<T> {
        fun onChildClick(node: T)
        fun onNodeClick(node: T)
    }
}

const val ROOT_LEVEL: Int = 0
const val BRANCH_LEVEL: Int = 1
const val LEAF_LEVEL: Int = 2

fun showLog(msg: String) {
    Log.e("ActivityTree", msg)
}
