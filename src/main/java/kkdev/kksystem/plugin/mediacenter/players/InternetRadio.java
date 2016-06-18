/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.mediacenter.players;

import kkdev.kksystem.plugin.mediacenter.configuration.PlayList;
import kkdev.kksystem.plugin.mediacenter.configuration.PlayListEntry;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 *
 * @author blinov_is
 */
public class InternetRadio implements IPlayer {
    private PlayerInfo currentTrack;
    private PlayList currentPlayList;
    
    final EmbeddedMediaPlayer  mediaPlayer = createPlayer();
    String[] VLC_ARGS = {
            "--intf", "dummy",          // no interface
            "--vout", "dummy",          // we don't want video (output)
            "--no-video-title-show",    // nor the filename displayed
            "--no-stats",               // no stats
            "--no-sub-autodetect-file", // we don't want subtitles
            "--no-inhibit",             // we don't want interfaces
            "--no-disable-screensaver", // we don't want interfaces
            "--no-snapshot-preview",    // no blending in dummy vout
            "--alsa-audio-device default",
            "-vvv"
    };
    private EmbeddedMediaPlayer  createPlayer( ) {
        EmbeddedMediaPlayer  headlessMediaPlayer;
        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(VLC_ARGS);
        headlessMediaPlayer=mediaPlayerFactory.newEmbeddedMediaPlayer();
        return headlessMediaPlayer;
    }
    
    public InternetRadio(){
        currentTrack=new PlayerInfo();
        currentTrack.PlayerName="Internet Radio";
        currentTrack.TitleArtist="===";
        currentTrack.TitleDescription="===";
        currentTrack.TrackTimeLine="===";
        
    }
    @Override
    public void play() {
        PlayListEntry PLE=currentPlayList.getTrack();
        currentTrack.TitleArtist=PLE.Title;
        currentTrack.TitleDescription=PLE.OnlineTrackInfoArtist;
        
        mediaPlayer.playMedia(PLE.SourceAddr);
        currentTrack.CurrentVolumeLevel=mediaPlayer.getVolume();
    }

    @Override
    public void play(int PlayListPosition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop() {
        mediaPlayer.stop();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void resume() {
       mediaPlayer.release();
    }

    @Override
    public void seekForward() {
       //
    }

    @Override
    public void seekBackward() {
      //
    }

    @Override
    public void stepNext() {
       play();
    }

    @Override
    public void stepBack() {
        //
    }

    @Override
    public void shuffle() {
      //
    }

    @Override
    public void setPlayList(PlayList PList) {
        currentPlayList=PList;
    }

    @Override
    public PlayerInfo getPlayerInfo() {
        return currentTrack;
    }

    @Override
    public void increaseVolume(int Step) {
        mediaPlayer.setVolume(mediaPlayer.getVolume()+Step);
         currentTrack.CurrentVolumeLevel=mediaPlayer.getVolume();
    }

    @Override
    public void decreaseVolime(int Step) {
        mediaPlayer.setVolume(mediaPlayer.getVolume()-Step);
        currentTrack.CurrentVolumeLevel=mediaPlayer.getVolume();
    }

}
