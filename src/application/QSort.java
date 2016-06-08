package application;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QSort {
  

  static double timer;
  static double timerBigThen;
  static int score;
  static int step;

  static Text time = new Text();
  static Text best = new Text();
  static Text worst = new Text();
  
  static Text steps = new Text();
  static Text scores = new Text();
  static Text counts = new Text();
  static Text times = new Text();
  static Text bestTimes = new Text();
  static Text worstTimes = new Text();

  public static void StageSorting() {
    Pane scene_info = new Pane();

    Stage sorting = new Stage();
    sorting.setWidth(800);
    sorting.setHeight(800);
    sorting.setTitle("Sorting of the games 2048");
    sorting.getIcons().add(new Image(QSort.class.getResourceAsStream("/Resources/2048.png")));
    scene_info.getStylesheets().add("application/game.css");

    Button Button_sorting_long = new Button("FX(Step)");
    Button_sorting_long.getStyleClass().add("game-button_sorting_long");
    Button_sorting_long.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        deleteText(scene_info);

        GameManager.OpenFileToStep();

        double startTime = System.currentTimeMillis();

        sortingData();

        double timeSpent = System.currentTimeMillis() - startTime;

        printDataText(scene_info, timeSpent, Board.Files[Board.count - 1], Board.Files[0]);

        createSortingTable("Step's FX", Board.Files);

        printMassiveString();
        printMassiveData();

      }
    });

    Button Button_sorting_score = new Button("FX(score)");
    Button_sorting_score.getStyleClass().add("game-button_sorting_score");
    Button_sorting_score.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        deleteText(scene_info);

        GameManager.OpenFileToScore();

        long startTime = System.currentTimeMillis();

        sortingData();

        double timeSpent = System.currentTimeMillis() - startTime;
        System.out.println("Time: " + timeSpent);

        printDataText(scene_info, timeSpent, Board.Files[Board.count - 1], Board.Files[1]);

        createSortingTable("Score FX", Board.Files);

        printMassiveString();
      }
    });

    Button Button_sorting_s_long = new Button("Scala(Step)");
    Button_sorting_s_long.getStyleClass().add("game-button_sorting_s_long");
    Button_sorting_s_long.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        deleteText(scene_info);

        GameManager.OpenFileToStep();

        long startTime = System.currentTimeMillis();

        QSorting sr = new QSorting();
        sr.sort(Board.FilesData, Board.Files);

        double timeSpent = System.currentTimeMillis() - startTime;

        printDataText(scene_info, timeSpent, Board.Files[0], Board.Files[Board.count - 1]);

        createSortingTable("Step's Scala", Board.Files);
      }
    });

    Button Button_sorting_s_score = new Button("Scala(Score)");
    Button_sorting_s_score.getStyleClass().add("game-button_sorting_s_score");
    Button_sorting_s_score.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        deleteText(scene_info);

        GameManager.OpenFileToScore();

        long startTime = System.currentTimeMillis();

        QSorting sr = new QSorting();
        sr.sort(Board.FilesData, Board.Files);

        double timeSpent = System.currentTimeMillis() - startTime;

        Board.count = Board.count - 1;

        printDataText(scene_info, timeSpent, Board.Files[0], Board.Files[Board.count - 1]);

        createSortingTable("Score Scala", Board.Files);
      }
    });

    Button buttonStatistic = new Button("Create statictic");
    buttonStatistic.getStyleClass().add("game-buttonStatistic");
    buttonStatistic.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        deleteText(scene_info);
        
        GameManager.openFileToStatistic();
        
        createStatisticScala();

        sortingTime(Board.avrTime);
        
        printStatistic(scene_info, timer, step, score, Board.count,
            Board.avrTime[0], Board.avrTime[Board.count-1]);
      }
    });

    Text info_sorting = new Text("Sorting data games 2048:");
    info_sorting.getStyleClass().add("game-info_sorting");

    Text info_statistic = new Text("Statistic of games 2048:");
    info_statistic.getStyleClass().add("game-info_statistic");

    Button Button_close = new Button("CLOSE");
    Button_close.getStyleClass().add("game-button_close");
    Button_close.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        sorting.close();
      }
    });

    Scene scene = new Scene(scene_info);
    scene_info.getStyleClass().add("game-sorting");

    scene_info.getChildren().addAll(info_sorting, info_statistic, Button_sorting_long,
        Button_sorting_score, Button_sorting_s_long, Button_sorting_s_score, buttonStatistic,
        Button_close);

    sorting.setScene(scene);
    sorting.show();
  }

  static JDialog dialog = null;
  static JTable table = null;

  public static void deleteText(Pane sorting) {
    sorting.getChildren().removeAll(time, best, worst, times, scores, steps, counts,
        bestTimes, worstTimes);
  }

  public static void createStatisticScala() {
    ScalaStatistic sr = new ScalaStatistic();
    sr.createStatistic(Board.avrTime, Board.avrScore, Board.avrStep, Board.count);
    
    
    int n = 0;
    for(int a = 0; a < 10000; a++){
      if(Board.avrTime[a] != 0 ){
        n++;
      }
    }
    
    long timeBig[] = new long[n];
    
    for(int a = 0; a < n; a++){
      if(Board.avrTime[a] != 0 ){
        timeBig[a] = Board.avrTime[a];
      }
    }
    
    timerBigThen = sr.getField(timeBig);
    
    timer = sr.getTime();
    score = sr.getScore();
    step = sr.getStep();
  }
  
  public static void printStatistic(Pane sorting, double timer, int step, int score, int count,
      long bestTime, long worstTime ) {

    times.setText("Averade time of game: " + Double.toString(timer) + " [sec]");
    times.setFont(Font.font("Arial", FontPosture.ITALIC, 24));
    times.setFill(Color.YELLOW);
    times.setLayoutX(100);
    times.setLayoutY(200);

    scores.setText("Averade scores of game: " + Integer.toString(score));
    scores.setFont(Font.font("Arial", FontPosture.ITALIC, 24));
    scores.setFill(Color.YELLOW);
    scores.setLayoutX(100);
    scores.setLayoutY(250);

    steps.setText("Averade steps of game: " + Integer.toString(step));
    steps.setFont(Font.font("Arial", FontPosture.ITALIC, 24));
    steps.setFill(Color.YELLOW);
    steps.setLayoutX(100);
    steps.setLayoutY(300);
    
    counts.setText("Number of game: " + Integer.toString(count));
    counts.setFont(Font.font("Arial", FontPosture.ITALIC, 24));
    counts.setFill(Color.YELLOW);
    counts.setLayoutX(100);
    counts.setLayoutY(350);
    
    bestTimes.setText("The best time of the game: " + Long.toString(bestTime));
    bestTimes.setFont(Font.font("Arial", FontPosture.ITALIC, 24));
    bestTimes.setFill(Color.YELLOW);
    bestTimes.setLayoutX(100);
    bestTimes.setLayoutY(400);
    
    worstTimes.setText("The worst game time: " + Long.toString(worstTime));
    worstTimes.setFont(Font.font("Arial", FontPosture.ITALIC, 24));
    worstTimes.setFill(Color.YELLOW);
    worstTimes.setLayoutX(100);
    worstTimes.setLayoutY(450);

    sorting.getChildren().addAll(times, scores, steps, counts, bestTimes, worstTimes);
   
    Board.count = 0;
  }

  public static void sortingTime(long mas[]) {
    int pos = 0;
    for (int i = 0; i < Board.count; i++) {
      long max = mas[i];
      for (int j = i; j < Board.count; j++)
        if (max >= mas[j]) {
          max = mas[j];
          pos = j;
        }
      long tmp = mas[i];
      mas[i] = max;
      mas[pos] = tmp;
    }
  }

  public static void createSortingTable(String name, String[] ms) {

    dialog = new JDialog();
    dialog.setTitle(name);

    DefaultTableModel model = new DefaultTableModel(new Object[] {"Sorted games"}, 0);
    table = new JTable(model);
    dialog.setLayout(new BorderLayout());
    dialog.add(new JScrollPane(table), BorderLayout.CENTER);

    for (int tmp = 0; tmp < Board.count; tmp++)
      model.addRow(new Object[] {ms[tmp]});
    dialog.pack();
    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    dialog.setResizable(true);
    dialog.setVisible(true);

    deleteFilesData();
    deleteFiles();
  }

  public static void sortingData() {
    String TMPFile = null;
    int pos = 0;
    for (int i = 0; i < Board.count; i++) {
      Board.MaxScore = Board.FilesData[i];
      TMPFile = Board.Files[i];

      for (int j = i; j < Board.count; j++)
        if (Board.MaxScore >= Board.FilesData[j]) {
          Board.MaxScore = Board.FilesData[j];
          TMPFile = Board.Files[j];
          pos = j;
        }
      int tmp = Board.FilesData[i];
      String temp = Board.Files[i];
      Board.FilesData[i] = Board.MaxScore;
      Board.Files[i] = TMPFile;
      Board.FilesData[pos] = tmp;
      Board.Files[pos] = temp;
    }
  }

  public static void printMassiveString() {
    for (int i = 0; i < Board.count; i++) {
      System.out.printf("Sort files[%d]: %s \n", i, Board.Files[i]);
    }
  }

  public static void printMassiveStatistic() {
    for (int i = 0; i < Board.count; i++) {
      System.out.printf("Avridge Step[%d]: %s \n", i, Board.avrStep[i]);
      System.out.printf("Avridge Score[%d]: %s \n", i, Board.avrScore[i]);
      System.out.printf("Avridge Time[%d]: %s \n", i, Board.avrTime[i]);
    }
  }

  public static void printMassiveData() {
    for (int i = 0; i < Board.count; i++) {
      System.out.printf("Sort files[%d]: %s \n", i, Board.FilesData[i]);
    }
  }

  public static void deleteFilesData() {
    for (int i = 0; i < Board.count; i++) {
      Board.FilesData[i] = 0;
    }
    Board.count = 0;
  }

  public static void deleteFiles() {
    for (int i = 0; i < Board.count; i++) {
      Board.Files[i] = null;
    }
    Board.count = 0;
  }

  public static void printDataText(Pane sorting, double timer, String Best, String Worst) {
    Double value = timer;
    String Timers = value.toString();

    time.setText("Time sorting: " + Timers + " [ms]");
    time.setFont(Font.font("Arial", FontPosture.ITALIC, 24));
    time.setFill(Color.YELLOW);
    time.setLayoutX(100);
    time.setLayoutY(200);

    best.setText("Best game: " + Best);
    best.setFont(Font.font("Arial", FontPosture.ITALIC, 24));
    best.setFill(Color.YELLOW);
    best.setLayoutX(100);
    best.setLayoutY(250);

    worst.setText("Worst game: " + Worst);
    worst.setFont(Font.font("Arial", FontPosture.ITALIC, 24));
    worst.setFill(Color.YELLOW);
    worst.setLayoutX(100);
    worst.setLayoutY(300);

    sorting.getChildren().addAll(time, best, worst);
  }
}
