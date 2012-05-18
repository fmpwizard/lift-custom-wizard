package code 
package snippet 

import net.liftweb.util._
import net.liftweb.common.Logger
import Helpers._
import net.liftweb.http._
import js.JsCmds

class MyWizard extends StatefulSnippet with Logger{

  def dispatch ={
    case "firstScreen" => firstScreen
    case "secondScreen" => secondScreen
    case "thirdScreen" => thirdScreen
    case "finalScreen" => finalScreen
  }

  /**
   * These variables keep the current
   * values of the submitted form
   * Because we are using StateFulSnippet, they maintain their values
   * after a page reload.
   */
  private var firstName = ""
  private var lastName= ""
  private var age= 0
  private var currentUrl= ""


  /**
   * Take a snapshot of our FirstRender RequestVar
   * S.uri will return the current url, and on first page load, it is like:
   * http://host.com/primero
   * but after the redirect, because we use StatefulSnippet, it will be like:
   * http://host.com/primero?FAWE233445fff=
   * which tells lift to keep track of the variables in this snippet.
   */
  def firstRender_? ={
    val urlParam= S.uriAndQueryString openOr(S.uri)
    if (S.uri == urlParam ) {
      redirectTo("/first")
    }
  }



  firstRender_?

  /**
   * First screen.
   * Note that we do not use S.redirectTo, we use the StatefulSnippet.redirectTo method,
   * which goes ahead and helps maintain the state across pages.
   */
  def firstScreen ={
    "#name"       #> JsCmds.FocusOnLoad(SHtml.text(firstName, firstName = _)) &
    "type=submit" #> SHtml.submit("Next",() => redirectTo("/second"))
  }

  /**
   * Second screen, note how we store the query String in a val, that we pass to the Back button.
   * This is so that when going back, the values entered on the previous screen are preserved.
   */
  def secondScreen ={
    val currentToken= S.queryString.openOr("")
    "#name *"     #> firstName &
    "#lastname *" #> JsCmds.FocusOnLoad(SHtml.text(lastName, lastName = _)) &
    "@back"       #> SHtml.button("Back", () => S.redirectTo("/first?" + currentToken)) &
    "@next"       #> SHtml.submit("Next", () => redirectTo("/third"))
  }

  /**
   * Same as the second screen
   */
  def thirdScreen ={
    val currentToken= S.queryString.openOr("")
    "#name *"     #> firstName &
    "#lastname *" #> lastName &
    "#age *"      #> JsCmds.FocusOnLoad(SHtml.text(age.toString , s => asInt(s).map(age = _))) &
    "@back"       #> SHtml.button("Back",() => S.redirectTo("/second?" + currentToken)) &
    "@next"       #> SHtml.submit("Next",() => redirectTo("/final"))
  }

  /**
   * This would be a confirmation page.
   * This shows how to use ajaxInvoke (thanks to
   * Torsten Uhlmann for the example!
   * And note how the second log entry shows you how you can manipulate the data submitted
   * on the server side.
   */
  def finalScreen ={
    "#name *"           #> firstName &
    "#lastname *"       #> lastName &
    "#age *"            #> age &
    "@finish [onclick]" #> SHtml.ajaxInvoke (() => {
      info("Data confirmed!")
      info("We got: \nName: %s\nLast name: %s\nAge: %s".format(firstName, lastName, age))
      JsCmds.Alert("We saved your \nName: %s\nLast name: %s\nAge: %s".format(firstName, lastName, age)) &
      JsCmds.JsHideId("finish")
    })
  }
}
