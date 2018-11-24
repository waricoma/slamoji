# slamoji
_slack kuromoji to cinema4d_

`cp ./slamoji-sample.db ./slamoji.db & cp ./config-sample.json ./config.json; vim ./config.json`  
+ `mysql_or_sqlite`
    + `mysql` or `sqlite`
+ `dl_token`
    + ⚠: ↓ run this command at complete of setting(`config.json` parameters other than `dl_token`)
    + `java -jar ./slamoji/target/scala-2.12/slamoji-assembly-0.1.jar ./config.json ./slamoji.db update_dl_token`

## Index
+ slamoji
+ slamoji-pipe
+ slamoji-c4d


## slamoji
`cd ./slamoji; java -jar ./target/scala-2.12/slamoji-assembly-0.1.jar`  
or  
`java -jar ./slamoji/target/scala-2.12/slamoji-assembly-0.1.jar ./config.json ./slamoji.db`  
### build.properties
`sbt.version = 1.2.6`  
### assembly.sbt
`addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.8")`

# slamoji-pipe

# slamoji-c4d
