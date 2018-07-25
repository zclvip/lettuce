package com.zcl.algorithm.lockfree;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by zhaocl1 on 2018/7/14.
 */
public class ConcurrentStack<E> {
    static class Node<E>{
        final E item;
        Node<E> next;
        public Node(E item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    ", next=" + next +
                    '}';
        }
    }


    /**
     * 从数据结构可以看出，是一个单向链表，
     * head执行的是头结点的引用，这是一个巧妙的设计，因为cas只能操作一个值，如果在尾部则需要更新两个指针tail和tail.next
     * cas无法对两个指针进行原子性cas，每个新进来的值都在头部，所以就是个栈
     */
    AtomicReference<Node<E>> head = new AtomicReference<>();
    public void push(E item){
        Node<E> newHead = new Node<>(item);
        Node<E> oldHead;
        do {
            oldHead = head.get();
            newHead.next = oldHead;
        }while (!head.compareAndSet(oldHead,newHead));
    }

    public E pop(){
        Node<E> oldHead;
        Node<E> newHead;
        do {
            oldHead = head.get();
            if (oldHead ==null){
                return null;
            }
            newHead = oldHead.next;
        }while (!head.compareAndSet(oldHead,newHead));
        return oldHead.item;
    }

    public static void main(String[] args) {
        ConcurrentStack<String> stack = new ConcurrentStack<>();
        stack.push("zhang san");
        stack.push("li si");
        System.out.println(stack.head);//Node{item=li si, next=Node{item=zhang san, next=null}}

        String result = stack.pop();
        System.out.println(result);//li si
    }
}
