package wwwviews

import com.idyria.osi.wsb.webapp.view.WWWView
import com.idyria.osi.wsb.webapp.lib.html.bootstrap.Bootstrap3
import com.idyria.osi.wsb.webapp.lib.html.bootstrap.BootstrapBuilder
import com.idyria.osi.wsb.webapp.view.WWWView
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

class Test extends WWWView with BootstrapBuilder {

  this.content {

    
    compose("/WEB-INF/templates/base.view.scala") {

      parent =>

      
        println("Configuring BASE VIEW")
        
        parent.part("page") {
          
          case (application,request) => 
          div {
            h1("In Design Welcome")

            // Pick up some file
            //------------------------
            bs3Header(h2("Source Scripts"))
            bs3Table[java.io.File] {

              t =>
                t.column("Path") {

                  c =>

                    c.content {
                      f => f.getAbsolutePath
                    }
                }

                t.column("Last Modified") {
                  c =>

                    c.content {
                      f =>

                        DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(f.lastModified()), ZoneId.systemDefault()))
                    }
                }

                var f = new File("src/main/resources/tests/simple/simple_top_with_sim.tcl")
                t.add(f)
                Test.watcher.onFileChange(f) {
                  println(s"File changed")
                }
            }

            // Show Available Root Modules
            //----------------------------------
            bs3Header(h2("Top Instances"))
            bs3Table[H2DLObject] {
              t =>
                t.column("Object ID") {
                  c =>
                    c.content {
                      instance => instance.toString
                    }
                }

                t.column("Name") {
                  c =>
                    c.content {
                      instance => instance.name
                    }
                }

                t.column("Master") {

                  c =>
                    c.content {
                      instance => instance.master
                    }

                }

                t.column("File") {
                  c =>
                    c.content {
                      instance => instance.file
                    }

                }

                t.column("Line") {
                  c =>
                    c.content {
                      instance => instance.line
                    }

                }

                InDesign.getRootH2DLInstances.foreach(t.add(_))

            }
          }
          // EOF Top from part
        }
      // EOF Part

    }
    // EOF Compose
  }

}
object Test {

  val watcher = new FileWatcher
  watcher.start

}