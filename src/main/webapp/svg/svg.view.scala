package wwwviews

import com.idyria.osi.wsb.webapp.lib.html.bootstrap.BootstrapBuilder
import com.idyria.osi.wsb.webapp.view.WWWView
import com.idyria.osi.wsb.webapp.view.WWWFastView
import java.time.Instant
import java.time.ZoneId
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.io.File
import com.idyria.osi.wsb.webapp.http.connector.websocket.WebsocketInterface
import com.idyria.osi.wsb.webapp.view.remote.PartReloadResponse
import com.idyria.osi.wsb.core.network.connectors.tcp.TCPNetworkContext
import java.net.InetSocketAddress
import com.idyria.osi.aib.core.utils.files.FileWatcher

/**
 * @author zm4632
 */
class SVGView extends WWWView with BootstrapBuilder {

  this.content {

    compose("/WEB-INF/templates/base.view.scala") {

      parent =>


        parent.part("page") {

          case (application, request) =>

            div {
              bs3Header(h1("SVG Viewer"))
              p {
                text("""Use this SVG Viewer to load some SVG file or a script that will create the SVG""")
              }

              // File Tables
              //-----------------------
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
                  var session = request.getSession
                  
                  request.networkContext.asInstanceOf[TCPNetworkContext].onClose {
                    
                    println(s"Closed connection to page, cleanup")
                    
                  }
                  
                  SVGView.watcher.onFileChange(f) {
                    
                    
                    
                    // Get Requesting Host 
                   /* var sa = request.networkContext.asInstanceOf[TCPNetworkContext].socketChannel.getRemoteAddress.asInstanceOf[InetSocketAddress]
                    
                    println(s"SVG File changed, target Client is: "+sa.toString())*/
                    
                    println(s"SVG nc is: "+request.networkContext.asInstanceOf[TCPNetworkContext])
                    println(s"SVG WS nc is: "+session[WebsocketInterface]("websocket").get.nc)
                     
                    // Prepare message
                    var update = new PartReloadResponse
                    update.id = "page"
                    update.content = parent.parts("page").render.toString()
                   session[WebsocketInterface]("websocket").get.writeSOAPPayload(update)
                    
                  }
              }
            }
        }
    }
  }

}

object SVGView {

  val watcher = new FileWatcher
  watcher.start

}