package com.liyang.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class TreeBuilder {

	/**
	 * 两层循环实现建树
	 * 
	 * @param treeNodes
	 *            传入的树节点列表
	 * @return
	 */
	public static List<TreeNodeImpl> bulid(List<TreeNodeImpl> treeNodes) {

		List<TreeNodeImpl> trees = new ArrayList<TreeNodeImpl>();

		for (TreeNodeImpl treeNode : treeNodes) {

			if (treeNode.getParentId().equals(0)) {
				trees.add(treeNode);
			}
			for (TreeNode it : treeNodes) {
				if (it.getParentId().equals(treeNode.getId())) {
					if (treeNode.getChildren() == null) {
						treeNode.setChildren(new ArrayList<TreeNodeImpl>());
					}
					treeNode.getChildren().add(it);
				}
			}
			if (treeNode.getChildren() != null) {
				Collections.sort(treeNode.getChildren());
			}
		}
		Collections.sort(trees);
		return trees;
	}

	/**
	 * 使用递归方法建树
	 * 
	 * @param treeNodes
	 * @return
	 */
	public static List<TreeNode> buildByRecursive(List<TreeNode> treeNodes) {
		List<TreeNode> trees = new ArrayList<TreeNode>();
		for (TreeNode treeNode : treeNodes) {
			if ("0".equals(treeNode.getParentId())) {
				trees.add(findChildren(treeNode, treeNodes));
			}
		}
		return trees;
	}

	/**
	 * 递归查找子节点
	 * 
	 * @param treeNodes
	 * @return
	 */
	public static TreeNode findChildren(TreeNode treeNode, List<TreeNode> treeNodes) {
		for (TreeNode it : treeNodes) {
			if (treeNode.getId().equals(it.getParentId())) {
				if (treeNode.getChildren() == null) {
					treeNode.setChildren(new ArrayList<TreeNode>());
				}
				treeNode.getChildren().add(findChildren(it, treeNodes));
			}
		}
		return treeNode;
	}

}