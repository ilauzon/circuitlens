package com.circuitlens.app;

import androidx.annotation.NonNull;

public class BoolNode<T> {
    BoolNode<?> left;
    BoolNode<?> right;
    public final T value;

    public BoolNode(T value) {
        this.value = value;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    // prints the tree via inorder tree traversal, with parentheses.
    @NonNull
    public String toString() {
        String result = "(";

        if (left != null) { // special case for NOT, a unary operator
            if (value != LogicalOperator.NOT) {
                result += left.toString();
            }
        }

        result += value;

        if (right != null) {
            result += right.toString();
        }

        return result + ")";
    }

    public void printTree(int spaces) {
        if (left != null) {
            if (value == LogicalOperator.NOT) { //special case for NOT, since it is a unary operator
                System.out.print("\n");
            } else {
                left.printTree(spaces + 4);
            }

        }

        for (int i = 0; i < spaces; i++) {
            System.out.print(" ");
        }
        System.out.println(value);

        if (right != null) {
            right.printTree(spaces + 4);
        }
    }
}
