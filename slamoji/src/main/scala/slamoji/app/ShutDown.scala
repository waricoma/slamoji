package slamoji.app

object ShutDown {

  def main(args: Array[String]) {
    sys.ShutdownHookThread {
      println("exiting by ShutdownHookThread")
    }


  }
}
