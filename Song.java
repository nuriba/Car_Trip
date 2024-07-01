/**
 * The Song class represents a song with various attributes such as ID, name, and scores related to different categories
 * like heartache, road trip, and blissful. It also contains information about the song's presence in various playlists.
 */
public class Song {
    public int ID;
    public String name;
    public int countNumber;
    public int heartacheScore;
    public int roadTripScore;
    public int blissfulScore;
    public int inWhichPlaylist = -1;
    public boolean isItInEpicHeartache = false;
    public boolean isItInEpicBlissful = false;
    public boolean isItInEpicRoadTrip = false;
    public boolean isItInEpicList = false;
    public boolean isItWaitingBlissful = false;
    public boolean isItWaitingHeartache = false;
    public boolean isItWaitingRoadTrip = false;
    /**
     * Constructor to initialize a Song object with given attributes.
     *
     * @param ID             The unique identifier of the song.
     * @param name           The name of the song.
     * @param countNumber    The count number associated with the song.
     * @param heartacheScore The score of the song in the heartache category.
     * @param roadTripScore  The score of the song in the road trip category.
     * @param blissfulScore  The score of the song in the blissful category.
     */
    Song(int ID, String name, int countNumber, int heartacheScore, int roadTripScore, int blissfulScore) {
        this.ID = ID;
        this.name = name;
        this.countNumber = countNumber;
        this.heartacheScore = heartacheScore;
        this.roadTripScore = roadTripScore;
        this.blissfulScore = blissfulScore;
    }
    /**
     * Resets the song's status in playlists and categories, setting all related flags to their default values.
     */
    public void resetTheSong() {
        inWhichPlaylist = -1;
        isItInEpicList = false;
        isItInEpicHeartache = false;
        isItInEpicBlissful = false;
        isItInEpicRoadTrip = false;
        isItWaitingBlissful = false;
        isItWaitingHeartache = false;
        isItWaitingRoadTrip = false;
    }
}

