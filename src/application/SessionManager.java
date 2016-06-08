package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.StringProperty;
import javafx.stage.FileChooser;
import javafx.stage.Window;


public class SessionManager {


  public String SESSION_PROPERTIES_FILENAME;
  public String SESSION_PROPERTIES_FILENAME_Replay;

  private final static Properties props = new Properties();

  private final GridOperator gridOperator;
  public static long NumberSave = 0;
  public static int NumberRead = 1;
  public static boolean EndFile = false;
  
  public static int ReadBuf = 327;
  public static int ReadBuf1 = 0;
  
  public static int step;
  public static int score;
  public static int time;
  public static int tile;

  public SessionManager(GridOperator gridOperator) {
    this.gridOperator = gridOperator;
    //this.SESSION_PROPERTIES_FILENAME = "AutoGenSave99.";
  }
  
  
  // Сохранение с псевдокодом
  public void saveSession(Map<Location, Tile> gameGrid, Integer score, Long time, Long number) {
    try {
      gridOperator.traverseGrid((x, y) -> {
        Tile t = gameGrid.get(new Location(x, y));
        props.setProperty("Location_tile_on_<" + x + ">_line_and_<" + y + ">_column_:", t != null ? t.getValue().toString() : "0");
        return 0;
      });
      props.setProperty("Score_of_Game_:", score.toString());
      props.setProperty("Time_of_Game_:", time.toString());
      props.setProperty("Number_the_game_moves_:", number.toString());
      props.store(new FileWriter(GameManager.gameSaveFile, true), SESSION_PROPERTIES_FILENAME);
    } catch (IOException ex) {
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  
  /* обычное сохранение
  public void saveSession(Map<Location, Tile> gameGrid, Integer score, Long time, Long number) {
    try {
      gridOperator.traverseGrid((x, y) -> {
        Tile t = gameGrid.get(new Location(x, y));
        props.setProperty("Location_" + x + "_" + y, t != null ? t.getValue().toString() : "0");
        return 0;
      });
      props.setProperty("score", score.toString());
      props.setProperty("time", time.toString());
      props.setProperty("NumberOfSave", number.toString());
      props.store(new FileWriter(GameManager.gameSaveFile, true), SESSION_PROPERTIES_FILENAME);
    } catch (IOException ex) {
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
    }
  }
  */
  
  
  /*
   * 
   * Генератор Реплаев и файлов
   * 
  public void saveSession(Map<Location, Tile> gameGrid, Integer score, Long time, Long number) {
    File file = null;
    int a = 10000;
    int b = 12000;
    for(int gaga = a; gaga < b; gaga++)
    {
      try {
        gridOperator.traverseGrid((x, y) -> {
          Tile t = gameGrid.get(new Location(x, y));
          props.setProperty("Location_" + x + "_" + y, t != null ? t.getValue().toString() : "0");
          return 0;
        });
        score = score + gaga;
        number = number + gaga;
        props.setProperty("score", score.toString());
        props.setProperty("time", time.toString());
        props.setProperty("NumberOfSave", number.toString());
        file = new File("C:\\Users\\Стас\\workspace\\LR5(Git)\\save" + gaga);
        //this.SESSION_PROPERTIES_FILENAME = "AutoGen/" + "Save" + gaga;
        props.store(new FileWriter(file, true), SESSION_PROPERTIES_FILENAME);
      } catch (IOException ex) {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
      }
    }
  }*/

  public int restoreSession(Map<Location, Tile> gameGrid, StringProperty time) {

    if( NumberRead == 1){
      
    } else {
      NumberRead = NumberRead + 21;
    }

    //Reader reader = null;
    BufferedReader reader = null;
    StringReader reader1 = null;
    long lines = 0;
    String zeile = null;
    long line = 0;
    
    try {
      lines = Files.lines(GameManager.gameReadFile.toPath()).count();
      
      reader = new BufferedReader(new FileReader(GameManager.gameReadFile));
      
      /*
      RandomAccessFile file = new RandomAccessFile(GameManager.gameReadFile, "r");
      String res = "";

      // ставим указатель на нужный вам символ
      file.seek(NumberRead);
      int b = file.read();

      // побитово читаем и добавляем символы в строку
      while(b != -1){
          res = res + (char)b;

          b = file.read();
      }
      file.close();
      */
      
      /*String s;
      
      while((s = reader.readLine()) != null){
                   
          System.out.println(s);
          //reader1 = new StringReader(s);
      }
      
       
      /*
        for( int i = NumberRead; i < NumberRead + 21; i++){
        
        s = reader.readLine();
        System.out.println(s);
        reader1 = new StringReader(s);
        
        System.out.printf("FOR, NumberRead: %d \n", NumberRead);
        
        if( i == 21){
          NumberRead = NumberRead + 21;
        }
      }
      */
      
      
      props.load(reader);
      
    } catch (FileNotFoundException ignored) {
      System.out.println("Not found file Save replay!");
      return -1;
    } catch (IOException ex) {
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
    } finally {
      System.out.printf("EXIT, NumberRead: %d \n", NumberRead);
      if (reader1 != null) {
        reader1.close();
      }
    }
    
    
    gridOperator.traverseGrid((x, y) -> {
      String val = props.getProperty("Location_" + x + "_" + y);
      if (!val.equals("0")) {
        Tile t = Tile.newTile(new Integer(val));
        Location l = new Location(x, y);
        t.setLocation(l);
        gameGrid.put(l, t);
      }
      return 0;
    });
    time.set(props.getProperty("time"));

    String score = props.getProperty("score");
    if (score != null) {
      return new Integer(score);
    }

    return 0;
  }
  
  public int openLastStepOfSession(Map<Location, Tile> gameGrid, StringProperty time) {

    Reader reader1 = null;
    String readers = null;
    Reader targetReader = null;
    
    try {
      reader1 = new FileReader(GameManager.gameReadFile);
      char c[] = new char[ReadBuf];
      reader1.read(c, 0, ReadBuf);
      ReadBuf = ReadBuf + 327;
      if(ReadBuf1 == 5){
        ReadBuf = ReadBuf + 10;
      }
      ReadBuf1++;
      System.out.println("ReadBuf: " + ReadBuf);
      System.out.println("ReadBuf1: " + ReadBuf1);
      StringBuilder sb = new StringBuilder();
      sb.append(c);
      readers = new String(sb);
      targetReader = new StringReader(readers);
      //reader = new FileReader(GameManager.gameReadFile);

      props.load(targetReader);
    } catch (FileNotFoundException ignored) {
      System.out.println("Not found file Save replay!");
      return -1;
    } catch (IOException ex) {
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
    } 
    gridOperator.traverseGrid((x, y) -> {
      String val = props.getProperty("Location_" + x + "_" + y);
      if (!val.equals("0")) {
        Tile t = Tile.newTile(new Integer(val));
        Location l = new Location(x, y);
        t.setLocation(l);
        gameGrid.put(l, t);
      }
      return 0;
    });
    time.set(props.getProperty("time"));

    String score = props.getProperty("score");
    if (score != null) {
      return new Integer(score);
    }

    return 0;
  }
    
  public static String sortingFileTime(File file) {
    Reader reader = null;
    try {
      reader = new FileReader(file);
      props.load(reader);
    } catch (FileNotFoundException ignored) {
      System.out.println("Not found file!");
      //return -1;
    } catch (IOException ex) {
      Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } catch (IOException ex) {
        Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    String time = props.getProperty("time");
    if (time != null) {
      return time;
    }
    return "0";
  }
  
  public static int SortingFileScore(File file) {
    Reader reader = null;
    try {
      reader = new FileReader(file);
      props.load(reader);
    } catch (FileNotFoundException ignored) {
      System.out.println("Not found file!");
      return -1;
    } catch (IOException ex) {
      Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } catch (IOException ex) {
        Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    String score = props.getProperty("score");
    if (score != null) {
      return new Integer(score);
    }
    return 0;
  }
  
  public static int SortingFileStep(File file) {
    Reader reader = null;
    try {
      reader = new FileReader(file);
      props.load(reader);
    } catch (FileNotFoundException ignored) {
      System.out.println("Not found file!");
      return -1;
    } catch (IOException ex) {
      Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } catch (IOException ex) {
        Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    String NumberOfSave = props.getProperty("NumberOfSave");
    if (NumberOfSave != null) {
      return new Integer(NumberOfSave);
    }
    return 0;
  }

}

