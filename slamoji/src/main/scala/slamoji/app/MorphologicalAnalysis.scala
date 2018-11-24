package slamoji.app

import java.io.StringReader

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.codelibs.neologd.ipadic.lucene.analysis.ja.JapaneseTokenizer
import org.codelibs.neologd.ipadic.lucene.analysis.ja.tokenattributes.{PartOfSpeechAttribute}
// BaseFormAttribute

class MorphologicalAnalysis (targetText:String) {
  private val tokenizer = new JapaneseTokenizer(null, true, JapaneseTokenizer.DEFAULT_MODE)

  tokenizer.setReader(new StringReader(targetText))

  // private val baseForm = tokenizer.addAttribute(classOf[BaseFormAttribute])
  private val partOfSpeech = tokenizer.addAttribute(classOf[PartOfSpeechAttribute])
  private val charTerm = tokenizer.addAttribute(classOf[CharTermAttribute])

  def result:List[Map[String, String]] = {
    var strAndKindList:List[Map[String, String]] = List(Map())

    tokenizer.reset()
    while (tokenizer.incrementToken()) {
      val strAndKind:Map[String, String] = Map(charTerm.toString -> partOfSpeech.getPartOfSpeech().split('-')(0))
      strAndKindList = strAndKind :: strAndKindList
    }
    tokenizer.close()

    return strAndKindList.reverse
  }
}
