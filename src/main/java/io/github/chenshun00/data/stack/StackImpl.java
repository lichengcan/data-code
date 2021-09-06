package io.github.chenshun00.data.stack;

/**
 * @author luobo.cs@raycloud.com
 * @since 2021/9/6 9:17 上午
 */
public class StackImpl<E> {


    private Object[] elements;

    private int location;

    private int initialCapacity = 16;

    public StackImpl() {
        elements = new Object[initialCapacity];
        location = 0;
    }

    public void push(E data) {
        checkCapacity();
        elements[location] = data;
        location = location + 1;
    }

    @SuppressWarnings("unchecked")
    public E pop() {
        location = location - 1;
        final Object element = elements[location];
        elements[location] = null;
        return (E) element;
    }

    public int size() {
        return location;
    }

    private void checkCapacity() {
        boolean shouldResize = location / (initialCapacity * 1f) >= 0.75f;
        if (shouldResize) {
            initialCapacity = initialCapacity << 1;
        }
        Object[] temp = new Object[this.initialCapacity];
        System.arraycopy(elements, 0, temp, 0, location);
        elements = temp;
    }


}
