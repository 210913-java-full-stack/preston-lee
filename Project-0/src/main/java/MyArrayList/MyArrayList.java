package MyArrayList;

import java.util.Arrays;

public class MyArrayList<E> implements MyListInterface<E>{
    private int size = 0;
    private Object elements[];
    private static int default_amount = 5;

    public MyArrayList()
    {
        //create new array for arraylist of the default amount of 5
        elements = new Object[default_amount];
    }

    @Override
    public int size() {
        //return size
        return size;
    }

    @Override
    public void add(E e) {
        //if we are at max capacity in arrayList
        if(size >= elements.length)
        {
            //doubles capacity!
            increaseCapacity();
        }
        //if not at max capacity, just add the element to the end.
        elements[size++] = e;

    }

    @Override
    public void add(E e, int index) {
        //if the index is larger than the size of the arraylist or less than 0, send out of bounds exception
        if(index >= size || index < 0)
        {
            throw new IndexOutOfBoundsException(index + " is out of bounds.");
        }
        //otherwise, if the adding another element will put us past the size of the array, increase the capacity.
        if((size+1) >= elements.length)
        {
            increaseCapacity();
        }

        //after we've done that, shift all the objects down one so we can insert at the given index.
        for(int i = size;i >= index;i--)
        {
            elements[i+1] = elements[i];
        }

        //insert given item at the index provided
        elements[index] = e;
        //increase the size (a la current element number) for arrayList.
        size++;
    }

    @Override
    public E get(int index) {
        //return item at given index of the array
        return (E) elements[index];
    }

    @Override
    public void remove(int index) {
        //moving all elements up on the list, replacing the one the user wants to remove @ index provided
        for(int i=index;i<size;i++)
        {
            elements[i] = elements[i+1];
        }
        //decreasing current size because we removed an element.
        size--;
    }

    @Override
    public void clear() {
        //creating a new array with nothing in it, of the same size as our current one.
        Object newArray[] = new Object[size];
        //setting a copy of that new empty array to elements, effectively clearing all objects that were currently in elements.
        elements = Arrays.copyOf(newArray, size);
        size = 0;

    }

    @Override
    public int contains(E e) {
        //go through the elements, if one of them matches return the index it was at.
        for(int i=0;i<size;i++)
        {
            if(elements[i] == e)
            {
                return i;
            }
            else return -1;
        }

        return -1;
    }

    private void increaseCapacity()
    {
        //double the size of the current array.
        int newSize = elements.length * 2;
        //copy current elements into an array of the bigger size and assign to elements.
        elements = Arrays.copyOf(elements, newSize);
    }
}