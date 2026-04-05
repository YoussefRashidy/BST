package io.github.youssefrashidy;

import io.github.youssefrashidy.Trees.RBBST;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }

        RBBST<Integer,Integer> rbtree = new RBBST<>() ;
        rbtree.insert(5, 5) ;
        rbtree.insert(10,10) ;
        rbtree.insert(11,11) ;
        rbtree.insert(12,12) ;
        rbtree.insert(8,13) ;
        rbtree.insert(1,13) ;
        rbtree.insert(14,13) ;
        rbtree.insert(6,13) ;
        rbtree.insert(19,13) ;


        var inorder = rbtree.inOrder() ;
        System.out.println(inorder);
        rbtree.delete(19) ;
        rbtree.delete(13) ;
        rbtree.delete(12) ;
        inorder = rbtree.inOrder() ;
        System.out.println(inorder);

    }
}