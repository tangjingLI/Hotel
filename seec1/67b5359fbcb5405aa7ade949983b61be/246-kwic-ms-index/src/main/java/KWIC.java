// -*- Java -*-
/*
 * <copyright>
 *
 *  Copyright (c) 2002
 *  Institute for Information Processing and Computer Supported New Media (IICM),
 *  Graz University of Technology, Austria.
 *
 * </copyright>
 *
 * <file>
 *
 *  Name:    KWIC.java
 *
 *  Purpose: Demo system for practice in Software Architecture
 *
 *  Created: 11 Sep 2002
 *
 *  $Id$
 *
 *  Description:
 *    The basic KWIC system is defined as follows. The KWIC system accepts an ordered
 *  set of lines, each line is an ordered set of words, and each word is an ordered set
 *  of characters. Any line may be "circularly shifted" by repeadetly removing the first
 *  word and appending it at the end of the line. The KWIC index system outputs a
 *  listing of all circular shifts of all lines in alphabetical order.
 * </file>
 */

//package kwic.ms;

/*
 * $Log$
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

/**
 *  This class is an implementation of the main/subroutine architectural solution
 *  for the KWIC system. This solution is based on functional
 *  decomposition of the system. Thus, the system is decomposed into a number of
 *  modules, each module being a function. In this solution all functions share access
 *  to data, which is stored in the "core storage". The system is decomposed into the
 *  following modules (functions):
 *  <ul>
 *  <li>Master Control (main). This function controls the sequencing among the
 *  other four functions.
 *  <li>Input. This function reads the data lines from the input medium (file) and
 *  stores them in the core for processing by the remaining modules. The characters are
 *  stored in a character array (char[]). The blank space is used to separate words in
 *  a particular line. Another integer array (int[]) keeps the starting indices of
 *  each line in the character array.
 *  <li>Circular Shift. This function is called after the input function has
 *  completed its work. It prepares a two-dimensional integer array (int[][]) to keep
 *  track of all circular shifts. The array is organized as follows: for each circular
 *  shift, both index of its original line, together with the index of the starting word of
 *  that circular shift are stored as one column of the array.
 *  <li>Alphabetizing. This function takes as input the arrays produced by the input
 *  and circular shift functions. It produces an array in the same format (int[][])
 *  as that produced by circular shift function. In this case, however, the circular
 *  shifts are listed in another order (they are sorted alphabetically).
 *  <li>Output. This function uses the arrays produced by input and alphabetizing
 *  function. It produces a nicely formated output listing of all circular shifts.
 *  </ul>
 *  @author  dhelic
 *  @version $Id$
 */

public class KWIC{

//----------------------------------------------------------------------
/**
 * Fields
 *
 */
//----------------------------------------------------------------------

  /*
   * Core storage for shared data
   *
   */

  /**
   * Input characters
   *
   */

  private char[] chars_;

  /**
   * Array that keeps line indices (line index is the index of the first character of a line)
   *
   */

  private int[] line_index_;

  /**
   * 2D array that keeps circular shift indices (each circular shift index is a column
   * in this 2D array, the first index is the index of the original line from line_index_
   * array, the second index is the index of the starting word from chars_ array of
   * that circular shift)
   *
   */

  private int[][] circular_shifts_;

  /**
   * 2D array that keeps circular shift indices, sorted alphabetically
   *
   */

  private int[][] alphabetized_;

//----------------------------------------------------------------------
/**
 * Constructors
 *
 */
//----------------------------------------------------------------------

//----------------------------------------------------------------------
/**
 * Methods
 *
 */
//----------------------------------------------------------------------

//----------------------------------------------------------------------
  /**
   * Input function reads the raw data from the specified file and stores it in the core storage.
   * If some system I/O error occurs the program exits with an error message.
   * The format of raw data is as follows. Lines are separated by the line separator
   * character(s) (on Unix '\n', on Windows '\r\n'). Each line consists of a number of
   * words. Words are delimited by any number and combination of the space chracter (' ')
   * and the horizontal tabulation chracter ('\t'). The entered data is parsed in the
   * following way. All line separators are removed from the data, all horizontal tabulation
   * word delimiters are replaced by a single space character, and all multiple word
   * delimiters are replaced by a single space character. Then the parsed data is represented
   * in the core as two arrays: chars_ array and line_index_ array.
   * @param file Name of input file
   */

