/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trees;

import org.biojava3.phylo.Comparison;

/**
 * Porównywanie dwóch drzew.
 * @author DanielWegner
 */
public class TreeComparer 
{
    public TreeComparer()
    { }
    
    public float getCompareMetric(String tree1, String tree2, int start, int end)
    {
        float result = Comparison.PID(tree1, tree2, start, end);
        return result;
    }
}
