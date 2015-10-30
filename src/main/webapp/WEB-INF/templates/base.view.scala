package wwwviews

import com.idyria.osi.wsb.webapp.view.WWWView
import com.idyria.osi.wsb.webapp.lib.html.bootstrap.Bootstrap3
import com.idyria.osi.wsb.webapp.lib.html.bootstrap.BootstrapBuilder
import com.idyria.osi.wsb.webapp.view.WWWFastView
import java.io.File
import java.util.Formatter.DateTime
import java.util.Date
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import java.time.ZoneId
import nx.NXObject
import odfi.h2dl.indesign.InDesign
import odfi.h2dl.indesign.h2dl.H2DLObject
import com.idyria.osi.aib.core.utils.files.FileWatcher

class Base extends WWWFastView with BootstrapBuilder {

  //this.content {

  html {

    head {
      meta {
        attribute(" http-equiv" -> "X-UA-Compatible")
        attribute("content" -> "IE=edge")
      }

      meta {

        attribute("name" -> "viewport")
        attribute("content" -> "width=device-width, initial-scale=1.0")
      }

      stylesheet("http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css")
      stylesheet("http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css")

      
      script {
        attribute("src" -> "//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js")
      }
      
      javaScript("/js/indesign.js")
     

      // Js for local page 
      var jsPath = request.path.replaceAll("""\..*$""", "") + ".js"
      application.searchResource(jsPath) match {
        case Some(url) =>
          script {
            attribute("src" -> jsPath)
          }
        case None =>
        //  println(s"JS path not found: $jsPath // ${request.path} ")
      }

    }

    body {

      // Menu
      div {

        nav {
          classes("navbar navbar-default navbar-static-top")

          // container
          div {
            classes("container")

            // Head
            div {
              classes("navbar-header")

              a("InDesign2", "#") {
                classes("navbar-brand")
              }

            }
            // Navigation
            div {
              classes("navbar-collapse collapse")
              ul {
                classes("nav navbar-nav")

                li {
                  a("Home", "/index.view") {

                  }
                }
                li {
                  a("Script Runner", "/index.view") {

                  }
                }
                li {
                  a("SVG Viewer", "/svg.view") {

                  }
                }
                li {
                  a("Designs Explorer", "/test.view") {

                  }
                }
              }
            }

          }
        }
      }

      // Page
      //----------------------
      div {
        placePart("page")
      }
      

    }

  }

  // }

}
object Base {

  val watcher = new FileWatcher
  watcher.start

}