  public void input(String file){
    line_index_ = new int[32];
    chars_ = new char[2048];
    int char_count = 0;
    int line_count = 0;
    int c;
    boolean is_new_line = true;
    boolean is_new_word = false;
    boolean is_line_started = false;
    try {
      InputStream in = new FileInputStream(file);
      c = in.read();
      while (c != -1) {
        switch ((byte) c) {
          case '\n':
            is_new_line = true;
            break;
          case ' ':
            is_new_word = true;
            break;
          case '\t':
            is_new_word = true;
            break;
          case '\r':
            break;
          default:
            if (is_new_line) {
              if (line_count == line_index_.length) {
                int[] new_index = new int[line_count + 32];
                System.arraycopy(line_index_, 0, new_index, 0,
                        line_count);
                line_index_ = new_index;
              }
              line_index_[line_count] = char_count;
              line_count++;
              is_new_line = false;
              is_line_started = false;
            }

            if (is_new_word) {
              if (is_line_started) {
                if (char_count == chars_.length) {
                  char[] new_chars = new char[char_count + 2048];
                  System.arraycopy(chars_, 0, new_chars, 0,
                          char_count);
                  chars_ = new_chars;
                }
                chars_[char_count] = ' ';
                char_count++;
              }
              is_new_word = false;
            }
            if (char_count == chars_.length) {
              char[] new_chars = new char[char_count + 2048];
              System.arraycopy(chars_, 0, new_chars, 0, char_count);
              chars_ = new_chars;
            }
            chars_[char_count] = (char) c;
            char_count++;
            is_line_started = true;

            break;
        }
        c = in.read();
      }
      if (line_count != line_index_.length) {
        int[] new_index = new int[line_count];
        System.arraycopy(line_index_, 0, new_index, 0, line_count);
        line_index_ = new_index;
      }
      if (char_count != chars_.length) {
        char[] new_chars = new char[char_count];
        System.arraycopy(chars_, 0, new_chars, 0, char_count);
        chars_ = new_chars;
      }
    } catch (FileNotFoundException exc) {
      exc.printStackTrace();
      System.err.println("KWIC Error: Could not open " + file + "file.");
      System.exit(1);

    } catch (IOException exc) {
      exc.printStackTrace();
      System.err.println("KWIC Error: Could not read " + file + "file.");
      System.exit(1);
    }

  }

//----------------------------------------------------------------------
  /**
   * This function processes arrays prepared by the input
   * function and produces circular shifts of the stored lines. A circular
   * shift is a line where the first word is removed from the begin of a line
   * and appended at the end of the line. To obtain all circular shifts of a line
   * we repeat this process until we can't obtain any new lines. Circular shifts
   * are represented as a 2D array that keeps circular shift indices (each circular
   * shift index is a column in this 2D array, the first index is the index of
   * the original line from line_index_ array, the second index is the index of
   * the starting word from chars_ array of that circular shift)
   */

  public void circularShift(){
    circular_shifts_ = new int[2][256];
    int shift_count = 0;
    for (int i = 0; i < line_index_.length; i++) {
      int line_end = 0;
      if (i == (line_index_.length - 1))
        line_end = chars_.length;
      else
        line_end = line_index_[i + 1];
      for (int j = line_index_[i]; j < line_end; j++) {
        if ((chars_[j] == ' ') || (j == line_index_[i])) {
          if (shift_count == circular_shifts_[0].length) {
            int[] tmp = new int[shift_count + 256];
            System.arraycopy(circular_shifts_[0], 0, tmp, 0,
                    shift_count);
            circular_shifts_[0] = tmp;
            tmp = new int[shift_count + 256];
            System.arraycopy(circular_shifts_[1], 0, tmp, 0,
                    shift_count);
            circular_shifts_[1] = tmp;
          }
          circular_shifts_[0][shift_count] = i;
          circular_shifts_[1][shift_count] = (j == line_index_[i]) ? j
                  : j + 1;
          shift_count++;
        }

      }
    }
    if (shift_count != circular_shifts_[0].length) {
      int[] tmp = new int[shift_count];
      System.arraycopy(circular_shifts_[0], 0, tmp, 0, shift_count);
      circular_shifts_[0] = tmp;
      tmp = new int[shift_count];
      System.arraycopy(circular_shifts_[1], 0, tmp, 0, shift_count);
      circular_shifts_[1] = tmp;
    }
  }

//----------------------------------------------------------------------
  /**
   * This function sorts circular shifts lines alphabetically. The sorted shifts
   * are represented in the same way as the unsorted shifts with the only difference
   * that now they are ordered alphabetically. This function implements binary search
   * to sort the shifts.
   */

