
package mediaplayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;


public class FXMLDocumentController implements Initializable {
    
    private MediaPlayer mediaPlayer;
    
    @FXML
    private MediaView mediaView;
   
    @FXML
    private Slider slider;
    
    @FXML
    private Slider seekSlider;
    
    private String filePath;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");


//        Method1
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select File (.mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        
        
//        Method2
//         fileChooser.getExtensionFilters().addAll(
//         new ExtensionFilter("Text Files", "*.txt"),
//         new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
//         new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
//         new ExtensionFilter("All Files", "*.*"));

        File file = fileChooser.showOpenDialog(null);    //We can multiple files too using showOpenMultipleDialog
        filePath = file.toURI().toString();
        
        if(filePath!=null){
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            
            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();
            
            width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
            
            slider.setValue(mediaPlayer.getVolume()*100);
            slider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(slider.getValue());
                }
            });
            
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    seekSlider.setValue(newValue.toSeconds());
                }
            });
            
            seekSlider.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
                }
                
            });
            
            mediaPlayer.play();
        }
        

    }
    
    @FXML
    private void playVideo (ActionEvent event){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    
    @FXML
    private void pauseVideo (ActionEvent event){
        mediaPlayer.pause();
    }
    
    @FXML
    private void stopVideo (ActionEvent event){
        mediaPlayer.stop();
    }
    
    @FXML
    private void fastVideo (ActionEvent event){
        mediaPlayer.setRate(1.5);
    }
    
    @FXML
    private void fasterVideo (ActionEvent event){
        mediaPlayer.setRate(2);
    }
    
    @FXML
    private void slowVideo (ActionEvent event){
        mediaPlayer.setRate(0.75);
    }
    
    @FXML
    private void slowerVideo (ActionEvent event){
        mediaPlayer.setRate(0.5);
    }
    
    @FXML
    private void exitVideo (ActionEvent event){
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
