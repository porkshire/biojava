/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.biojava3.phylo;

import java.util.HashMap;
import java.util.Set;
import org.forester.phylogeny.Phylogeny;
import org.forester.phylogeny.PhylogenyNode;
import org.forester.phylogenyinference.BasicSymmetricalDistanceMatrix;
import org.forester.phylogenyinference.DistanceMatrix;

/**
 *
 * @author Scooter
 */
public class CheckTreeAccuracy {

    public static DistanceMatrix copyMatrix(DistanceMatrix matrix) {

        DistanceMatrix distanceMatrix = new BasicSymmetricalDistanceMatrix(matrix.getSize());
        for (int i = 0; i < matrix.getSize(); i++) {
            distanceMatrix.setIdentifier(i, matrix.getIdentifier(i));
        }

        for (int col = 0; col < matrix.getSize(); col++) {
            for (int row = 0; row < matrix.getSize(); row++) {
                distanceMatrix.setValue(col, row, matrix.getValue(col, row));
            }
        }

        return distanceMatrix;

    }

    public void process(Phylogeny tree, DistanceMatrix matrix) {
        int numSequences = matrix.getSize();
        Set<PhylogenyNode> externalNodes = tree.getExternalNodes();
        HashMap<String, PhylogenyNode> externalNodesHashMap = new HashMap<String, PhylogenyNode>();

        for (PhylogenyNode node : externalNodes) {
            externalNodesHashMap.put(node.getNodeName(), node);
        }
        int count = 0;
        double averageMatrixDistance = 0.0;
        double averageTreeDistance = 0.0;
        double averageTreeErrorDistance = 0.0;
        for (int row = 0; row < numSequences - 1; row++) {
            String nodeName1 = matrix.getIdentifier(row);
            PhylogenyNode node1 = externalNodesHashMap.get(nodeName1);
            markPathToRoot(node1, true);
            for (int col = row + 1; col < numSequences; col++) {
                count++;
                String nodeName2 = matrix.getIdentifier(col);
                PhylogenyNode node2 = externalNodesHashMap.get(nodeName2);
                double distance = matrix.getValue(col, row);
                averageMatrixDistance = averageMatrixDistance + distance;
                PhylogenyNode commonParent = findCommonParent(node2);
                if (commonParent != null) {
                    double treeDistance = getNodeDistance(commonParent, node1) + getNodeDistance(commonParent, node2);

                    averageTreeDistance = averageTreeDistance + treeDistance;
                    averageTreeErrorDistance = averageTreeErrorDistance + Math.abs(distance - treeDistance);
                    System.out.println(nodeName1 + " " + nodeName2 + " Distance: " + distance + " Tree:" + treeDistance + " difference:" + Math.abs(distance - treeDistance));
                } else {
                    System.out.println("Unable to find common parent with " + node1 + " " + node2);
                }
            }
            markPathToRoot(node1, false);
        }

        System.out.println("Average matrix distance:" + averageMatrixDistance / count);
        System.out.println("Average tree distance:" + averageTreeDistance / count);
        System.out.println("Average error:" + averageTreeErrorDistance / count);

    }

    public double getNodeDistance(PhylogenyNode parentNode, PhylogenyNode childNode) {
        double distance = 0.0;
        while (childNode != parentNode) {
            distance = distance + childNode.getDistanceToParent();
            childNode = childNode.getParent();
        }

        return distance;
    }

    public PhylogenyNode findCommonParent(PhylogenyNode node) {
        while (node.getPathToParent() == false) {
            node = node.getParent();
        }
        return node;
    }

    public void markPathToRoot(PhylogenyNode node, boolean value) {
        node.setPathToParent(value);
        while (node.isRoot() == false) {
            node = node.getParent();
            node.setPathToParent(value);
        }
    }
}
