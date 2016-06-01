import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class wordStopWatch extends PApplet {

/*
Mitchell Griest
 Word Stopwatch
 Summer 2016
 
 mdgriest@crimson.ua.edu
 */

//A few colors to get things started
int red = 0xffF27074;
int darkRed = 0xff8E2800;
int clay = 0xffB64926;
int yellow = 0xffFFA500;
int paleYellow = 0xffFFFF9D;
int orange = 0xffFF6138;
int green = 0xff468966;
int limeGreen = 0xffBDF271;
int lightGreen = 0xffBEEB9F;
int middleGreen = 0xff79BD8F;
int blueGreen = 0xff00A388;
int blue = 0xff1E90FF;
int dullBlue = 0xff348899;
int aqua = 0xff29D9C2;
int lightBlue = 0xff99CCFF;
int darkGrayBlue = 0xff6D7889;
int lightGray = 0xffD4D4D4;
int middleGray = 0xffAAAAAA;
int darkGray = 0xff4B4747;
int white = 0xffF3F4F2;

//The colors we will use to set the look for the app
int bg;
int c1;
int c2;
int c3;

PFont font;
int fontSize;
int maxFontSize = 200; //Feel free to increase this for higher resolution monitors
int minFontSize = 10;
int fontStep = 1;
String fontName = "Helvetica-Light"; //Change this to use a different typeface

int hours;
int minutes;
int seconds;

int x;
int initialX = width/10;
int y;

//Used for resetting the stopwatch without restarting the sketch
int time;
int start;

boolean paused;
int pauseStart;
int pauseRelease;
int totalPauseTime = 0;

public void setup() {
  
  surface.setResizable(true);
  surface.setTitle("Word Stopwatch");

  bg = darkGray;
  c1 = aqua;
  c2 = lightGray;
  c3 = lightGray;
  background(bg);

  //Create the font
  fontSize = 25;
  font = createFont(fontName, fontSize);
  textFont(font);

  start = millis();
  paused = false;
  
  ellipseMode(CENTER);
  strokeWeight(2);
}

public void draw() {
  if (!paused) {
    background(bg);
    drawWord("It's been", c3);

    time = millis() - start - totalPauseTime;
    hours = getHour();
    minutes = getMinute();
    seconds = getSecond();

    if (hours != 0) {
      drawWord(toString(hours), c1);
      drawWord(" hours", c2);
      if (minutes != 0 || seconds != 0) {
        drawWord(",", c3);
      }
    }
    if (minutes != 0) {
      drawWord(toString(minutes), c1);
      String m = minutes == 1? " minute" : " minutes";
      drawWord(m, c2);
      if (seconds != 0) {
        drawWord(" and", c3);
      }
    }
    if (seconds != 0) {
      drawWord(toString(seconds), c1);
      String s = seconds == 1? " second" : " seconds";
      drawWord(s, c2);
      drawWord(".", c1);
    }
    x = initialX;
    y = height/2;
  }
}

public void drawWord(String word, int c) {
  if (x + textWidth(word) >= width) {
    x = initialX;
    y += fontSize;
  }
  fill(c);
  text(word, x, y);  
  x += textWidth(word);
}

//This method courtesy of
//http://stackoverflow.com/questions/3911966/how-to-convert-number-to-words-in-java
public String toString(int value) {
  String[] tensNames = {
    "", 
    " ten", 
    " twenty", 
    " thirty", 
    " forty", 
    " fifty", 
    " sixty", 
    " seventy", 
    " eighty", 
    " ninety"
  };

  String[] generalNames = {
    "", 
    " one", 
    " two", 
    " three", 
    " four", 
    " five", 
    " six", 
    " seven", 
    " eight", 
    " nine", 
    " ten", 
    " eleven", 
    " twelve", 
    " thirteen", 
    " fourteen", 
    " fifteen", 
    " sixteen", 
    " seventeen", 
    " eighteen", 
    " nineteen"
  };

  StringBuilder sb = new StringBuilder();

  if (value % 100 < 20) {
    sb.append(generalNames[value % 100]);
    value /= 100;
  } else {
    sb.append(generalNames[value % 10]);
    value /= 10;
    sb.insert(0, tensNames[value % 10]);
    value /= 10;
  }
  if (value == 0) return sb.toString();
  return generalNames[value] + " hundred" + sb.toString();
}

public String hourToString() {
  if (hours == 0) return "";
  else return toString(hours);
}

public String minuteToString() {
  if (minutes == 0) return "";
  else return toString(minutes);
}

public String secondToString() {
  if (seconds == 0) return "";
  else return toString(seconds);
}

public int getHour() {
  return time / 3600000;
}

public int getMinute() {
  return (time / 60000) - (60 * hours);
}

public int getSecond() {
  return (time / 1000) - (60 * minutes);
}

public void keyPressed() {
  //Increase font size
  if (keyCode == ']') {
    fontSize = min(fontSize + fontStep, maxFontSize);
    font = createFont(fontName, fontSize);
    textFont(font);
  }

  //Decrease font size)
  else if (key == '[' ) {
    fontSize = max(fontSize - fontStep, minFontSize);
    font = createFont(fontName, fontSize);
    textFont(font);
  }

  //Color schemes
  else if (key == '1') {
    bg = white;
    c1 = darkGray;
    c2 = middleGray;
    c3 = middleGray;
  } else if (key == '2') {
    bg = green;
    c1 = limeGreen;
    c2 = lightGray;
    c3 = lightGray;
  } else if (key == '3') {
    bg = darkGray;
    c1 = red;
    c2 = lightGray;
    c3 = lightGray;
  } else if (key == '4') {
    bg = white;
    c1 = blue;
    c2 = middleGray;
    c3 = middleGray;
  } else if (key == '5') {
    bg = darkRed;
    c1 = white;
    c2 = red;
    c3 = red;
  } else if (key == '0') {
    bg = darkGray;
    c1 = aqua;
    c2 = lightGray;
    c3 = lightGray;
  }

  //Pause
  else if ( key == ' ') {
    if (paused) {
      paused = false;
      pauseRelease = millis();
      totalPauseTime += pauseRelease - pauseStart;
    } else {
      paused = true;
      pauseStart = millis();
      stroke(c1);
      int x1 = width/2 - fontSize;
      int x2 = width/2 + fontSize;
      int y1 = y - fontSize;
      int y2 = y + fontSize;
      line(x1, 0, x1, height);
      line(x2, 0, x2, height);
    }
  }

  //Reset
  else if ( key == '\n' || key == '\b') {
    start = millis();
  }
}

  public void settings() {  size(400, 130); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "wordStopWatch" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
