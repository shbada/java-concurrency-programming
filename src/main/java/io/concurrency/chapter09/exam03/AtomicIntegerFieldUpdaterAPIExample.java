package io.concurrency.chapter09.exam03;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicIntegerFieldUpdaterAPIExample {
    // Atomic * FieldUpdater 선언
    static AtomicIntegerFieldUpdater<MyClass> fieldUpdater1;
    static AtomicReferenceFieldUpdater<MyClass, String> fieldUpdater2;

    public static class MyClass {
        // 규칙 1. volatile 이여야한다.
        private volatile int field1;
        private volatile String field2;

        public int getField1() {
            return field1;
        }

        public String getField2() {
            return field2;
        }
    }

    public static void main(String[] args) {
        // 해당 필드에 대해서 원자적 수행을 하겠다
        fieldUpdater1 = AtomicIntegerFieldUpdater.newUpdater(MyClass.class, "field1");
        fieldUpdater2 = AtomicReferenceFieldUpdater.newUpdater(MyClass.class, String.class, "field2");

        MyClass instance = new MyClass();
        fieldUpdater1.addAndGet(instance, 42); // 원자성 보장
        fieldUpdater2.compareAndSet(instance, null, "myField");  // 원자성 보장


        System.out.println("Updated value: " + instance.getField1()); // 42
        System.out.println("Updated value: " + instance.getField2()); // myField
    }
}
