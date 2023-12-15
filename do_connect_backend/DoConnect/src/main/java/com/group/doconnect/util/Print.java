package com.group.doconnect.util;


import java.util.*;


public class Print {

    //only deal with integer values stored in tree nodes
    public static void treeLevelOrderPrint(TreeNode root) {
        List result = new ArrayList<>();
        if (root == null) {
            System.out.println("root is null");
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int height = 0;
        int levelSize = 1;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            result.add(node.val);
            levelSize--;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
            if (levelSize == 0) {
                height++;
                levelSize = queue.size();
            }
        }
        System.out.println(result);
        System.out.println("Tree Height: " + height);
    }

    public static <E> void printList(List<E> list) {
        if (list.size() == 0 || list == null) {
            System.out.println("[ ]");
        } else {
            System.out.print("[ ");
            int lastIndex = list.size() - 1;
            for (int i = 0; i <= lastIndex - 1; i++) {
                System.out.print(list.get(i).toString() + ", ");
            }
            System.out.println(list.get(lastIndex).toString() + " ]");
        }
    }

    public static <E> void print2DList(List<List<E>> list) {
        try {
            if (list.size() == 0 || list.isEmpty()) {
                System.out.println("[]");
            } else {
                System.out.print("[ ");
                for (int i = 0; i < list.size() - 1; i++) {
                    System.out.print(list.get(i).toString() + ", ");
                }
                System.out.print(list.get(list.size() - 1).toString());
                System.out.println(" ]");
            }
        } catch (NullPointerException e) {
            // TODO: handle exception
            System.out.println("Null");
        }
    }




    public static void printIntArray_2d(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < array[i].length - 1; j++) {
                System.out.print(array[i][j] + "  ");
            }
            System.out.println(array[i][array[i].length - 1] + " ]");
        }
    }

    public static void printIntArray_2d_flat(int[][] array) {
        for (int i = 0; i < array.length - 1; i++) {
            System.out.print("[[ ");
            for (int j = 0; j < array[i].length - 1; j++) {
                System.out.print(array[i][j] + "  ");
            }
            System.out.print(array[i][array[i].length - 1] + " ],  ");
        }
        System.out.print("[ ");
        for (int j = 0; j < array[array.length - 1].length - 1; j++) {
            System.out.print(array[array.length - 1][j] + "  ");
        }
        System.out.print(array[array.length - 1][array[array.length-1].length-1] + " ]]");
        System.out.println();
    }

    public static void printBoolArray_2d(boolean[][] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < array[i].length - 1; j++) {
                System.out.print(array[i][j] + "  ");
            }
            System.out.println(array[i][array[i].length - 1] + " ]");
        }
    }

    public static void printCharArray_2d(char[][] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print("{ ");
            for (int j = 0; j < array[i].length - 1; j++) {
                System.out.print(array[i][j] + "  ");
            }
            System.out.println(array[i][array[i].length - 1] + " }");
        }
    }

    public static void printArray(Object array) {
        String arrayClassName = array.getClass().getSimpleName();
        try {
            if (arrayClassName.equals("int[]")) {
                System.out.println(Arrays.toString((int[]) array));
            } else if (arrayClassName.equals("char[]")) {
                System.out.println(Arrays.toString((char[]) array));
            } else if (arrayClassName.equals("boolean[]")) {
                System.out.println(Arrays.toString((boolean[]) array));
            } else if (arrayClassName.equals("String[]")) {
                System.out.println(Arrays.toString((String[]) array));
            } else if(arrayClassName.equals("Integer[]")){
                System.out.println(Arrays.toString((Integer[]) array));
            } else {
                System.out.println("Unrecognized Class...");
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("ERROR OCCURED...");
        }
    }

    public static <K, V> void printMap(Map<K, V> map) {
        System.out.print("{ ");
        for (K i : map.keySet()) {
            System.out.print("[key: " + i.toString() + ", value: " + map.get(i).toString() + "] ");
        }
        System.out.println("}");
    }

    public static void printArray(int[] array, int length) {
        System.out.print("{ ");
        int lastIndex = length - 1;
        for (int i = 0; i <= lastIndex - 1; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.println(array[lastIndex] + " }");
    }

//	public static <E> void printArray(E[] inputArray) {
//		// 输出数组元素
//		for (E element : inputArray) {
//			System.out.printf("%s ", element);
//		}
//		System.out.println();
//	}

    public static <E> void printSet(Set<E> set) {
        Iterator<E> itr = set.iterator();
        System.out.print("Set = { " + itr.next());
        while (itr.hasNext()) {
            System.out.print(", " + itr.next());
        }
        System.out.println(" }");
    }

    public static <E> void printStack(Stack<E> stack) {
        System.out.println("---Stack---");
        Stack copy = (Stack) stack.clone();
        while (copy.size() != 0) {
            System.out.println(copy.pop());
        }
        System.out.println("-----------");
    }
}
