package application

class QSorting {
  
  def sort(maxNumber: Array[Int], nameOfGame: Array[String]) {
    def swap(i: Int, j: Int) {
      val temp = maxNumber(i)
      maxNumber(i) = maxNumber(j)
      maxNumber(j) = temp

      val str = nameOfGame(i);
      nameOfGame(i) = nameOfGame(j);
      nameOfGame(j) = str;
    }

    def quickSort(low: Int, high: Int) {
      val pivot = maxNumber((low + high) / 2)
      var i = low
      var j = high
      while (i <= j) {
        while (maxNumber(i) > pivot) i += 1
        while (maxNumber(j) < pivot) j -= 1
        if (i <= j) {
          swap(i, j)
          i += 1
          j -= 1
        }
      }
      if (low < j) quickSort(low, j)
      if (j < high) quickSort(i, high)
    }
    quickSort(0, maxNumber.length - 1)
  }
  
  def sortingTime(maxNumber: Array[Long], nameOfGame: Array[String]) {
    def swap(i: Int, j: Int) {
      val temp = maxNumber(i)
      maxNumber(i) = maxNumber(j)
      maxNumber(j) = temp

      val str = nameOfGame(i);
      nameOfGame(i) = nameOfGame(j);
      nameOfGame(j) = str;
    }

    def quickSort(low: Int, high: Int) {
      val pivot = maxNumber((low + high) / 2)
      var i = low
      var j = high
      while (i <= j) {
        while (maxNumber(i) > pivot) i += 1
        while (maxNumber(j) < pivot) j -= 1
        if (i <= j) {
          swap(i, j)
          i += 1
          j -= 1
        }
      }
      if (low < j) quickSort(low, j)
      if (j < high) quickSort(i, high)
    }
    quickSort(0, maxNumber.length - 1)
  }
}