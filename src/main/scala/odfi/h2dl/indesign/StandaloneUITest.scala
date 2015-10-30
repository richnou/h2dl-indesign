package odfi.h2dl.indesign

import java.io.File
import com.idyria.osi.ooxoo.db.store.fs.FSStore
import com.idyria.osi.vui.core.VBuilder
import com.idyria.osi.vui.impl.html.HtmlTreeBuilder
import com.idyria.osi.vui.javafx.JavaFXNodeDelegate
import com.idyria.osi.vui.javafx.JavaFXRun
import com.idyria.osi.wsb.webapp.db.OOXOODatabase
import odfi.h2dl.indesign.session.Sessions
import com.idyria.osi.vui.impl.html.components.Button
import com.idyria.osi.vui.core.VUIBuilder
import odfi.h2dl.indesign.lib.ui.StandaloneHTMLUIBuilder
import odfi.h2dl.indesign.lib.ui.StandaloneHTMLViewCompiler

object StandaloneUITest extends App with StandaloneHTMLUIBuilder {

  // Session DB
  //---------------------
  var db = new OOXOODatabase(new FSStore(new File("db")))
  val sessionContainer = db.container("session")
  val sessionDoc = sessionContainer.documentWrapper("sessions.xml", new Sessions).get

  // UI
  //--------------------
  JavaFXRun.onJavaFX {

    var f = VUIBuilder.selectedImplementation[Any].frame()

    var ui = f {
      f =>
        f title ("InDesign")
        f.size(1024, 768)

        var w = new javafx.scene.web.WebView()
        //w.getEngine.load("http://localhost:8889/indesign/test.view")
        var n = new JavaFXNodeDelegate(w)

        f <= n

        // Prepare Test HTML
        //----------------
        
        var view = StandaloneHTMLViewCompiler.doCompile(new File("src/main/aview/ui/test.aview.scala").toURI().toURL())
        
        var h = view.newInstance().render
        
       /* var h = html {
          head {

          }

          body {
            h1("Test")

            div {
              var b = button("Test") {}
              b {
                b =>

                  b.onClickFork {
                    println(s"Hello World from button")
                  }
              }
            }
          }
        }*/

        w.getEngine.getLoadWorker.stateProperty().addListener(new javafx.beans.value.ChangeListener[javafx.concurrent.Worker.State] {

          def changed(v: javafx.beans.value.ObservableValue[_ <: javafx.concurrent.Worker.State], old: javafx.concurrent.Worker.State, n: javafx.concurrent.Worker.State): Unit = {
            if (n == javafx.concurrent.Worker.State.SUCCEEDED) {
              println(s"LOADED")
              var window = w.getEngine.executeScript("window").asInstanceOf[netscape.javascript.JSObject]
              window.setMember("base", h)
              window.setMember("sys", Runtime.getRuntime)

              w.getEngine.setOnError(new javafx.event.EventHandler[javafx.scene.web.WebErrorEvent] {

                def handle(err: javafx.scene.web.WebErrorEvent) = {
                  println(s"ERROR")
                }

              })
              w.getEngine.setJavaScriptEnabled(true)

              /*w.getEngine.executeScript("window.aaa.test(0)")
              w.getEngine.executeScript("window.sys.exit")
              window.eval("top.test")*/
            }
          }

        })

      
        
        w.getEngine.loadContent(h.toString())

        //w.getEngine.executeScript(script)

        // window.setMember("top",h);
        //println(s"HTML: ${h.toString}")
        println(s"HTML: ${h.getClass.getCanonicalName}")
      // println(s"Window: ${window.getClass.getCanonicalName}");

    }

    ui.show()
  }

}