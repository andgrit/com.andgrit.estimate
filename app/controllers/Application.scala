package controllers

import play.api._
import play.Logger
import play.api.mvc._
import play.api.libs.iteratee.Enumerator
import java.io.File

object Application extends Controller {

  def index = Action { request =>
    def recursiveListFiles(f: File): Array[File] = {
      val these = f.listFiles
      these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
    }
    val newrelicYml = new File("newrelic/newrelic.yml")
    var hello = if (newrelicYml.exists()) {
      scala.io.Source.fromFile(newrelicYml).mkString
    } else {
      newrelicYml.toString() + ": file not found"
    }
    hello = hello + "\n"
    val files = recursiveListFiles(new File("."))
    files.foreach{f: File => hello = hello + "\n" + f}
    Ok(views.html.h1("index")(hello))
    //Ok(hello + request.toString())
  }

  // def index = TODO

  def projects = Action { Ok("projects") }
  def project(pid: String) = Action { Ok("project: " + pid) }

  def players = Action { Ok("players") }
  def player(playerId: Long) = Action { Ok("player: " + playerId) }

  def games = Action { Ok("games") }
  def game(gameId: String) = Action { Ok("game: " + gameId) }

}
