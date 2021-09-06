package io.github.chenshun00.data.list;

/**
 * @author chenshun00@gmail.com
 * @since 2021/7/21 9:15 下午
 */
public class ArrayListImpl<T> {
    private int location = 0;

    private Object[] elements;

    private int initialCapacity;

    public ArrayListImpl(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        this.elements = new Object[this.initialCapacity];
    }

    public int size() {
        return location;
    }

    public void add(T data) {
        checkCapacity();
        elements[location] = data;
        location = location + 1;
    }

    public void add(int index, T data) {
        checkCapacity();
        System.arraycopy(elements, index, elements, index + 1, location - index);
        elements[index] = data;
        location = location + 1;
    }

    public boolean contains(T data) {
        for (Object element : elements) {
            if (data.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public boolean remove(T data) {
        final int length = location;
        int tempIndex = -1;
        for (int i = 0; i < length; i++) {
            final Object element = elements[i];
            if (element.equals(data)) {
                tempIndex = i;
                break;
            }
        }
        if (tempIndex < 0) {
            return false;
        }
        System.arraycopy(elements, tempIndex + 1, elements, tempIndex, length - tempIndex);
        location--;
        return true;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) elements[index];
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
