package com.zcl.algorithm.lockfree;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by zhaocl1 on 2018/7/16.
 */
public class LinkedQueue<E> {
    private static class Node<E>{
        final E item;
        final AtomicReference<Node<E>> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<Node<E>>(next);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    ", next=" + next +
                    '}';
        }
    }

    private AtomicReference<Node<E>> head = new AtomicReference<>(new Node<E>(null,null));
    private AtomicReference<Node<E>> tail = head;

    /**queue的put需要操作两个指针
     * 如果A线程用cas顺序更新tail.next已经tail，那么两步操作可能存在一个不一致的状态，即tail指向的不是最后一个节点
     * 假设线程B在A完成第一步后调用put，此时B可以判断出A的更新完成了还是正在进行（tail.next == null ?），如果B选择自旋等待A完成，那么可能出现A第二个CAS失败，
     * B会一直等待下去的情况。因此，B必须选择 帮助A完成剩下的更新动作，等A切换回来执行第二个CAS时，可以不管CAS的结果，因为如果失败了，它知道有另一个线程帮它做完了剩下的工作。更详细的步骤解释可以参考Java 理论与实践: 非阻塞算法简介。
     *
     * @param item
     * @return
     */
    public boolean put(E item){
        Node<E> newNode = new Node<>(item,null);
        while (true){
            Node<E> curTail = tail.get();//获取当前尾指针
            Node<E> tailNext = curTail.next.get();//或者尾指针的next
            //再次比较 curTail是否是尾指针，在并发的情况下，有可能已经不是了，其他线程已经put了，因此自旋，重新get
            if (curTail == tail.get()){
                //判断尾指针的next是否是null，为null则说明是静止状态，没被改变
                if (tailNext == null){
                    //cas更新next，不成功则说明这里并发下next已经被修改了
                    if (curTail.next.compareAndSet(null,newNode)){
                        //cas尝试更新tail节点，不用判断结果，如果失败，则说明另一个线程帮忙完成了这个动作
                        tail.compareAndSet(curTail,newNode);
                        return true;
                    }
                }else {
                    //程序走到这个地方的意思是，curtail还是尾节点，但是尾节点的next已经更新成功了，即另一个线程的第一个cas已经成功，所以这个地方就帮忙更新其tail
                    tail.compareAndSet(curTail,tailNext);
                }
            }
        }
    }

    public static void main(String[] args) {
        LinkedQueue<String> queue = new LinkedQueue<>();
        queue.put("first item");
        queue.put("second item");
        System.out.println("head=" + queue.head);
        System.out.println("tail="+queue.tail);
    }
}
