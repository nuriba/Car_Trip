/**
 * The MaxBinaryHeap class represents a max-heap data structure specifically tailored for managing Song objects.
 * It is primarily used for efficiently accessing and managing songs based on certain criteria (e.g., countNumber).
 * Also, the other Heap classes work same. The only difference is their comparisons.
 */
import java.util.ArrayList;
import java.util.HashMap;

public class MaxBinaryHeap {
    public ArrayList<Song> array;
    public int size;
    HashMap<Integer,Integer> songIDAndIndex = new HashMap<>();
    /**
     * Constructor for creating an empty MaxBinaryHeap instance.
     */
    MaxBinaryHeap(){
        size = 0;
        array = new ArrayList<>();
        array.add(null);
    }
    /**
     * Inserts a new Song into the heap.
     * The Song is placed according to its countNumber, maintaining the max-heap property.
     *
     * @param value The Song object to be inserted into the heap.
     */
    public void insert(Song value) {
        size++;
        array.add(value);
        int hole = size;
        songIDAndIndex.put(value.ID,size);
        while(hole > 1 && value.countNumber >= array.get(hole/2).countNumber ) {
            if (value.countNumber==array.get(hole/2).countNumber){
                if (value.name.compareToIgnoreCase(array.get(hole/2).name) >=0)
                    break;
            }
            Song parent = array.get(hole / 2);
            array.set(hole / 2, value);
            songIDAndIndex.put(value.ID,hole/2);
            array.set(hole, parent);
            songIDAndIndex.put(parent.ID,hole);
            hole = hole / 2;
        }
    }
    /**
     * Retrieves, but does not remove, the Song at the top of the heap.
     *
     * @return The Song object at the top of the heap.
     */
    public Song peek() {
        return array.get(1);
    }
    /**
     * Removes and returns the Song at the top of the heap, adjusting the heap accordingly to maintain its properties.
     *
     * @return The removed Song object.
     */
    public Song pop() {
        Song maxItem = peek();
        Song lastElement = array.get(size);
        songIDAndIndex.remove(maxItem.ID);
        array.set(1,lastElement);
        songIDAndIndex.put(lastElement.ID,1);
        array.remove(size);
        size--;
        if (size>1) {
            percolateDown(1);
        }
        return maxItem;
    }
    /**
     * Removes a specific Song from the heap.
     *
     * @param song The Song object to be removed.
     */
    public void delete(Song song){
        if (song.ID==array.get(1).ID) {
            pop();
            return;
        }
        if (song.ID== array.get(size).ID) {
            array.remove(size);
            songIDAndIndex.remove(song.ID);
            size--;
            return;
        }
        int hole = songIDAndIndex.get(song.ID);
        songIDAndIndex.remove(song.ID);
        array.set(hole,array.get(size));
        songIDAndIndex.put(array.get(size).ID,hole);
        array.remove(size);
        size--;
        if (size>1) {
            percolateDown(hole);
            percolateUp(hole);
        }
    }
    /**
     * Percolates a Song object up the heap to its appropriate position to maintain the max-heap property.
     *
     * @param hole The index of the Song object that needs to be percolated up.
     */
    private void percolateUp(int hole){
        while (hole > 1 && array.get(hole).countNumber >= array.get(hole / 2).countNumber) {
            if (array.get(hole).countNumber == array.get(hole / 2).countNumber) {
                if (array.get(hole).name.compareToIgnoreCase(array.get(hole / 2).name) >= 0)
                    break;
            }
            Song parent = array.get(hole / 2);
            array.set(hole / 2, array.get(hole));
            songIDAndIndex.put(array.get(hole).ID, hole / 2);
            array.set(hole, parent);
            songIDAndIndex.put(parent.ID, hole);
            hole = hole / 2;
        }
    }
    /**
     * Percolates a Song object down the heap to its appropriate position to maintain the max-heap property.
     *
     * @param hole The index of the Song object that needs to be percolated down.
     */
    private void percolateDown(int hole) {
        int child;
        Song temp = array.get(hole);

        while(hole * 2 <= size) {
            child = hole * 2;
            if(child != size && array.get(child + 1).countNumber >= array.get(child).countNumber) {
                if (array.get(child+1).countNumber == array.get(child).countNumber){
                    if (array.get(child+1).name.compareToIgnoreCase(array.get(child).name) < 0)
                        child++;
                }else
                    child++;
            }
            if(array.get(child).countNumber >= temp.countNumber) {
                if (array.get(child).countNumber==temp.countNumber){
                    if (array.get(child).name.compareToIgnoreCase(temp.name) < 0) {
                        array.set(hole, array.get(child));
                        songIDAndIndex.put(array.get(child).ID, hole);
                    }
                    else{
                        break;
                    }
                }else{
                    array.set(hole, array.get(child));
                    songIDAndIndex.put(array.get(child).ID,hole);
                }
            }else {
                break;
            }

            hole = child;
        }
        array.set(hole, temp);
        songIDAndIndex.put(temp.ID,hole);
    }
}
