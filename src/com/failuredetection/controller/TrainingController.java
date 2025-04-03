package com.failuredetection.controller;

import java.util.List;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import com.failuredetection.model.Node;
import com.failuredetection.model.TreeNode;
import com.failuredetection.treelayout.TextInBox;
import com.failuredetection.util.Constants;
import com.failuredetection.view.Main;

public class TrainingController {
	
	public static TreeForTreeLayout<TextInBox> tree;

	public TreeNode<Node> buildTree(final List<String> lines, Boolean isDetectionMode) {
		TreeNode<Node> root = null;

		Integer currentLevel = 0, index = 0;
		TreeNode<Node> currentNode = null;
		
		TextInBox rootBox = null;
		TextInBox currentBox = null;
		
		DefaultTreeForTreeLayout<TextInBox> tree = null;
		
		try {
			for (String line : lines) {

				String[] splittedStr = line.split(" ");

				// rootnode
				if (index == 0) {

					if (Constants.COMMAND_CALL.equals(splittedStr[1])) {
						Node node = new Node();

						node.setLevel(++currentLevel);
						node.setStartTime(Integer.parseInt(splittedStr[0]));
						node.setName(splittedStr[2]);

						root = new TreeNode<Node>(node);
						rootBox = new TextInBox(node.getName());

						currentNode = root;
						currentBox = rootBox;
						
						tree = new DefaultTreeForTreeLayout<TextInBox>(rootBox);
					} else {
						// TODO show error
					}

				} else {

					if (Constants.COMMAND_CALL.equals(splittedStr[1])) {

						Node node = new Node();

						node.setLevel(++currentLevel);
						node.setStartTime(Integer.parseInt(splittedStr[0]));
						node.setName(splittedStr[2]);

						TreeNode<Node> treeNode = new TreeNode<Node>(node);
						
						TextInBox tempTextInBox = new TextInBox(node.getName());
						
						tree.addChild(currentBox, tempTextInBox);
						currentBox = tempTextInBox;
						
						currentNode.addChild(treeNode);
						currentNode = treeNode;

					} else {

						Integer endTime = Integer.parseInt(splittedStr[0]);

						Node node = currentNode.getData();

						node.setEndTime(endTime);
						node.setExecutionTime(endTime - node.getStartTime());
						
						if(Boolean.TRUE.equals(isDetectionMode)) {
							// Check if its deviating/abnormal or not
							
							if((new Double(node.getExecutionTime())) < (Main.mean - 2 * Main.standardDeviation) || 
									(new Double(node.getExecutionTime())) > (Main.mean + 2 * Main.standardDeviation)) {
								
								return null;
								
							}
							
						}

						currentNode = currentNode.getParent();
						currentBox = tree.getParent(currentBox);
						--currentLevel;
					}

				}

				index++;

			}
		} catch (Exception e) {
			TrainingController.tree = null;
			return null;
		}
		
		TrainingController.tree = tree;
		return root;
	}
	
	public Boolean matchTree(List<String> lines1, List<String> lines2) {
		
		if(lines1.size() != lines2.size()) {
			return Boolean.FALSE;
		}
		
		for(int i = 0; i < lines1.size(); i++) {
			if(!(lines1.get(i).equals(lines2.get(i)))) {
				return Boolean.FALSE;
			}
		}
		
		return Boolean.TRUE;
	}

}
