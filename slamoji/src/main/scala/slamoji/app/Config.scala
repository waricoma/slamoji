package slamoji.app

import scala.io.Source
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization
import java.io.{ FileOutputStream=>FileStream, OutputStreamWriter=>StreamWriter }

class Config (filePath: String, updateAccessToken:Boolean) {

  if(updateAccessToken){
    implicit val formats = org.json4s.DefaultFormats
    val getJsonMapUpdated = getJsonMap() updated ("dl_token", java.util.UUID.randomUUID.toString)
    println(Serialization.write(getJsonMapUpdated).toString)
    val fileOutPutStream = new FileStream(filePath)
    val writer = new StreamWriter( fileOutPutStream, "UTF-8")
    writer.write(Serialization.write(getJsonMapUpdated).toString)
    writer.close()
  }

  def loadFile ():String = {
    var configText:String = ""
    Source.fromFile(filePath).foreach { line =>
      configText += line
    }
    return configText
  }

  def getJsonMap ():Map[String, Any] = {
    val jsonObj = parse(loadFile())
    return jsonObj.asInstanceOf[JObject].values
  }
}
