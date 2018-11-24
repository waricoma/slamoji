package slamoji.app

import scalikejdbc.{ConnectionPool, DB, SQL}
// scalikejdbc.SQLInterpolation._

class DBController (connectionInf:Map[String, Any], dbPath:String) {

  if(connectionInf.get("mysql_or_sqlite").get.toString == "mysql"){
    Class.forName("com.mysql.jdbc.Driver")
    ConnectionPool.singleton("jdbc:mysql://" + connectionInf.get("host").get.toString + "/" + connectionInf.get("db").get.toString + "?characterEncoding=UTF-8", connectionInf.get("db_user").get.toString, connectionInf.get("db_password").get.toString)
  }else if(connectionInf.get("mysql_or_sqlite").get.toString == "sqlite"){
    Class.forName("org.sqlite.JDBC")
    ConnectionPool.singleton(s"jdbc:sqlite:${dbPath}", connectionInf.get("db_user").get.toString, connectionInf.get("db_password").get.toString)
  }

  def insertMorphologicalAnalysisResult(userId:String, result:List[Map[String, String]]): Unit = {

    DB localTx { implicit session =>
      result.foreach( strAndKind =>
        strAndKind.keys.foreach(str => {
          val kind = strAndKind.get(str).get
          if(connectionInf.get("mysql_or_sqlite").get.toString == "mysql") {
            SQL(s"insert into slamoji (user_id, str, kind, created_at) values ('${userId.toString}', '${str.toString}', '${kind.toString}', unix_timestamp())").update.apply()
            // SQL(s"insert into slamoji (user_id, str, kind, created_at) values ('${userId.toString}', '${str.toString}', '${kind.toString}', now())").update.apply()
          }else if(connectionInf.get("mysql_or_sqlite").get.toString == "sqlite") {
            SQL(s"insert into slamoji (user_id, str, kind, created_at) values ('${userId.toString}', '${str.toString}', '${kind.toString}', strftime('%s', 'now'))").update.apply()
            // SQL(s"insert into slamoji (user_id, str, kind, created_at) values ('${userId.toString}', '${str.toString}', '${kind.toString}', datetime('now', 'localtime'))").update.apply()
          }
        })
      )
    }
  }

  def count(): Int = {
    val countByTable: List[Map[String, Any]] = DB readOnly { implicit session =>
      SQL("select count(id) from slamoji").map(rs => rs.toMap).list.apply()
    }
    var countNumByTableForReturn:Int = 0
    countByTable.foreach(countNumByTable => {
      countNumByTableForReturn = countNumByTable.get("count(id)").get.toString.toInt
    })
    return countNumByTableForReturn
  }

}
