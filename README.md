This is an example of how you could write a wizard like application
using Lift. Lift has a Wizard module, but in cases where you need 
full control of the generated html, or need some other
customization, this is one way to go.

To run this project, I included the plugins you need
as well as the sbt launcher (0.11.2)

So all you need to do is:

./sbt

>container:start

Go to http://127.0.0.1:8080

and enjoy!

You can read more about it on my [blog](http://blog.fmpwizard.com/scala-lift-custom-wizard)
