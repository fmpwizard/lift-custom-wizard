package code 
package snippet 

import net.liftweb.util._
import Helpers._
import net.liftweb.http._

class MyWizard {

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
    "#name" #> SHtml.text(firstName, firstName = _) &
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
   * S.redirect closure
   */
  def secondScreen ={
    firstName= NameVar.is
    whence= S.uriAndQueryString openOr ("/")

    "#name" #> NameVar.is &
    "#name" #> NameVar.is &
    "#lastname *" #> SHtml.text(lastName, lastName = _) &
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
    "#age *"        #> SHtml.text(age.toString , s => asInt(s).map(age = _)) &
    "@back" #> SHtml.button("Back",() => S.redirectTo(Whence.is)) &
    "@next" #> SHtml.submit("Next",() => {
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
   * This would be a confirmation page. But we just include
   * a back button
   */
  def finalScreen ={
    "#name *"       #> NameVar.is &
    "#lastname *"   #> LastNameVar.is &
    "#age *"        #> AgeVar.is &
    "@back" #> SHtml.button("Back",() => S.redirectTo(Whence.is))
  }
}
