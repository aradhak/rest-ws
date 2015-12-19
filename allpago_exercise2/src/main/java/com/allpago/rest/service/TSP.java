package com.allpago.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author aradhak
 * 
 */
public class TSP{
    private int numberOfNodes;
    private Stack<Integer> stack;
	private Logger logger = LoggerFactory.getLogger(TSP.class);

    public TSP(){
        stack = new Stack<Integer>();
    }
 
    public List<Integer> tsp(int adjacencyMatrix[][]){
    	logger.debug("tsp matrix row={}, col={}",adjacencyMatrix.length,adjacencyMatrix.length);
        List<Integer> list = new ArrayList<Integer>();
    	numberOfNodes = adjacencyMatrix[0].length;
        int[] visited = new int[numberOfNodes];
        visited[0] = 1;
        stack.push(0);
        int element, dst = 0, i;
        int min = Integer.MAX_VALUE;
        boolean minFlag = false;
   //     System.out.print(0 + "\t");
        list.add(0);
        while (!stack.isEmpty()) {
            element = stack.peek();
            i = 0;
            min = Integer.MAX_VALUE;
            while (i < numberOfNodes)
            {
                if (adjacencyMatrix[element][i] > 1 && visited[i] == 0)
                {
                    if (min > adjacencyMatrix[element][i])
                    {
                        min = adjacencyMatrix[element][i];
                        dst = i;
                        minFlag = true;
                    }
                }
                i++;
            }
            if (minFlag)
            {
                visited[dst] = 1;
                stack.push(dst);
          //      System.out.print(dst + "\t");
                list.add(dst);
                minFlag = false;
                continue;
            }
            stack.pop();
        }
        return list;
    }
 
}