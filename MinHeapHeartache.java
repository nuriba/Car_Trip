import java.util.ArrayList;
import java.util.HashMap;

public class MinHeapHeartache {
    public ArrayList<Song> array;
    public int size;
    HashMap<Integer,Integer> songIDAndIndex = new HashMap<>();
    MinHeapHeartache(){
        size = 0;
        array = new ArrayList<>();
        array.add(null);
    }
    public void insert(Song value) {
        size++;
        array.add(value);
        int hole = size;
        songIDAndIndex.put(value.ID,size);
        while(hole > 1 && value.heartacheScore <= array.get(hole/2).heartacheScore ) {
            if (value.heartacheScore==array.get(hole/2).heartacheScore){
                if (value.name.compareToIgnoreCase(array.get(hole/2).name) <=0) {
                    break;
                }
            }
            Song parent = array.get(hole / 2);
            array.set(hole / 2, value);
            songIDAndIndex.put(value.ID,hole/2);
            array.set(hole, parent);
            songIDAndIndex.put(parent.ID,hole);
            hole = hole / 2;
        }
    }

    public Song peek() {
        return array.get(1);
    }

    public Song pop() {
        Song minItem = peek();
        Song lastElement = array.get(size);
        songIDAndIndex.remove(minItem.ID);
        array.set(1,lastElement);
        songIDAndIndex.put(lastElement.ID,1);
        array.remove(size);
        size--;
        if (size>1)
            percolateDown(1);

        return minItem;
    }
    public int size() {
        return size;
    }
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
            while (hole > 1 && array.get(hole).heartacheScore <= array.get(hole / 2).heartacheScore) {
                if (array.get(hole).heartacheScore == array.get(hole / 2).heartacheScore) {
                    if (array.get(hole).name.compareToIgnoreCase(array.get(hole / 2).name) <= 0) {
                        break;
                    }
                }
                Song parent = array.get(hole / 2);
                array.set(hole / 2, array.get(hole));
                songIDAndIndex.put(array.get(hole).ID, hole / 2);
                array.set(hole, parent);
                songIDAndIndex.put(parent.ID, hole);
                hole = hole / 2;
            }
        }
    }
    private void percolateDown(int hole) {
        int child;
        Song temp = array.get(hole);

        while(hole * 2 <= size) {
            child = hole * 2;
            if(child != size && array.get(child + 1).heartacheScore <= array.get(child).heartacheScore) {
                if (array.get(child+1).heartacheScore == array.get(child).heartacheScore){
                    if (array.get(child+1).name.compareToIgnoreCase(array.get(child).name) > 0)
                        child++;
                }else
                    child++;
            }
            if(array.get(child).heartacheScore <= temp.heartacheScore) {
                if (array.get(child).heartacheScore==temp.heartacheScore){
                    if (array.get(child).name.compareToIgnoreCase(temp.name) > 0) {
                        array.set(hole, array.get(child));
                        songIDAndIndex.put(array.get(child).ID, hole);
                    }else{
                        break;
                    }
                }else {
                    array.set(hole, array.get(child));
                    songIDAndIndex.put(array.get(child).ID, hole);
                }
            }else {
                break;
            }

            hole = child;
        }
        array.set(hole, temp);
        songIDAndIndex.put(temp.ID, hole);
    }
}
