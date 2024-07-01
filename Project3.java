/**
 * The Project3 class is responsible for managing a collection of songs and playlists.
 * It reads song data and playlist criteria from input files, processes various operations
 * like adding and removing songs from playlists, and outputs the final playlists to a file.
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Project3 {
    /**
     * The main method reads song and playlist data from files, processes the data,
     * and executes operations based on the input.
     *
     * @param args Command line arguments where:
     *             args[0] is the path to the file containing song data,
     *             args[1] is the path to the file containing playlist criteria and operations,
     *             args[2] is the path to the output file where results are written.
     * @throws IOException If there is an issue with file reading or writing.
     */
    public static void main(String[] args) throws IOException {
        File songFile = new File(args[0]);
        FileWriter writer = new FileWriter(args[2]);
        Scanner reading = new Scanner(songFile);
        int numberOfSongs = Integer.parseInt(reading.nextLine().split(" ")[0]);
        HashMap<Integer, Song> songs = new HashMap<>();
        while (reading.hasNextLine()) {
            String[] lineList = reading.nextLine().split(" ");
            int ID = Integer.parseInt(lineList[0]);
            String songName = lineList[1];
            int count = Integer.parseInt(lineList[2]);
            int heartache = Integer.parseInt(lineList[3]);
            int roadTrip = Integer.parseInt(lineList[4]);
            int blissful = Integer.parseInt(lineList[5]);
            Song song = new Song(ID, songName, count, heartache, roadTrip, blissful);
            songs.put(ID, song);
        }
        File testCaseFile = new File(args[1]);
        Scanner input = new Scanner(testCaseFile);
        String[] listOfLine = input.nextLine().split(" ");
        int limitForPlaylist = Integer.parseInt(listOfLine[0]);
        int limitForHeartache = Integer.parseInt(listOfLine[1]);
        int limitForRoadTrip = Integer.parseInt(listOfLine[2]);
        int limitForBlissful = Integer.parseInt(listOfLine[3]);
        int numberOfPlaylist = Integer.parseInt(input.nextLine());
        EpicBlend epicBlend = new EpicBlend(limitForPlaylist, limitForBlissful, limitForHeartache, limitForRoadTrip);
        for (int i = 0; i < numberOfPlaylist; i++) {
            String[] information = input.nextLine().split(" ");
            int numberOfSongsInThePlaylist = Integer.parseInt(information[1]);
            String[] song = input.nextLine().split(" ");
            for (int j = 0; j < numberOfSongsInThePlaylist; j++) {
                Song additionSong = songs.get(Integer.parseInt(song[j]));
                additionSong.inWhichPlaylist = Integer.parseInt(information[0]);
                epicBlend.addToAllSongsList(additionSong);
            }
        }
        epicBlend.createEpicBlend();
        int numberOfOperation = Integer.parseInt(input.nextLine());
        for (int i = 0; i < numberOfOperation; i++) {
            String[] lineList = input.nextLine().split(" ");
            String operationName = lineList[0];
            if (operationName.equals("ADD")) {
                Song addedSong = songs.get(Integer.parseInt(lineList[1]));
                addedSong.inWhichPlaylist = Integer.parseInt(lineList[2]);
                epicBlend.add(addedSong,writer);
            } else if (operationName.equals("REM")) {
                Song removalSong = songs.get(Integer.parseInt(lineList[1]));
                epicBlend.remove(removalSong,writer);
            } else if (operationName.equals("ASK"))
                epicBlend.printEpicBlend(writer);
        }
    }
}