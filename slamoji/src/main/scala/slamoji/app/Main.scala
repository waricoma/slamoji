package slamoji.app

import slack.rtm.SlackRtmClient
import akka.actor.ActorSystem
import slack.SlackUtil

object Main {

  def main(args: Array[String]): Unit = {

    val config = new Config(if(args.length != 0) args(0) else "../config.json", if(args.length > 2 && args(2) == "update_dl_token") true else false)
    val configMap = config.getJsonMap()

    val server = new HTTPServer(configMap.get("dl_token").get.toString, if(args.length > 1) args(1) else "../slamoji.db")
    server.start(configMap.get("http_port").get.toString.toInt)

    val dbController = new DBController(configMap, if(args.length > 1) args(1) else "../slamoji.db")

    implicit val system = ActorSystem("slack")
    // implicit val ec = system.dispatcher

    val client = SlackRtmClient(configMap.get("slack_token").get.toString)
    val selfId = client.state.self.id
    // val state = client.state

    var slamoji:Boolean = false

    client.onMessage { message =>
      val mentionedIds = SlackUtil.extractMentionedIds(message.text)

      if(mentionedIds.contains(selfId)) {
        val inputMsg = message.text.replaceAll("<@" + selfId + ">", "").trim()
        if(inputMsg == "!slamoji") {
          slamoji = true
          client.sendMessage(message.channel, "slamoji start!")
        }else if(inputMsg == "!slamoji stop") {
          slamoji = false
          client.sendMessage(message.channel, "slamoji 💤")
        }else if(inputMsg == "!slamoji count") {
          client.sendMessage(message.channel, s"count: ${dbController.count()}")
        }
      }

      if(slamoji){
        val morphologicalAnalysis = new MorphologicalAnalysis(message.text)
        dbController.insertMorphologicalAnalysisResult(message.user, morphologicalAnalysis.result)
      }
    }

  }
}
