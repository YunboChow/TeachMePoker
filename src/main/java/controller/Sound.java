package controller;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Random;

/**
 * Sound class containing all the sounds that the program uses.
 * 
 * @author Lykke Levin
 * @version 1.1
 * @author Anthon Haväng, Erik Larsson, Jens Bjerre
 * @version 1.2
 *
 */
public class Sound {
	private static Media m = new Media(Sound.class.getResource("/sounds/cool_struttin'.mp3").toString());
	public static MediaPlayer mp = new MediaPlayer(m);
	public static AudioClip checkSound = new AudioClip(Sound.class.getResource("/sounds/checkMeSound.m4a").toString());
	public static AudioClip shuffleSound = new AudioClip(Sound.class.getResource("/sounds/cardShuffle.wav").toString());
	public static AudioClip singleCard = new AudioClip(Sound.class.getResource("/sounds/cardSlide8.wav").toString());
	public static AudioClip cardFold = new AudioClip(Sound.class.getResource("/sounds/cardShove3.wav").toString());
	public static AudioClip chipSingle = new AudioClip(Sound.class.getResource("/sounds/chipsStacksSingle.wav").toString());
	public static AudioClip chipMulti = new AudioClip(Sound.class.getResource("/sounds/ChipMe.m4a").toString());
	public static AudioClip coinSound = new AudioClip(Sound.class.getResource("/sounds/ChingChingChip.m4a").toString());
	public static AudioClip wrongSound = new AudioClip(Sound.class.getResource("/sounds/bruh.mp3").toString());
	public static AudioClip allin = new AudioClip(Sound.class.getResource("/sounds/allin.mp3").toString());
	private static double volume = 1;
	private static Random rand = new Random();
	private static int max = 10;
	private static int min = 1;
	

	/**
	 * Plays the AudioClip.
	 * @param whatSound Name of sound that is being sent from the different classes that uses the audio objects.
	 * Amended by: Anthon Haväng, Erik Larsson, Jens Bjerre: simplified a long else if-else if section to a simple
	 * switch-case. Also changed some sound files.
	 */
	public static void playSound(String whatSound) {
		if (!mp.isMute()) {
			switch (whatSound) {
				case "check" 		-> checkSound.play();
				case "fold" 		-> cardFold.play();
				case "shuffle" 	-> shuffleSound.play();
				case "singleCard" 	-> singleCard.play();
				case "chipSingle" 	-> chipSingle.play();
				case "chipMulti" 	-> chipMulti.play();
				case "coinSound" 	-> coinSound.play();
				case "wrong" 		-> wrongSound.play();
				case "allin" -> {
					int randomNum = rand.nextInt((max - min) + 1) + min;
					System.out.println(randomNum + " randomizade nummer");
					if (randomNum == 5){
						allin.play();
					}
				}
			}
		}
	}

	/**
	 * Starts playing the background music.
	 */
	public static void playBackgroundMusic() {
		mp.play();
	}


	/**
	 * Method created to be used in a future volume slider. Could be separated into two sliders for Music and SFX.
	 * @param newVolume the next volume setting.
	 */
	public static void setVolume(double newVolume){
		volume = newVolume;
		mp.setMute(false);
		mp.setVolume(volume / 100);
		checkSound.setVolume(volume);
		shuffleSound.setVolume(volume);
		singleCard.setVolume(volume);
		cardFold.setVolume(volume);
		chipSingle.setVolume(volume);
		chipMulti.setVolume(volume);
		coinSound.setVolume(volume);
		wrongSound.setVolume(volume);
	}

	/**
	 * Sänker volymen på MediaPlayer
	 */
	public static void lowerVolume(){
		mp.setVolume(0.5);
		System.out.println(mp.getVolume() + " volym nu");
	}

	/**
	 * Toggles the sound on and off.
	 */
	public static void toggleMute(){
		mp.setMute(!mp.isMute());
	}

	/**
	 * Stops the music.
	 */
	public static void stopMusic(){
		mp.stop();
	}

}
