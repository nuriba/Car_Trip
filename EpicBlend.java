/**
 * The EpicBlend class is responsible for managing a collection of songs categorized into different moods like RoadTrip,
 * Heartache, and Blissful. It provides functionality to create and manage these song collections.
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
public class EpicBlend {
    MaxHeapRoadTrip allRoadTripSongs = new MaxHeapRoadTrip();
    MaxheapHeartache allHeartacheSongs = new MaxheapHeartache();
    MaxHeapBlissful allBlissfulSongs = new MaxHeapBlissful();
    HashMap<Integer, MaxHeapBlissful> waitingForEpicBlissFul = new HashMap<>();
    HashMap<Integer, MaxHeapRoadTrip> waitingForEpicRoadTrip = new HashMap<>();
    HashMap<Integer, MaxheapHeartache> waitingForEpicHeartache = new HashMap<>();
    HashMap<Integer, MinHeapRoadTrip> epicBlendRoadTripHashMap = new HashMap<>();
    HashMap<Integer, MinHeapHeartache> epicBlendHeartacheHashMap = new HashMap<>();
    HashMap<Integer, MinHeapBlissful> epicBlendBlissfulHashMap = new HashMap<>();
    MaxHeapRoadTrip roadTripForEpicBlend = new MaxHeapRoadTrip();
    MinHeapRoadTrip roadTripWithMinHeap = new MinHeapRoadTrip();
    MaxheapHeartache heartacheForEpicBlend = new MaxheapHeartache();
    MinHeapHeartache heartacheWithMinHeap = new MinHeapHeartache();
    MaxHeapBlissful blissfulForEpicBlend = new MaxHeapBlissful();
    MinHeapBlissful blissfulWithMinHeap = new MinHeapBlissful();
    MaxBinaryHeap epicBlendList = new MaxBinaryHeap();
    int limitForPlaylist;
    int limitForHeartache;
    int limitForRoadTrip;
    int limitForBlissful;
    /**
     * Constructor to initialize the EpicBlend with limits for playlist, heartache, road trip, and blissful song counts.
     * @param limitForPlaylist   The maximum number of songs allowed in a playlist.
     * @param limitForBlissful   The maximum number of blissful songs allowed.
     * @param limitForHeartache  The maximum number of heartache songs allowed.
     * @param limitForRoadTrip   The maximum number of road trip songs allowed.
     */
    EpicBlend(int limitForPlaylist, int limitForBlissful, int limitForHeartache, int limitForRoadTrip) {
        this.limitForPlaylist = limitForPlaylist;
        this.limitForBlissful = limitForBlissful;
        this.limitForHeartache = limitForHeartache;
        this.limitForRoadTrip = limitForRoadTrip;
    }
    /**
     * Adds a song to all mood categories (RoadTrip, Heartache, Blissful).
     * AllSongsLists hold the songs before deciding whether the song is in the epic blend or not.
     * @param song The song to be added.
     */
    public void addToAllSongsList(Song song){
        addToAllSongsRoadTrip(song);
        addToAllSongsHeartache(song);
        addToAllSongsBlissful(song);
    }

    /**
     * following three private methods adds a song to given category.
     * @param song The song to be added
     */
    private void addToAllSongsRoadTrip(Song song){
        allRoadTripSongs.insert(song);
    }
    private void addToAllSongsHeartache(Song song){
        allHeartacheSongs.insert(song);
    }
    private void addToAllSongsBlissful(Song song){
        allBlissfulSongs.insert(song);
    }
    /**
     * Creates the final EpicBlend by combining songs from different mood categories.
     * It ensures that the final blend adheres to the specified limits for each category.
     */
    public void createEpicBlend(){
        createEpicBlissful();
        createEpicHeartache();
        createEpicRoadTrip();
        for (int i = 1; i <= blissfulForEpicBlend.size(); i++) {
            Song song = blissfulForEpicBlend.array.get(i);
            if (song.isItInEpicList)
                continue;
            song.isItInEpicList = true;
            epicBlendList.insert(song);
        }
        for (int i = 1; i <= roadTripForEpicBlend.size(); i++) {
            Song song = roadTripForEpicBlend.array.get(i);
            if (song.isItInEpicList)
                continue;
            song.isItInEpicList = true;
            epicBlendList.insert(song);
        }
        for (int i = 1; i <= heartacheForEpicBlend.size(); i++) {
            Song song = heartacheForEpicBlend.array.get(i);
            if (song.isItInEpicList)
                continue;
            song.isItInEpicList = true;
            epicBlendList.insert(song);
        }
    }

    /**
     * Following three private methods creates epic blend for the given category. Helps to create final epic blend
     */
    private void createEpicBlissful(){
        while (blissfulForEpicBlend.size() < limitForBlissful && allBlissfulSongs.size() > 0) {
            Song song = deletePeekBlissfulFromAllSongs();
            int numberOfElement = numberOfElementFromTheCurrentPlaylist(song.inWhichPlaylist, "blissful");
            if (numberOfElement >= limitForPlaylist) {
                addToTheWaitingMap(song, "blissful");
                continue;
            }
            blissfulForEpicBlend.insert(song);
            blissfulWithMinHeap.insert(song);
            song.isItInEpicBlissful = true;
            addToTheHashMap(song, "blissful");
        }
    }
    private void createEpicHeartache(){
        while (heartacheForEpicBlend.size() < limitForHeartache && allHeartacheSongs.size() > 0) {
            Song song = deletePeekHeartacheFromAllSongs();
            int numberOfElement = numberOfElementFromTheCurrentPlaylist(song.inWhichPlaylist, "heartache");
            if (numberOfElement >= limitForPlaylist) {
                addToTheWaitingMap(song, "heartache");
                continue;
            }
            heartacheForEpicBlend.insert(song);
            heartacheWithMinHeap.insert(song);
            song.isItInEpicHeartache = true;
            addToTheHashMap(song, "heartache");
        }
    }
    private void createEpicRoadTrip(){
        while (roadTripForEpicBlend.size() < limitForRoadTrip && allRoadTripSongs.size() > 0) {
            Song song = deletePeekRoadTripFromAllSongs();
            int numberOfElement = numberOfElementFromTheCurrentPlaylist(song.inWhichPlaylist, "roadTrip");
            if (numberOfElement >= limitForPlaylist) {
                addToTheWaitingMap(song, "roadTrip");
                continue;
            }
            roadTripForEpicBlend.insert(song);
            roadTripWithMinHeap.insert(song);
            song.isItInEpicRoadTrip = true;
            addToTheHashMap(song, "roadTrip");
        }
    }
    /**
     * Prints the current state of the EpicBlend to a file.
     */
    public void printEpicBlend(FileWriter writer) throws IOException {
        ArrayList<Song> copiedArray = new ArrayList<>(epicBlendList.array);
        HashMap<Integer,Integer> copiedHash = new HashMap<>(epicBlendList.songIDAndIndex);
        while (epicBlendList.size > 0) {
            if(epicBlendList.size==1)
                writer.write(epicBlendList.pop().ID+"");
            else
                writer.write(epicBlendList.pop().ID+" ");
        }
        writer.write("\n");
        writer.flush();
        epicBlendList.songIDAndIndex = copiedHash;
        epicBlendList.array = copiedArray;
        epicBlendList.size = copiedArray.size() - 1;
    }

    /**
     * The following three private methods return the peek elements from the given category
     * @return the highest level element from the AllSongsLists
     */
    private Song deletePeekBlissfulFromAllSongs(){
        return allBlissfulSongs.pop();

    }
    private Song deletePeekRoadTripFromAllSongs(){
        return allRoadTripSongs.pop();
    }
    private Song deletePeekHeartacheFromAllSongs(){
        return allHeartacheSongs.pop();
    }

    /**
     * Same songs should wait for entering the epic blend because of some reasons such as limitForPlaylist
     * Adds them to the waiting list according to their playlist.
     * key of the hash Maps is the playlist number, values are the binary heaps to hold the waitings.
     * @param song The song will wait
     * @param type The type represent in which category the song will wait
     */
    private void addToTheWaitingMap(Song song, String type){
        if (type.equals("blissful")){
            MaxHeapBlissful list = waitingForEpicBlissFul.getOrDefault(song.inWhichPlaylist,null);
            if (list == null)
                list = new MaxHeapBlissful();
            list.insert(song);
            song.isItWaitingBlissful = true;
            waitingForEpicBlissFul.put(song.inWhichPlaylist,list);
        }
        else if (type.equals("heartache")){
            MaxheapHeartache list = waitingForEpicHeartache.getOrDefault(song.inWhichPlaylist,null);
            if (list == null)
                list = new MaxheapHeartache();
            list.insert(song);
            song.isItWaitingHeartache = true;
            waitingForEpicHeartache.put(song.inWhichPlaylist,list);
        }
        else{
            MaxHeapRoadTrip list = waitingForEpicRoadTrip.getOrDefault(song.inWhichPlaylist,null);
            if (list == null)
                list = new MaxHeapRoadTrip();
            list.insert(song);
            song.isItWaitingRoadTrip = true;
            waitingForEpicRoadTrip.put(song.inWhichPlaylist,list);
        }
    }

    /**
     * Gives how many songs from the playlist Epic blend have
     * @param playlistID The playlist is to control
     * @param type The type represent in which sub epic blend.
     * @return the number
     */
    private int numberOfElementFromTheCurrentPlaylist(int playlistID,String type){
        if (type.equals("blissful")){
            MinHeapBlissful heap= epicBlendBlissfulHashMap.getOrDefault(playlistID,null);
            if (heap == null)
                return 0;
            return heap.size();
        }else if(type.equals("heartache")){
            MinHeapHeartache list = epicBlendHeartacheHashMap.getOrDefault(playlistID,null);
            if (list == null)
                return 0;
            return list.size();
        }else{
            MinHeapRoadTrip list = epicBlendRoadTripHashMap.getOrDefault(playlistID,null);
            if (list == null)
                return 0;
            return list.size();
        }
    }

    /**
     * I hold a hash map to hold the song in terms of their playlist for each category
     * because I will need to check the last element in the epic blend list from taking the given playlist
     * @param song will be added to hash map
     * @param type in which category
     */
    private void addToTheHashMap(Song song,String type){
        if (type.equals("blissful")){
            MinHeapBlissful list = epicBlendBlissfulHashMap.getOrDefault(song.inWhichPlaylist,null);
            if (list == null)
                list = new MinHeapBlissful();
            list.insert(song);
            epicBlendBlissfulHashMap.put(song.inWhichPlaylist,list);
        }else if(type.equals("heartache")){
            MinHeapHeartache list = epicBlendHeartacheHashMap.getOrDefault(song.inWhichPlaylist,null);
            if (list == null)
                list = new MinHeapHeartache();
            list.insert(song);
            epicBlendHeartacheHashMap.put(song.inWhichPlaylist,list);
        }else{
            MinHeapRoadTrip list = epicBlendRoadTripHashMap.getOrDefault(song.inWhichPlaylist,null);
            if (list == null)
                list = new MinHeapRoadTrip();
            list.insert(song);
            epicBlendRoadTripHashMap.put(song.inWhichPlaylist,list);
        }
    }

    /**
     * To take the smallest song in the epic blend from taking the given playlist
     * @param type is category
     * @return the smallest song
     */
    private Song getLastElementFromCurrentPlaylist(int playlistID, String type){
        switch (type) {
            case "heartache" -> {
                MinHeapHeartache heap = epicBlendHeartacheHashMap.get(playlistID);
                return heap.peek();
            }
            case "roadTrip" -> {
                MinHeapRoadTrip heap = epicBlendRoadTripHashMap.get(playlistID);
                return heap.peek();
            }
            case "blissful" -> {
                MinHeapBlissful heap = epicBlendBlissfulHashMap.get(playlistID);
                return heap.peek();
            }
        }
        return null;
    }
    /**
     * Adds a song to the EpicBlend, performing necessary adjustments and checks based on the song's attributes and
     * the current state of the blend.
     *
     * @param song   The song to be added to the blend.
     */
    public void add(Song song, FileWriter writer) throws IOException {
        int[] output1 = addToTheHeartache(song);
        int[] output2 = addToTheRoadTrip(song);
        int[] output3 = addToTheBlissful(song);
        if ((song.isItInEpicBlissful || song.isItInEpicHeartache || song.isItInEpicRoadTrip) && !song.isItInEpicList){
            epicBlendList.insert(song);
            song.isItInEpicList = true;
        }
        writer.write(output1[0]+" "+output2[0]+" "+ output3[0] +"\n");
        writer.write(output1[1] + " "+output2[1] +" "+ output3[1] + "\n");
        writer.flush();
    }
    /**
     * Removes a song from the EpicBlend, updating the blend accordingly.
     *
     * @param songToRemove The song to be removed from the blend.
     */
    public void remove(Song songToRemove,FileWriter writer) throws IOException {
        int[] output1 = removeFromTheHeartache(songToRemove);
        int[] output2 = removeFromTheRoadTrip(songToRemove);
        int[] output3 = removeFromTheBlissful(songToRemove);
        if (songToRemove.isItInEpicList) {
            epicBlendList.delete(songToRemove);
            songToRemove.isItInEpicList=false;
        }
        songToRemove.inWhichPlaylist=-1;
        writer.write(output1[0]+" "+output2[0]+" "+ output3[0] +"\n");
        writer.write(output1[1] + " "+output2[1] +" "+ output3[1] + "\n");
        writer.flush();
    }

    /**
     * add a new song to the epic blend of the given category
     * @param addedSong is new song for the epic blend
     * @param type represent the category
     */
    private void addToTheEpicBlend(Song addedSong, String type){
        if(type.equals("heartache")){
            heartacheForEpicBlend.insert(addedSong);
            heartacheWithMinHeap.insert(addedSong);
            addedSong.isItInEpicHeartache=true;
            if (!addedSong.isItInEpicList)
                epicBlendList.insert(addedSong);
            addedSong.isItInEpicList = true;
            addToTheHashMap(addedSong,"heartache");
        }
        if (type.equals("roadTrip")){
            roadTripForEpicBlend.insert(addedSong);
            roadTripWithMinHeap.insert(addedSong);
            addedSong.isItInEpicRoadTrip=true;
            if (!addedSong.isItInEpicList)
                epicBlendList.insert(addedSong);
            addedSong.isItInEpicList = true;
            addToTheHashMap(addedSong,"roadTrip");
        }
        if(type.equals("blissful")){
            blissfulForEpicBlend.insert(addedSong);
            blissfulWithMinHeap.insert(addedSong);
            addedSong.isItInEpicBlissful=true;
            if (!addedSong.isItInEpicList)
                epicBlendList.insert(addedSong);
            addedSong.isItInEpicList = true;
            addToTheHashMap(addedSong,"blissful");
        }
    }
    /**
     * removes from the epic blend of the given category
     * @param songToRemove will be removed from the epic blend
     * @param type represent the category
     */
    private void removeFromTheEpicBlend(Song songToRemove, String type){
        if(type.equals("heartache")){
            epicBlendHeartacheHashMap.get(songToRemove.inWhichPlaylist).delete(songToRemove);
            heartacheForEpicBlend.delete(songToRemove);
            heartacheWithMinHeap.delete(songToRemove);
            songToRemove.isItInEpicHeartache = false;
            if (!songToRemove.isItInEpicBlissful && !songToRemove.isItInEpicRoadTrip && songToRemove.isItInEpicList){
                epicBlendList.delete(songToRemove);
                songToRemove.isItInEpicList = false;
            }
        }
        if (type.equals("roadTrip")){
            epicBlendRoadTripHashMap.get(songToRemove.inWhichPlaylist).delete(songToRemove);
            roadTripForEpicBlend.delete(songToRemove);
            roadTripWithMinHeap.delete(songToRemove);
            songToRemove.isItInEpicRoadTrip = false;
            if (!songToRemove.isItInEpicBlissful && !songToRemove.isItInEpicHeartache && songToRemove.isItInEpicList){
                epicBlendList.delete(songToRemove);
                songToRemove.isItInEpicList = false;
            }
        }
        if(type.equals("blissful")){
            epicBlendBlissfulHashMap.get(songToRemove.inWhichPlaylist).delete(songToRemove);
            blissfulForEpicBlend.delete(songToRemove);
            blissfulWithMinHeap.delete(songToRemove);
            songToRemove.isItInEpicBlissful = false;
            if (!songToRemove.isItInEpicRoadTrip && !songToRemove.isItInEpicHeartache && songToRemove.isItInEpicList){
                epicBlendList.delete(songToRemove);
                songToRemove.isItInEpicList = false;
            }
        }
    }
    private int[] addToTheHeartache(Song song){
        int[] output = new int[2];
        int add1=0; int remove1=0;
        int playlistForNewSong = song.inWhichPlaylist;
        int numElement = numberOfElementFromTheCurrentPlaylist(playlistForNewSong,"heartache");
        if(heartacheForEpicBlend.size()<limitForHeartache){
            if(numElement<limitForPlaylist){
                song.isItInEpicHeartache = true;
                heartacheForEpicBlend.insert(song);
                heartacheWithMinHeap.insert(song);
                addToTheHashMap(song,"heartache");
                add1 = song.ID;
            }else{
                Song smallestElement = getLastElementFromCurrentPlaylist(playlistForNewSong,"heartache");
                assert smallestElement != null;
                if (smallestElement.heartacheScore<=song.heartacheScore){
                    if (smallestElement.heartacheScore == song.heartacheScore){
                        if(smallestElement.name.compareToIgnoreCase(song.name) > 0){
                            removeFromTheEpicBlend(smallestElement,"heartache");
                            remove1 = smallestElement.ID;
                            addToAllSongsHeartache(smallestElement);
                            addToTheEpicBlend(song,"heartache");
                            add1 = song.ID;
                        }else{
                            addToAllSongsHeartache(song);
                        }
                    }else{
                        removeFromTheEpicBlend(smallestElement,"heartache");
                        remove1 = smallestElement.ID;
                        addToAllSongsHeartache(smallestElement);
                        addToTheEpicBlend(song,"heartache");
                        add1 = song.ID;
                    }
                }else{
                    addToAllSongsHeartache(song);
                }
            }
        }else{
            Song smallestElementInEpic = heartacheWithMinHeap.peek();
            if (song.heartacheScore > smallestElementInEpic.heartacheScore) {
                if (song.inWhichPlaylist == smallestElementInEpic.inWhichPlaylist || numElement < limitForPlaylist) {
                    // remove last element and add newSong
                    removeFromTheEpicBlend(smallestElementInEpic,"heartache");
                    remove1 = smallestElementInEpic.ID;
                    addToAllSongsHeartache(smallestElementInEpic);
                    addToTheEpicBlend(song,"heartache");
                    add1 = song.ID;
                } else {
                    Song smallestElement = getLastElementFromCurrentPlaylist(playlistForNewSong,"heartache");
                    assert smallestElement != null;
                    if (smallestElement.heartacheScore<=song.heartacheScore){
                        if (smallestElement.heartacheScore == song.heartacheScore){
                            if(smallestElement.name.compareToIgnoreCase(song.name) > 0){
                                removeFromTheEpicBlend(smallestElement,"heartache");
                                remove1 = smallestElement.ID;
                                addToAllSongsHeartache(smallestElement);
                                addToTheEpicBlend(song,"heartache");
                                add1 = song.ID;
                            }else{
                                addToAllSongsHeartache(song);
                            }
                        }else{
                            removeFromTheEpicBlend(smallestElement,"heartache");
                            remove1 = smallestElement.ID;
                            addToAllSongsHeartache(smallestElement);
                            addToTheEpicBlend(song,"heartache");
                            add1 = song.ID;
                        }
                    }else{
                        addToAllSongsHeartache(song);
                    }
                }
            } else if(song.heartacheScore < smallestElementInEpic.heartacheScore){
                addToAllSongsHeartache(song);
            }else{
                if (song.inWhichPlaylist == smallestElementInEpic.inWhichPlaylist || numElement < limitForPlaylist) {
                    if (smallestElementInEpic.name.compareToIgnoreCase(song.name) >= 0) {
                        removeFromTheEpicBlend(smallestElementInEpic, "heartache");
                        remove1 = smallestElementInEpic.ID;
                        addToAllSongsHeartache(smallestElementInEpic);
                        addToTheEpicBlend(song, "heartache");
                        add1 = song.ID;
                    } else {
                        addToAllSongsHeartache(song);
                    }
                }else{
                    addToAllSongsHeartache(song);
                }
            }
        }
        output[0]=add1; output[1]=remove1;
        return output;
    }
    private int[] addToTheRoadTrip(Song song){
        int[] output = new int[2];
        int add2=0; int remove2=0;
        int playlistForNewSong = song.inWhichPlaylist;
        int numElement =numberOfElementFromTheCurrentPlaylist(playlistForNewSong,"roadTrip");
        if(roadTripForEpicBlend.size()<limitForRoadTrip){
            if(numElement<limitForPlaylist){
                song.isItInEpicRoadTrip = true;
                roadTripForEpicBlend.insert(song);
                roadTripWithMinHeap.insert(song);
                addToTheHashMap(song,"roadTrip");
                add2 = song.ID;
            }else{
                Song smallestElement = getLastElementFromCurrentPlaylist(playlistForNewSong,"roadTrip");
                assert smallestElement != null;
                if (smallestElement.roadTripScore<=song.roadTripScore){
                    if (smallestElement.roadTripScore == song.roadTripScore){
                        if(smallestElement.name.compareToIgnoreCase(song.name) > 0){
                            removeFromTheEpicBlend(smallestElement,"roadTrip");
                            remove2 = smallestElement.ID;
                            addToAllSongsRoadTrip(smallestElement);
                            addToTheEpicBlend(song,"roadTrip");
                            add2 =song.ID;
                        }else{
                            addToAllSongsRoadTrip(song);
                        }
                    }else{
                        removeFromTheEpicBlend(smallestElement,"roadTrip");
                        remove2 = smallestElement.ID;
                        addToAllSongsRoadTrip(smallestElement);
                        addToTheEpicBlend(song,"roadTrip");
                        add2 = song.ID;
                    }
                }else{
                    addToAllSongsRoadTrip(song);
                }
            }
        }else{
            Song smallestElementInEpic = roadTripWithMinHeap.peek();
            if (song.roadTripScore > smallestElementInEpic.roadTripScore) {
                if (song.inWhichPlaylist == smallestElementInEpic.inWhichPlaylist || numElement < limitForPlaylist) {
                    // remove last element and add newSong
                    removeFromTheEpicBlend(smallestElementInEpic,"roadTrip");
                    remove2 = smallestElementInEpic.ID;
                    addToAllSongsRoadTrip(smallestElementInEpic);
                    addToTheEpicBlend(song,"roadTrip");
                    add2 = song.ID;
                } else {
                    Song smallestElement = getLastElementFromCurrentPlaylist(playlistForNewSong,"roadTrip");
                    assert smallestElement != null;
                    if (smallestElement.roadTripScore<=song.roadTripScore){
                        if (smallestElement.roadTripScore == song.roadTripScore){
                            if(smallestElement.name.compareToIgnoreCase(song.name) > 0){
                                removeFromTheEpicBlend(smallestElement,"roadTrip");
                                remove2 = smallestElement.ID;
                                addToAllSongsRoadTrip(smallestElement);
                                addToTheEpicBlend(song,"roadTrip");
                                add2 = song.ID;
                            }else{
                                addToAllSongsRoadTrip(song);
                            }
                        }else{
                            removeFromTheEpicBlend(smallestElement,"roadTrip");
                            remove2 = smallestElement.ID;
                            addToAllSongsRoadTrip(smallestElement);
                            addToTheEpicBlend(song,"roadTrip");
                            add2 = song.ID;
                        }
                    }else{
                        addToAllSongsRoadTrip(song);
                    }
                }
            } else if(song.roadTripScore < smallestElementInEpic.roadTripScore){
                addToAllSongsRoadTrip(song);
            }else{
                if (song.inWhichPlaylist == smallestElementInEpic.inWhichPlaylist || numElement < limitForPlaylist) {
                    if (smallestElementInEpic.name.compareToIgnoreCase(song.name) > 0) {
                        removeFromTheEpicBlend(smallestElementInEpic, "roadTrip");
                        remove2 = smallestElementInEpic.ID;
                        addToAllSongsRoadTrip(smallestElementInEpic);
                        addToTheEpicBlend(song, "roadTrip");
                        add2 = song.ID;
                    } else {
                        addToAllSongsRoadTrip(song);
                    }
                }else{
                    addToAllSongsRoadTrip(song);
                }
            }

        }
        output[0]=add2; output[1]=remove2;
        return output;
    }
    private int[] addToTheBlissful(Song song){
        int[] output = new int[2];
        int add3 = 0; int remove3=0;
        int playlistForNewSong = song.inWhichPlaylist;
        int numElement = numberOfElementFromTheCurrentPlaylist(playlistForNewSong,"blissful");
        if(blissfulForEpicBlend.size()<limitForBlissful){
            if(numElement<limitForPlaylist){
                song.isItInEpicBlissful = true;
                blissfulForEpicBlend.insert(song);
                blissfulWithMinHeap.insert(song);
                addToTheHashMap(song,"blissful");
                add3 = song.ID;
            }else{
                Song smallestElement = getLastElementFromCurrentPlaylist(playlistForNewSong,"blissful");
                assert smallestElement != null;
                if (smallestElement.blissfulScore<=song.blissfulScore){
                    if (smallestElement.blissfulScore == song.blissfulScore){
                        if(smallestElement.name.compareToIgnoreCase(song.name) > 0){
                            removeFromTheEpicBlend(smallestElement,"blissful");
                            remove3 = smallestElement.ID;
                            addToAllSongsBlissful(smallestElement);
                            addToTheEpicBlend(song,"blissful");
                            add3 = song.ID;
                        }else{
                            addToAllSongsBlissful(song);
                        }
                    }else{
                        removeFromTheEpicBlend(smallestElement,"blissful");
                        remove3 = smallestElement.ID;
                        addToAllSongsBlissful(smallestElement);
                        addToTheEpicBlend(song,"blissful");
                        add3 = song.ID;
                    }
                }else{
                    addToAllSongsBlissful(song);
                }
            }
        }else{
            Song smallestElementInEpic = blissfulWithMinHeap.peek();
            if (song.blissfulScore > smallestElementInEpic.blissfulScore) {
                if (song.inWhichPlaylist == smallestElementInEpic.inWhichPlaylist || numElement < limitForPlaylist) {
                    // remove last element and add newSong
                    removeFromTheEpicBlend(smallestElementInEpic,"blissful");
                    remove3 = smallestElementInEpic.ID;
                    addToAllSongsBlissful(smallestElementInEpic);
                    addToTheEpicBlend(song,"blissful");
                    add3 = song.ID;
                } else {
                    Song smallestElement = getLastElementFromCurrentPlaylist(playlistForNewSong,"blissful");
                    assert smallestElement != null;
                    if (smallestElement.blissfulScore<=song.blissfulScore){
                        if (smallestElement.blissfulScore == song.blissfulScore){
                            if(smallestElement.name.compareToIgnoreCase(song.name) > 0){
                                removeFromTheEpicBlend(smallestElement,"blissful");
                                remove3 = smallestElement.ID;
                                addToAllSongsBlissful(smallestElement);
                                addToTheEpicBlend(song,"blissful");
                                add3 = song.ID;
                            }else{
                                addToAllSongsBlissful(song);
                            }
                        }else{
                            removeFromTheEpicBlend(smallestElement,"blissful");
                            remove3 = smallestElement.ID;
                            addToAllSongsBlissful(smallestElement);
                            addToTheEpicBlend(song,"blissful");
                            add3 = song.ID;
                        }
                    }else{
                        addToAllSongsBlissful(song);
                    }
                }
            } else if(song.blissfulScore < smallestElementInEpic.blissfulScore){
                addToAllSongsBlissful(song);
            }else{
                if (song.inWhichPlaylist == smallestElementInEpic.inWhichPlaylist || numElement < limitForPlaylist) {
                    if (smallestElementInEpic.name.compareToIgnoreCase(song.name) > 0) {
                        removeFromTheEpicBlend(smallestElementInEpic, "blissful");
                        remove3 = smallestElementInEpic.ID;
                        addToAllSongsBlissful(smallestElementInEpic);
                        addToTheEpicBlend(song, "blissful");
                        add3 = song.ID;
                    } else {
                        addToAllSongsBlissful(song);
                    }
                }else{
                    addToAllSongsBlissful(song);
                }
            }
        }
        output[0]=add3; output[1]=remove3;
        return output;
    }
    private int[] removeFromTheHeartache(Song songToRemove){
        int[] output=new int[2];
        int add=0; int remove =0;
        if (songToRemove.isItInEpicHeartache){
            MaxheapHeartache waitingList = waitingForEpicHeartache.getOrDefault(songToRemove.inWhichPlaylist,null);
            removeFromTheEpicBlend(songToRemove,"heartache");
            remove=songToRemove.ID;
            if(waitingList==null || waitingList.size==0){
                while (heartacheForEpicBlend.size() < limitForHeartache && allHeartacheSongs.size>0) {
                    Song song = deletePeekHeartacheFromAllSongs();
                    int numberOfElement = numberOfElementFromTheCurrentPlaylist(song.inWhichPlaylist, "heartache");
                    if (numberOfElement >= limitForPlaylist) {
                        addToTheWaitingMap(song, "heartache");
                        continue;
                    }
                    addToTheEpicBlend(song,"heartache");
                    add=song.ID;
                }
            }else{
                if(allHeartacheSongs.size>0){
                    if(allHeartacheSongs.peek().heartacheScore<waitingList.peek().heartacheScore){
                        Song newSong = waitingList.pop();
                        newSong.isItWaitingHeartache = false;
                        addToTheEpicBlend(newSong,"heartache");
                        add=newSong.ID;
                    }else{
                        if(allHeartacheSongs.peek().heartacheScore==waitingList.peek().heartacheScore){
                            if(allHeartacheSongs.peek().name.compareToIgnoreCase(waitingList.peek().name)<0){
                                Song newElement = deletePeekHeartacheFromAllSongs();
                                while (true) {
                                    if (newElement.heartacheScore <= waitingList.peek().heartacheScore) {
                                        if(newElement.heartacheScore==waitingList.peek().heartacheScore){
                                            if(newElement.name.compareToIgnoreCase(waitingList.peek().name)>0){
                                                allHeartacheSongs.insert(newElement);
                                                newElement = waitingList.pop();
                                                newElement.isItWaitingHeartache = false;
                                                break;
                                            }
                                        }else {
                                            allHeartacheSongs.insert(newElement);
                                            newElement = waitingList.pop();
                                            newElement.isItWaitingHeartache = false;
                                            break;
                                        }
                                    }
                                    int numberOfElement = numberOfElementFromTheCurrentPlaylist(newElement.inWhichPlaylist, "heartache");
                                    if (numberOfElement >= limitForPlaylist) {
                                        addToTheWaitingMap(newElement, "heartache");
                                        if (allHeartacheSongs.size() > 0) {
                                            newElement = deletePeekHeartacheFromAllSongs();
                                        } else {
                                            newElement = waitingList.pop();
                                            newElement.isItWaitingHeartache = false;
                                            break;
                                        }
                                        continue;
                                    }
                                    break;
                                }
                                addToTheEpicBlend(newElement,"heartache");
                                add=newElement.ID;
                            }else{
                                Song newSong = waitingList.pop();
                                newSong.isItWaitingHeartache = false;
                                addToTheEpicBlend(newSong,"heartache");
                                add=newSong.ID;
                            }
                        }else{
                            Song newElement = deletePeekHeartacheFromAllSongs();
                            while (true) {
                                if (newElement.heartacheScore <= waitingList.peek().heartacheScore) {
                                    if(newElement.heartacheScore==waitingList.peek().heartacheScore){
                                        if(newElement.name.compareToIgnoreCase(waitingList.peek().name)>0){
                                            allHeartacheSongs.insert(newElement);
                                            newElement = waitingList.pop();
                                            newElement.isItWaitingHeartache = false;
                                            break;
                                        }
                                    }else {
                                        allHeartacheSongs.insert(newElement);
                                        newElement = waitingList.pop();
                                        newElement.isItWaitingHeartache = false;
                                        break;
                                    }
                                }
                                int numberOfElement = numberOfElementFromTheCurrentPlaylist(newElement.inWhichPlaylist, "heartache");
                                if (numberOfElement >= limitForPlaylist) {
                                    addToTheWaitingMap(newElement, "heartache");
                                    if (allHeartacheSongs.size() > 0) {
                                        newElement = deletePeekHeartacheFromAllSongs();
                                    } else {
                                        newElement = waitingList.pop();
                                        newElement.isItWaitingHeartache = false;
                                        break;
                                    }
                                    continue;
                                }
                                break;
                            }
                            addToTheEpicBlend(newElement,"heartache");
                            add=newElement.ID;
                        }
                    }
                }else{
                    Song newSong = waitingList.pop();
                    newSong.isItWaitingHeartache = false;
                    addToTheEpicBlend(newSong,"heartache");
                    add=newSong.ID;
                }
            }
        }else{
            if (songToRemove.isItWaitingHeartache) {
                waitingForEpicHeartache.get(songToRemove.inWhichPlaylist).delete(songToRemove);
                songToRemove.isItWaitingHeartache=false;
            }else{
                allHeartacheSongs.delete(songToRemove);
            }
        }
        output[0]=add; output[1]=remove;
        return output;
    }
    private  int[] removeFromTheRoadTrip(Song songToRemove){
        int[] output=new int[2];
        int add=0; int remove =0;
        if (songToRemove.isItInEpicRoadTrip){
            MaxHeapRoadTrip waitingList = waitingForEpicRoadTrip.getOrDefault(songToRemove.inWhichPlaylist,null);
            removeFromTheEpicBlend(songToRemove,"roadTrip");
            remove=songToRemove.ID;
            if(waitingList==null || waitingList.size==0){
                while (roadTripForEpicBlend.size() < limitForRoadTrip && allRoadTripSongs.size>0) {
                    Song song = deletePeekRoadTripFromAllSongs();
                    int numberOfElement = numberOfElementFromTheCurrentPlaylist(song.inWhichPlaylist, "roadTrip");
                    if (numberOfElement >= limitForPlaylist) {
                        addToTheWaitingMap(song, "roadTrip");
                        continue;
                    }
                    addToTheEpicBlend(song,"roadTrip");
                    add=song.ID;
                }
            }else{
                if(allRoadTripSongs.size>0){
                    if(allRoadTripSongs.peek().roadTripScore<waitingList.peek().roadTripScore){
                        Song newSong = waitingList.pop();
                        newSong.isItWaitingRoadTrip = false;
                        addToTheEpicBlend(newSong,"roadTrip");
                        add=newSong.ID;
                    }else{
                        if(allRoadTripSongs.peek().roadTripScore==waitingList.peek().roadTripScore){
                            if(allRoadTripSongs.peek().name.compareToIgnoreCase(waitingList.peek().name)<0){
                                Song newElement = deletePeekRoadTripFromAllSongs();
                                while (true) {
                                    if (newElement.roadTripScore <= waitingList.peek().roadTripScore) {
                                        if (newElement.roadTripScore == waitingList.peek().roadTripScore) {
                                            if (newElement.name.compareToIgnoreCase(waitingList.peek().name) > 0) {
                                                allRoadTripSongs.insert(newElement);
                                                newElement = waitingList.pop();
                                                newElement.isItWaitingRoadTrip = false;
                                                break;
                                            }
                                        } else {
                                            allRoadTripSongs.insert(newElement);
                                            newElement = waitingList.pop();
                                            newElement.isItWaitingRoadTrip = false;
                                            break;
                                        }
                                    }
                                    int numberOfElement = numberOfElementFromTheCurrentPlaylist(newElement.inWhichPlaylist, "roadTrip");
                                    if (numberOfElement >= limitForPlaylist) {
                                        addToTheWaitingMap(newElement, "roadTrip");
                                        if (allRoadTripSongs.size() > 0) {
                                            newElement = deletePeekRoadTripFromAllSongs();
                                        } else {
                                            newElement = waitingList.pop();
                                            newElement.isItWaitingRoadTrip = false;
                                            break;
                                        }
                                        continue;
                                    }
                                    break;
                                }
                                addToTheEpicBlend(newElement,"roadTrip");
                                add=newElement.ID;
                            }else{
                                Song newSong = waitingList.pop();
                                newSong.isItWaitingRoadTrip = false;
                                addToTheEpicBlend(newSong,"roadTrip");
                                add=newSong.ID;
                            }
                        }else{
                            Song newElement = deletePeekRoadTripFromAllSongs();
                            while (true) {
                                if (newElement.roadTripScore <= waitingList.peek().roadTripScore) {
                                    if(newElement.roadTripScore==waitingList.peek().roadTripScore){
                                        if(newElement.name.compareToIgnoreCase(waitingList.peek().name)>0){
                                            allRoadTripSongs.insert(newElement);
                                            newElement = waitingList.pop();
                                            newElement.isItWaitingRoadTrip = false;
                                            break;
                                        }
                                    }else {
                                        allRoadTripSongs.insert(newElement);
                                        newElement = waitingList.pop();
                                        newElement.isItWaitingRoadTrip = false;
                                        break;
                                    }
                                }
                                int numberOfElement = numberOfElementFromTheCurrentPlaylist(newElement.inWhichPlaylist, "roadTrip");
                                if (numberOfElement >= limitForPlaylist) {
                                    addToTheWaitingMap(newElement, "roadTrip");
                                    if (allRoadTripSongs.size() > 0) {
                                        newElement = deletePeekRoadTripFromAllSongs();
                                    } else {
                                        newElement = waitingList.pop();
                                        newElement.isItWaitingRoadTrip = false;
                                        break;
                                    }
                                    continue;
                                }
                                break;
                            }
                            addToTheEpicBlend(newElement,"roadTrip");
                            add=newElement.ID;
                        }
                    }
                }else{
                    Song newSong = waitingList.pop();
                    newSong.isItWaitingRoadTrip = false;
                    addToTheEpicBlend(newSong,"roadTrip");
                    add=newSong.ID;
                }
            }
        }else{
            if (songToRemove.isItWaitingRoadTrip) {
                waitingForEpicRoadTrip.get(songToRemove.inWhichPlaylist).delete(songToRemove);
                songToRemove.isItWaitingRoadTrip=false;
            }else{
                allRoadTripSongs.delete(songToRemove);
            }
        }
        output[0]=add; output[1]=remove;
        return output;
    }
    private int[] removeFromTheBlissful(Song songToRemove){
        int[] output=new int[2];
        int add=0; int remove =0;
        if (songToRemove.isItInEpicBlissful){
            MaxHeapBlissful waitingList = waitingForEpicBlissFul.getOrDefault(songToRemove.inWhichPlaylist,null);
            removeFromTheEpicBlend(songToRemove,"blissful");
            remove=songToRemove.ID;
            if(waitingList==null || waitingList.size==0){
                while (blissfulForEpicBlend.size() < limitForBlissful && allBlissfulSongs.size>0) {
                    Song song = deletePeekBlissfulFromAllSongs();
                    int numberOfElement = numberOfElementFromTheCurrentPlaylist(song.inWhichPlaylist, "blissful");
                    if (numberOfElement >= limitForPlaylist) {
                        addToTheWaitingMap(song, "blissful");
                        continue;
                    }
                    addToTheEpicBlend(song,"blissful");
                    add=song.ID;
                }
            }else{
                if(allBlissfulSongs.size>0){
                    if(allBlissfulSongs.peek().blissfulScore<waitingList.peek().blissfulScore){
                        Song newSong = waitingList.pop();
                        newSong.isItWaitingBlissful = false;
                        addToTheEpicBlend(newSong,"blissful");
                        add=newSong.ID;
                    }else{
                        if(allBlissfulSongs.peek().blissfulScore==waitingList.peek().blissfulScore){
                            if(allBlissfulSongs.peek().name.compareToIgnoreCase(waitingList.peek().name)<0){
                                Song newElement = deletePeekBlissfulFromAllSongs();
                                while (true) {
                                    if (newElement.blissfulScore <= waitingList.peek().blissfulScore) {
                                        if(newElement.blissfulScore==waitingList.peek().blissfulScore){
                                            if(newElement.name.compareToIgnoreCase(waitingList.peek().name)>0){
                                                allBlissfulSongs.insert(newElement);
                                                newElement = waitingList.pop();
                                                newElement.isItWaitingBlissful = false;
                                                break;
                                            }
                                        }else {
                                            allBlissfulSongs.insert(newElement);
                                            newElement = waitingList.pop();
                                            newElement.isItWaitingBlissful = false;
                                            break;
                                        }
                                    }
                                    int numberOfElement = numberOfElementFromTheCurrentPlaylist(newElement.inWhichPlaylist, "blissful");
                                    if (numberOfElement >= limitForPlaylist) {
                                        addToTheWaitingMap(newElement, "blissful");
                                        if (allBlissfulSongs.size() > 0) {
                                            newElement = deletePeekBlissfulFromAllSongs();
                                        } else {
                                            newElement = waitingList.pop();
                                            newElement.isItWaitingBlissful = false;
                                            break;
                                        }
                                        continue;
                                    }
                                    break;
                                }
                                addToTheEpicBlend(newElement,"blissful");
                                add=newElement.ID;
                            }else{
                                Song newSong = waitingList.pop();
                                newSong.isItWaitingBlissful = false;
                                addToTheEpicBlend(newSong,"blissful");
                                add=newSong.ID;
                            }
                        }else{
                            Song newElement = deletePeekBlissfulFromAllSongs();
                            while (true) {
                                if (newElement.blissfulScore <= waitingList.peek().blissfulScore) {
                                    if(newElement.blissfulScore==waitingList.peek().blissfulScore){
                                        if(newElement.name.compareToIgnoreCase(waitingList.peek().name)>0){
                                            allBlissfulSongs.insert(newElement);
                                            newElement = waitingList.pop();
                                            newElement.isItWaitingBlissful = false;
                                            break;
                                        }
                                    }else {
                                        allBlissfulSongs.insert(newElement);
                                        newElement = waitingList.pop();
                                        newElement.isItWaitingBlissful = false;
                                        break;
                                    }
                                }
                                int numberOfElement = numberOfElementFromTheCurrentPlaylist(newElement.inWhichPlaylist, "blissful");
                                if (numberOfElement >= limitForPlaylist) {
                                    addToTheWaitingMap(newElement, "blissful");
                                    if (allBlissfulSongs.size() > 0) {
                                        newElement = deletePeekBlissfulFromAllSongs();
                                    } else {
                                        newElement = waitingList.pop();
                                        newElement.isItWaitingBlissful = false;
                                        break;
                                    }
                                    continue;
                                }
                                break;
                            }
                            addToTheEpicBlend(newElement,"blissful");
                            add=newElement.ID;
                        }
                    }
                }else{
                    Song newSong = waitingList.pop();
                    newSong.isItWaitingBlissful = false;
                    addToTheEpicBlend(newSong,"blissful");
                    add=newSong.ID;
                }
            }
        }else{
            if (songToRemove.isItWaitingBlissful) {
                waitingForEpicBlissFul.get(songToRemove.inWhichPlaylist).delete(songToRemove);
                songToRemove.isItWaitingBlissful=false;
            }else{
                allBlissfulSongs.delete(songToRemove);
            }
        }
        output[0]=add; output[1]=remove;
        return output;
    }
}
