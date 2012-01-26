package code 
package snippet 

import net.liftweb.util._
import Helpers._
import net.liftweb.http._

class MyWizard {

  private var firstName = ""
  private var lastName= ""
  private var age= 0
  private val whence = S.referer openOr "/"

  object NameVar extends RequestVar( "foo")
  object LastNameVar extends RequestVar("foo")
  object AgeVar extends RequestVar( 0)

  def firstScreen ={
    "#name" #> SHtml.text(firstName, firstName = _) &
    "type=submit" #> SHtml.submit(
      "Next",() => {
        S.redirectTo("/second",() => {
          NameVar.set(firstName)
        })
      }
    )
  }

  def secondScreen ={
    firstName= NameVar.is
    "#name" #> NameVar.is &
    "#lastname *" #> SHtml.text(lastName, lastName = _) &
    "type=submit" #> SHtml.submit("Next",
          () => {
                  S.redirectTo("/third",() => {
                    NameVar.set(firstName)
                    LastNameVar.set(lastName)
                  }
                  )
                } )

  }

  def thirdScreen ={
    firstName= NameVar.is
    lastName= LastNameVar.is
    "#name" #> NameVar.is &
    "#lastname *" #> LastNameVar.is &
    "#age *"        #> SHtml.text(age.toString , s => asInt(s).map(age = _)) &
    "type=submit" #> SHtml.submit("Next",
          () => {
            S.redirectTo("/final",() => {
                                NameVar.set(firstName)
                                LastNameVar.set(lastName)
                                AgeVar.set(age)
                              }
                              )
                } )

  }

  def finalScreen ={
    "#name *"       #> NameVar.is &
    "#lastname *"   #> LastNameVar.is &
    "#age *"        #> AgeVar.is
  }


}

