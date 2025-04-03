package com.failuredetection.util;

import java.util.LinkedList;
import java.util.Queue;

import com.failuredetection.model.Node;
import com.failuredetection.model.TreeNode;

public class MathUtil {

	public static Double calculateMean(TreeNode<Node> root) {

		Double mean = 0.0;
		int count = 0;

		if (root != null) {

			Queue<TreeNode<Node>> queue = new LinkedList<>();

			queue.add(root); // push operation

			++count;

			while (!queue.isEmpty()) {

				TreeNode<Node> treeNode = queue.poll();
				queue.poll();

				++count;

				mean += treeNode.getData().getExecutionTime();

				for (TreeNode<Node> child : treeNode.getChildren()) {
					queue.add(child);
				}

			}

			mean /= count;

		}

		return mean;
	}

	public static Double calculateStandardDeviation(TreeNode<Node> root, Double mean) {

		Double standardDeviation = 0.0;
		int count = 0;

		if (root != null) {

			Queue<TreeNode<Node>> queue = new LinkedList<>();

			queue.add(root); // push operation

			++count;

			while (!queue.isEmpty()) {

				TreeNode<Node> treeNode = queue.poll();
				queue.poll();

				++count;

				standardDeviation += (new Double(treeNode.getData().getExecutionTime()) - mean)
						* (new Double(treeNode.getData().getExecutionTime()) - mean);

				for (TreeNode<Node> child : treeNode.getChildren()) {
					queue.add(child);
				}

			}

			standardDeviation /= count;

		}

		return Math.sqrt(standardDeviation);
	}

}
