package com.fencing.app;
import javafx.application.Application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.fencing.fencing.FoilMatch;
import com.fencing.fencing.FoilMatch.Player;

public class FoilViewerApp extends Application {

    private FoilMatch match;
    private Timeline autoplay;

    // UI
    private Label scoreLabel = new Label();
    private Label attackerLabel = new Label();

    private Text atkText = new Text("-");
    private Text defText = new Text("-");
    private Label outcomeLabel = new Label("-");

    private ListView<String> log = new ListView<>();

    @Override
    public void start(Stage stage) {
        match = new FoilMatch(5, Player.A); // first to 5

        // Card panes
        StackPane atkCardPane = cardPane("Attacker", atkText);
        StackPane defCardPane = cardPane("Defender", defText);

        HBox center = new HBox(20, atkCardPane, defCardPane);
        center.setPadding(new Insets(10));

        // Controls
        Button stepBtn = new Button("Step");
        Button autoBtn = new Button("Auto");
        Button stopBtn = new Button("Stop");
        Button resetBtn = new Button("Reset");

        stepBtn.setOnAction(e -> stepOnce());
        autoBtn.setOnAction(e -> startAutoplay());
        stopBtn.setOnAction(e -> stopAutoplay());
        resetBtn.setOnAction(e -> resetMatch());

        HBox controls = new HBox(10, stepBtn, autoBtn, stopBtn, resetBtn, outcomeLabel);
        controls.setPadding(new Insets(10));

        VBox top = new VBox(5, scoreLabel, attackerLabel);
        top.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(center);
        root.setRight(log);
        root.setBottom(controls);

        updateHeader();

        stage.setScene(new Scene(root, 900, 400));
        stage.setTitle("Foil Match Viewer");
        stage.show();
    }

    private StackPane cardPane(String title, Text valueText) {
        Rectangle r = new Rectangle(220, 140);
        r.setArcWidth(18);
        r.setArcHeight(18);

        VBox box = new VBox(8, new Label(title), valueText);
        box.setPadding(new Insets(12));

        StackPane pane = new StackPane(r, box);
        pane.setPadding(new Insets(10));
        return pane;
    }

    private void stepOnce() {
        FoilMatch.Exchange ex = match.step(); // returns null when finished/deck empty
        if (ex == null) {
            outcomeLabel.setText("Done");
            stopAutoplay();
            return;
        }

        atkText.setText(ex.attackCard().toString());
        defText.setText(ex.defendCard().toString());
        outcomeLabel.setText(ex.outcome().toString());

        log.getItems().add(String.format(
                "#%d Att=%s  %s vs %s  => %s  | %d-%d  NextAtt=%s",
                ex.number(), ex.attackerBefore(),
                ex.attackCard(), ex.defendCard(),
                ex.outcome(), ex.scoreA(), ex.scoreB(),
                ex.attackerAfter()
        ));

        updateHeader();
    }

    private void updateHeader() {
        scoreLabel.setText("Score A-B: " + match.scoreA() + " - " + match.scoreB());
        attackerLabel.setText("Attacker: " + match.attacker());
    }

    private void startAutoplay() {
        if (autoplay != null) autoplay.stop();
        autoplay = new Timeline(new KeyFrame(Duration.millis(600), e -> stepOnce()));
        autoplay.setCycleCount(Timeline.INDEFINITE);
        autoplay.play();
    }

    private void stopAutoplay() {
        if (autoplay != null) autoplay.stop();
    }

    private void resetMatch() {
        stopAutoplay();
        log.getItems().clear();
        atkText.setText("-");
        defText.setText("-");
        outcomeLabel.setText("-");
        match = new FoilMatch(5, Player.A);
        updateHeader();
    }

    public static void main(String[] args) {
        launch(args);
    }
}