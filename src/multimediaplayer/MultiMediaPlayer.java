/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multimediaplayer;

import java.io.File;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SliderBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author MWIGO-JON-MARK
 */
public class MultiMediaPlayer extends Application
{
        MediaView mediaview;
        Button playPauseBtn;
        Button mute;
        Button stop;
        Button loadFile;
        Slider timeSlider;
        Slider volSlider;
        FileChooser fileChooser;
        Media media;
        MediaPlayer mediaplayer;
        Label mediaTitle;
        Label currentTime;
        Label defaultTime;
        Label volLbl;
//        Label statusLbl;
        BorderPane root;
        FileChooser fileLoader;
        File selected;
        Duration currenttime;
        Duration duration;
        Pane mediaViewHolder;
        HBox statusBar;
    
    @Override
    public void start(Stage stage)
    {
        playPauseBtn = new Button("Play");
        mute = new Button("Mute");
        stop = new Button("Stop");
        loadFile = new Button("File");
        currentTime = new Label();
        defaultTime = new Label();
        timeSlider = SliderBuilder
                .create()
                .prefWidth(200)
                .minWidth(70)
                .maxWidth(Region.USE_PREF_SIZE)
                .build();
        volLbl = new Label("Vol");
        volSlider = SliderBuilder
                .create()
                .prefWidth(70)
                .minWidth(50)
                .maxWidth(USE_PREF_SIZE)
                .build();
        volSlider.setValueChanging(true);
        fileChooser = new FileChooser();
        mediaViewHolder = new Pane();
        mediaTitle = new Label();
        statusBar = new HBox(loadFile, mediaTitle/*, statusLbl*/);
//        statusLbl = new Label();
        mediaTitle.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 18));
        playPauseBtn.setOnMouseEntered(e->{playPauseBtn.setStyle("-fx-background-color: rgb(102, 102, 102);"
                + "-fx-padding: 5;"
                + "-fx-border-color: white;"
                + "-fx-border-radius: 5;"
                + "-fx-border-width: 1;");});
        mute.setOnMouseEntered(e->{mute.setStyle("-fx-background-color: rgb(102, 102, 102);"
                + "-fx-padding: 5;"
                + "-fx-border-color: white;"
                + "-fx-border-radius: 5;"
                + "-fx-border-width: 1;");});
        stop.setOnMouseEntered(e->{stop.setStyle("-fx-background-color: rgb(102, 102, 102);"
                + "-fx-padding: 5;"
                + "-fx-border-color: white;"
                + "-fx-border-radius: 5;"
                + "-fx-border-width: 1;");});
        loadFile.setOnMouseEntered(e->{loadFile.setStyle("-fx-background-color: rgb(102, 102, 102);"
                + "-fx-padding: 5;"
                + "-fx-border-color: white;"
                + "-fx-border-radius: 5;"
                + "-fx-border-width: 1;");});
        
        playPauseBtn.setOnMouseExited(e->{playPauseBtn.setStyle("-fx-background-color: gray;"
                + "-fx-padding: 5;"
                + "-fx-border-color: white;"
                + "-fx-border-radius: 5;"
                + "-fx-border-width: 1;");});
        mute.setOnMouseExited(e->{mute.setStyle("-fx-background-color: gray;"
                + "-fx-padding: 5;"
                + "-fx-border-color: white;"
                + "-fx-border-radius: 5;"
                + "-fx-border-width: 1;");});
        stop.setOnMouseExited(e->{stop.setStyle("-fx-background-color: gray;"
                + "-fx-padding: 5;"
                + "-fx-border-color: white;"
                + "-fx-border-radius: 5;"
                + "-fx-border-width: 1;");});
        loadFile.setOnMouseExited(e->{loadFile.setStyle("-fx-background-color: gray;"
                + "-fx-padding: 5;"
                + "-fx-border-color: white;"
                + "-fx-border-radius: 5;"
                + "-fx-border-width: 1;");});
        
        playPauseBtn.setStyle("-fx-padding: 5;"
                + "-fx-background-color: gray;"
                + "-fx-border-color: white;"
                + "-fx-border-radius: 5;"
                + "-fx-border-width: 1;");
        mute.setStyle("-fx-padding: 5;"
                + "-fx-background-color: gray;"
                + "-fx-border-color: white;"
                + "-fx-border-radius: 5;"
                + "-fx-border-width: 1;");
        stop.setStyle("-fx-padding: 5;"
                + "-fx-background-color: gray;"
                + "-fx-border-color: white;"
                + "-fx-border-radius: 5;"
                + "-fx-border-width: 1;");
        loadFile.setStyle("-fx-padding: 5;"
                + "-fx-background-color: gray;"
                + "-fx-border-color: white;"
                + "-fx-border-radius: 5;"
                + "-fx-border-width: 1;");
        
        Node left = null;
        Node right = null;
        
        //
        HBox mediaControlBar = new HBox(playPauseBtn, stop, currentTime, timeSlider, defaultTime, mute, volLbl, volSlider);
        mediaControlBar.setSpacing(10);
        mediaControlBar.setStyle("-fx-padding: 10;"
                + "-fx-background-color: gray;");
        
        loadFile.setOnAction(e->{
            fileLoader  = new FileChooser();
            selected = fileLoader.showOpenDialog(stage);
            fileLoader.getExtensionFilters().addAll(new ExtensionFilter("MultiMedia Files", "*.mp3", "*.mp4", "*.3gp", "*.webm", "*.ogg", "*.wav"));
            
            mediaTitle.setText(selected.getName());
            
            if(selected != null)
            {
                mediaViewHolder.setPrefWidth(stage.getWidth());
                mediaViewHolder.setPrefHeight(stage.getHeight()-(statusBar.getHeight()+mediaControlBar.getHeight()));
                media = new Media(selected.toURI().toString());
                mediaplayer = new MediaPlayer(media);
                mediaplayer.setAutoPlay(true);
                mediaview = new MediaView(mediaplayer);
                mediaViewHolder.getChildren().add(mediaview);
                playPauseBtn.setText("Pause");
                volSlider.setValue(mediaplayer.getVolume()*100.0);
                
                mediaplayer.currentTimeProperty().addListener(ev->updater());
            }
        });
        
        playPauseBtn.setOnAction(e -> {
            Status playerStatus = mediaplayer.getStatus();
            if(playerStatus == Status.UNKNOWN || playerStatus == Status.HALTED || playerStatus == Status.DISPOSED)
            {}
            
            if(playerStatus == Status.PAUSED || playerStatus == Status.STOPPED || playerStatus == Status.READY || playerStatus == Status.STALLED)
            {
                mediaplayer.play();
                playPauseBtn.setText("Pause");
                updater();
            }
            else
            {
                mediaplayer.pause();
                playPauseBtn.setText("Play");
            }
        });

        mute.setOnAction(e->{
            if(mediaplayer.isMute() == false)
            {
                mediaplayer.setMute(true);
                mute.setText("UnMute");
            }
            else
            {
                mediaplayer.setMute(false);
                mute.setText("Mute");
            }
        });
        
        stop.setOnAction(e->{
            Status playerStatus = mediaplayer.getStatus();
            if(playerStatus == Status.PLAYING)
            {
                mediaplayer.stop();
                playPauseBtn.setText("Play");
            }
        });
        
        volSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable ob)
            {}
        });
        volSlider.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                if(volSlider.isValueChanging())
                {
                    mediaplayer.setVolume(volSlider.getValue()/100);
                }
                updater();
            }
        });
        
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable ob)
            {}
        });
        timeSlider.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                if(timeSlider.isValueChanging())
                {
                    if(duration!=null)
                    {
                        mediaplayer.seek(duration.multiply(timeSlider.getValue()/100.0));
                    }
                    updater();
                }
            }
        });
        
        mediaControlBar.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(mediaControlBar, Pos.CENTER);
        
        //
        mediaViewHolder.setStyle("-fx-background-color: black;");
        
        //
        statusBar.setSpacing(30);
        statusBar.setStyle("-fx-padding: 10;"
                + "-fx-background-color: gray;");
        
        root = new BorderPane(mediaViewHolder, statusBar, right, mediaControlBar, left);
        
        Scene scene = new Scene(root, 850, 600);
        
        scene.widthProperty().addListener(e->{
            timeSlider.setPrefWidth(stage.getWidth() * 0.286);
            volSlider.setPrefWidth(stage.getWidth() * 0.1);
            mediaViewHolder.setPrefWidth(stage.getWidth());
            mediaview.setFitWidth(mediaViewHolder.getWidth());
        });
        
        scene.heightProperty().addListener(e->{
            mediaViewHolder.setPrefHeight(stage.getHeight() - (statusBar.getHeight() + mediaControlBar.getHeight()));
            mediaview.setFitHeight(mediaViewHolder.getHeight());
        });
                
        stage.maximizedProperty().addListener(e->{
            if(!stage.isFullScreen())
            {
                mediaViewHolder.setPrefWidth(stage.getWidth());
                mediaViewHolder.setPrefHeight(stage.getHeight() - (statusBar.getHeight() + mediaControlBar.getHeight()));
                mediaview.setFitHeight(mediaViewHolder.getHeight());
                mediaview.setFitWidth(mediaViewHolder.getWidth());
            }
        });
        
        stage.setScene(scene);
        stage.setTitle("Dy~Od MultiMedia Player");
        stage.show();
    }
    
    @Override
    public void stop()
    {
        mediaplayer.stop();
        mediaplayer.dispose();
    }
    
    public void updater()
    {
        duration = mediaplayer.getMedia().getDuration();
        currenttime = mediaplayer.getCurrentTime();
        
        int timeSliderHours = (int) Math.floor(currenttime.toHours());
        int timeSliderMins = (int) Math.floor(currenttime.toMinutes());
        int timeSliderSecs = (int) Math.floor(currenttime.toSeconds());
        String currenttimeformat = String.format("%d:%02d:%02d", timeSliderHours, timeSliderMins, timeSliderSecs);
        
        int defaulttimeHours = (int) Math.floor(duration.toHours());
        int defaulttimeMins = (int) Math.floor(duration.toMinutes());
        int defaulttimeSecs = (int) Math.floor(duration.toSeconds());
        String defaulttimeformat = String.format("%d:%02d:%02d", defaulttimeHours, defaulttimeMins, defaulttimeSecs);
        
        if(timeSlider!=null&&volSlider!=null&&duration!=null)
        {
            if(duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging())
            {
                timeSlider.setValue(currenttime.divide(duration).toMillis()*100.0);
                defaultTime.setText(defaulttimeformat);
                currentTime.setText(currenttimeformat);
            }
            
            if(!volSlider.isValueChanging())
            {
                volSlider.setValue(mediaplayer.getVolume()*100.0);
            }
        }
    }
        
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
