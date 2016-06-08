package application

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

class PopularStat {

  def getCell(coordinates: Array[Int], keysForCoordinates: Array[Int], amount: Int): Int =
    {
      // create an empty map
      var states = scala.collection.mutable.Map[Int, Int]()

      // Create map with value
      for (i <- (0).to(amount - 1)) {
        states += (coordinates(i) -> keysForCoordinates(i))
      }
      // println("Keys : " + states.keys)  // for check in console 
      //println("Values  : " + states.values)

      var cell = 0
      states.toList sortBy (_._2) foreach {
        case (key, value) =>
          println(key + " = " + value)
          cell = key
      }
      println("Keys : " + cell)
      cell
    }

  def getField(fieldFilling: Array[Int], keysForField: Array[Int], amount: Int): Int =
    {
      // create an empty map
      var states = scala.collection.mutable.Map[Int, Int]()

      // Create map with value
      for (i <- (0).to(amount - 1)) {
        states += (keysForField(i) -> fieldFilling(i))
      }

      var field = 0
      states.toList sortBy (_._2) foreach {
        case (key, value) =>
          println(key + " = " + value)
          field = value
      }
      println("Field : " + field)
      field
    }

  def winsFirstPlayer(winner: Array[Int], amount: Int): Int =
    {
      var first = 0
      for (i <- (0).to(amount - 1)) {
        if (winner(i) == 1) first = first + 1
      }
      println("Number of 1 = " + first)
      first
    }

  def winsSecondPlayer(winner: Array[Int], amount: Int): Int =
    {
      var second = 0
      for (i <- (0).to(amount - 1)) {
        if (winner(i) == 2) second = second + 1
      }
      println("Second of 1 = " + second)
      second
    }

  def findMaxSeq(fieldFilling: Array[Array[Array[Int]]], mas1: Array[Int], replays: Int): Array[Int] = {
    var sum = 0
    var amount = 0

    for (i <- (0).to(replays - 1)) {
      var find = i
      for (l <- (0).to(replays - 1)) {
        sum = 0
        for (k <- (20).to(39)) {
          for (j <- (0).to(9)) {

            if (fieldFilling(find)(k)(j) == fieldFilling(l)(k)(j)) {
              sum = sum + 1
            }
            if (sum == 200) {
              amount = amount + 1
            }
          }
        }
      }
      mas1(i) = amount
      amount = 0
    }
    var findMaxRepeat = 0
    var currentPosition = 0
    for (i <- (0).to(replays - 1)) {
      if (mas1(i) > findMaxRepeat) {
        val change = mas1(i)
        mas1(i) = findMaxRepeat
        findMaxRepeat = change
        currentPosition = i
      }
    }

    var result: Array[Int] = new Array[Int](3)
    result(0) = findMaxRepeat
    result(1) = currentPosition
    result
  }
}