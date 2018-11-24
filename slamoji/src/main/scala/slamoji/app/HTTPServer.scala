package slamoji.app

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

class HTTPServer(dlToken:String, dbPath:String) {
  def start(port:Int) {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val route =
      path("") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to slamoji</h1>"))
        }
      } ~ path(s"slamoji-dl-${dlToken}") {
        get {
          getFromDirectory(dbPath)
        }
      }

    Http().bindAndHandle(route, "localhost", port)

    println(s"Server online at http://localhost:${port}/")
  }
}
