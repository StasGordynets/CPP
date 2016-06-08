package application

class ScalaStatistic {
  var averageTime = 0.00000000
  var averageScore = 0
  var averageStep = 0

  var timesNew = new Array[Double](50)
  var timeBigThan = 30.000

  def getField(fieldFilling: Array[Long]): Long =
    {
      val field = for (timeBigThan <- fieldFilling) yield timeBigThan
      println("Check = " + field.max + " " + field.size)
      field.max
    }

  def createStatistic(avrTime: Array[Long], avrScore: Array[Int], avrStep: Array[Int], size: Int) {
    for (i <- 0 until size) {
      averageTime += avrTime(i)
      averageScore += avrScore(i)
      averageStep += avrStep(i)

    }
    averageTime = averageTime / size
    averageScore = averageScore / size
    averageStep = averageStep / size
  }

  def getTime() = averageTime
  def getScore() = averageScore
  def getStep() = averageStep


  def sort(xs: Array[Long]): Array[Long] = {
    if (xs.length <= 1) xs
    else {
      val pivot = xs(xs.length / 2)
      Array.concat(
        sort(xs filter (pivot >)),
        xs filter (pivot ==),
        sort(xs filter (pivot <)))
    }
  }
}