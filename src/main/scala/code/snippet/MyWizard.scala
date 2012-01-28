package code 
package snippet 

import net.liftweb.util._
import net.liftweb.common.Logger
import Helpers._
import net.liftweb.http._
import js.JsCmds

class MyWizard extends Logger{

  /**
   * These variables keep the current
   * values of the submitted form
   */
  private var firstName = ""
  private var lastName= ""
  private var age= 0
  private var whence= S.uriAndQueryString.openOr("/")

  /**
   * These objects pass values to the
   * next screen
   */
  object Whence extends RequestVar("/")
  object NameVar extends RequestVar("")
  object LastNameVar extends RequestVar("")
  object AgeVar extends RequestVar(0)


  /**
   * First screen. Note how we pass the referer and
   * first name values
   */
  def firstScreen ={
    "#name"       #> JsCmds.FocusOnLoad(SHtml.text(firstName, firstName = _)) &
    "type=submit" #> SHtml.submit(
      "Next",() => {
        S.redirectTo("/second",() => {
          NameVar.set(firstName)
          Whence.set(whence)
        })
      }
    )
  }

  /**
   * Second screen, note how we assign the values of some RequestVars
   * to some variables, (So they are available on the
   * S.redirectTo closure
   */
    def secondScreen ={
      firstName= NameVar.is
      whence= S.uriAndQueryString openOr ("/")

      "#name" #> NameVar.is &
      "#name" #> NameVar.is &
      "#lastname *" #> JsCmds.FocusOnLoad(SHtml.text(lastName, lastName = _)) &
      "@back" #> SHtml.button("Back",() => S.redirectTo(Whence.is)) &
      "@next" #> SHtml.submit("Next", () => {
            S.redirectTo("/third",() => {
              NameVar.set(firstName)
              LastNameVar.set(lastName)
              Whence.set(whence)
            }
          )
        }
      )
    }

  /**
   * Same as the second screen
   */
  def thirdScreen ={
    firstName= NameVar.is
    lastName= LastNameVar.is
    whence= S.uriAndQueryString openOr ("/")

    "#name" #> NameVar.is &
    "#lastname *" #> LastNameVar.is &
    "#age *"      #> JsCmds.FocusOnLoad(SHtml.text(age.toString , s => asInt(s).map(age = _))) &
    "@back"       #> SHtml.button("Back",() => S.redirectTo(Whence.is)) &
    "@next"       #> SHtml.submit("Next",() => {
          S.redirectTo("/final",() => {
            NameVar.set(firstName)
            LastNameVar.set(lastName)
            AgeVar.set(age)
            Whence.set(whence)
          }
        )
      }
    )
  }

  /**
   * This would be a confirmation page.
   * This shows how to use ajaxInvoke (thanks to
   * Torsten Uhlmann for the example!
   */
  def finalScreen ={
    "#name *"           #> NameVar.is &
    "#lastname *"       #> LastNameVar.is &
    "#age *"            #> AgeVar.is &
    "@finish [onclick]" #> SHtml.ajaxInvoke (() => {
      info("Data confirmed!")
      JsCmds.Alert("We saved your \nName: %s\nLast name: %s\nAge: %s".format(NameVar.is, LastNameVar.is,  AgeVar.is)) &
      JsCmds.JsHideId("finish")
    })
  }
}