  public void alphabetizing(){
    alphabetized_ = new int[2][circular_shifts_[0].length];
    int alphabetized_count = 0;
    int low = 0;
    int high = 0;
    int mid = 0;
    for (int i = 0; i < alphabetized_[0].length; i++) {
      int line_number = circular_shifts_[0][i];
      int shift_start = circular_shifts_[1][i];
      int line_start = line_index_[line_number];
      int line_end = 0;
      if (line_number == (line_index_.length - 1))
        line_end = chars_.length;
      else
        line_end = line_index_[line_number + 1];
      char[] current_shift = new char[line_end - line_start];
      if (line_start != shift_start) {
        System.arraycopy(chars_, shift_start, current_shift, 0,
                line_end - shift_start);
        current_shift[line_end - shift_start] = ' ';
        System.arraycopy(chars_, line_start, current_shift, line_end
                - shift_start + 1, shift_start - line_start - 1);
      } else
        System.arraycopy(chars_, line_start, current_shift, 0, line_end
                - line_start);
      low = 0;
      high = alphabetized_count - 1;
      while (low <= high) {
        mid = (low + high) / 2;
        int mid_line_number = alphabetized_[0][mid];
        int mid_shift_start = alphabetized_[1][mid];
        int mid_line_start = line_index_[mid_line_number];
        int mid_line_end = 0;
        if (mid_line_number == (line_index_.length - 1))
          mid_line_end = chars_.length;
        else
          mid_line_end = line_index_[mid_line_number + 1];
        char[] mid_line = new char[mid_line_end - mid_line_start];
        if (mid_line_start != mid_shift_start) {
          System.arraycopy(chars_, mid_shift_start, mid_line, 0,
                  mid_line_end - mid_shift_start);
          mid_line[mid_line_end - mid_shift_start] = ' ';
          System.arraycopy(chars_, mid_line_start, mid_line,
                  mid_line_end - mid_shift_start + 1, mid_shift_start
                          - mid_line_start - 1);
        } else
          System.arraycopy(chars_, mid_line_start, mid_line, 0,
                  mid_line_end - mid_line_start);
        int length = Math.min(current_shift.length, mid_line.length);
        int compared = 0;
        for (int j = 0; j < length; j++) {
          if (current_shift[j] > mid_line[j]) {
            compared = 1;
            break;
          } else if (current_shift[j] < mid_line[j]) {
            compared = -1;
            break;
          }
        }
        if (compared == 0) {
          if (current_shift.length < mid_line.length)
            compared = -1;
          else if (current_shift.length > mid_line.length)
            compared = 1;
        }
        switch (compared) {
          case 1: // i-th line greater
            low = mid + 1;
            break;
          case -1: // i-th line smaller
            high = mid - 1;
            break;
          default: // i-th line equal
            low = mid;
            high = mid - 1;
            break;
        }
      }
      System.arraycopy(alphabetized_[0], low, alphabetized_[0], low + 1,
              alphabetized_count - low);
      System.arraycopy(alphabetized_[1], low, alphabetized_[1], low + 1,
              alphabetized_count - low);
      alphabetized_[0][low] = line_number;
      alphabetized_[1][low] = shift_start;
      alphabetized_count++;
    }
  }

//----------------------------------------------------------------------
  /**
   * This function prints the sorted shifts at the standard output.
   */

  public void output(){
    for (int i = 0; i < alphabetized_[0].length; i++) {
      int line_number = alphabetized_[0][i];
      int shift_start = alphabetized_[1][i];
      int line_start = line_index_[line_number];
      int line_end = 0;
      if (line_number == (line_index_.length - 1))
        line_end = chars_.length;
      else
        line_end = line_index_[line_number + 1];
      if (line_start != shift_start) {
        for (int j = shift_start; j < line_end; j++)
          System.out.print(chars_[j]);
        System.out.print(' ');
        for (int j = line_start; j < (shift_start - 1); j++)
          System.out.print(chars_[j]);
      } else
        for (int j = line_start; j < line_end; j++)
          System.out.print(chars_[j]);
      System.out.println();
    }
  }

//----------------------------------------------------------------------
  /**
   * This function controls all other functions in the system. It implements
   * the sequence of calls to other functions to obtain the desired functionality
   * of the system. Before any other function is called, main function checks the
   * command line arguments. The program expects exactly one command line argument
   * specifying the name of the file that contains the data. If the program have
   * not been started with proper command line arguments, main function exits
   * with an error message. Otherwise, the input function is called first to read the
   * data from the file. After that the circularShift and alphabetizing
   * functions are called to produce and sort the shifts respectivelly. Finally, the output
   * function prints the sorted shifts at the standard output.
   * @param args command line argumnets
   */

  public static void main(String[] args){
    KWIC kwic = new KWIC();
    kwic.input("Test_Case.txt");
    kwic.circularShift();
    kwic.alphabetizing();
    kwic.output();
  }

//----------------------------------------------------------------------
/**
 * Inner classes
 *
 */
//----------------------------------------------------------------------

}
