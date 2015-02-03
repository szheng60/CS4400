/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental10.model;

import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author song
 */
public class MusicPlayerSingleton {
    private static MusicPlayerSingleton instance = null;
    private boolean played = false;
    final URL resource;
    final Media media;
    final MediaPlayer mediaPlayer;
    private MusicPlayerSingleton()
    {
        resource  = getClass().getClassLoader().getResource("rental10/dm.mp3");
        media  = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media); 
    }
    
    public static MusicPlayerSingleton getInstance()
    {
        if (instance == null)
        {
            instance = new MusicPlayerSingleton();
        }
        return instance;
    }
    public void play()
    {
        try
        {    
            mediaPlayer.setOnError(new Runnable() {
                @Override
                public void run() {
                    final String errorMessage = media.getError().getMessage();
                }
            }); 
            if (!played)
            {
                mediaPlayer.play();
            }
        } 
        catch (RuntimeException e) 
        {
        }        
        played = true;
    }

}
