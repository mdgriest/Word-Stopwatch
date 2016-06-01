/*
Mitchell Griest
 Word Stopwatch
 Summer 2016
 
 mdgriest@crimson.ua.edu
 */

//A few colors to get things started
color red = #F27074;
color darkRed = #8E2800;
color clay = #B64926;
color yellow = #FFA500;
color paleYellow = #FFFF9D;
color orange = #FF6138;
color green = #468966;
color limeGreen = #BDF271;
color lightGreen = #BEEB9F;
color middleGreen = #79BD8F;
color blueGreen = #00A388;
color blue = #1E90FF;
color dullBlue = #348899;
color aqua = #29D9C2;
color lightBlue = #99CCFF;
color darkGrayBlue = #6D7889;
color lightGray = #D4D4D4;
color middleGray = #AAAAAA;
color darkGray = #4B4747;
color white = #F3F4F2;

//The colors we will use to set the look for the app
color bg;
color c1;
color c2;
color c3;

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

void setup() {
  size(400, 130);
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

void draw() {
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

void drawWord(String word, color c) {
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
String toString(int value) {
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

String hourToString() {
  if (hours == 0) return "";
  else return toString(hours);
}

String minuteToString() {
  if (minutes == 0) return "";
  else return toString(minutes);
}

String secondToString() {
  if (seconds == 0) return "";
  else return toString(seconds);
}

int getHour() {
  return time / 3600000;
}

int getMinute() {
  return (time / 60000) - (60 * hours);
}

int getSecond() {
  return (time / 1000) - (60 * minutes);
}

void keyPressed() {
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