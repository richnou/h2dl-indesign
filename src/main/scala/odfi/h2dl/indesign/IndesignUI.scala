package odfi.h2dl.indesign

import com.idyria.osi.vui.javafx.JavaFXRun
import com.idyria.osi.vui.core.VBuilder
import com.idyria.osi.vui.lib.gridbuilder.GridBuilder
import com.idyria.osi.aib.appserv.AIBApplication
import org.apache.batik.swing.JSVGCanvas
import com.idyria.osi.vui.javafx.JavaFXNodeDelegate
import javax.swing.SwingUtilities
import java.io.File
import com.idyria.osi.vui.impl.swing.SwingUtils
import odfi.h2dl.indesign.top.TopScript
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.ZoneId
import java.time.Instant
import javafx.scene.input.DragEvent
import javafx.event.EventHandler
import javafx.scene.input.TransferMode
import odfi.h2dl.indesign.top.ui.TopScriptUI
import com.idyria.osi.aib.core.utils.files.FileWatcher

class IndesignUI extends AIBApplication with GridBuilder {

  var watcher = new FileWatcher

  this.onStart {

    watcher.start

    JavaFXRun.onJavaFX {

      var ui = frame {
        f =>
          f title ("InDesign")
          f.size(1024, 768)

          f <= tabpane {
            tp =>
              tp(expand)

              // Top Scripts Tab
              //---------------------
              tp <= grid {
                currentGrid.group.name = "Top Scripts"

                var filesTable = table[TopScript]
                var topScriptsTabPane = tabpane

                "Files" row {

                  filesTable {
                    t =>
                      t(expandWidth)
                      t.column("Name") {
                        c =>
                          c.content {
                            script =>
                              script.path.getName
                          }
                      }
                      t.column("Path") {
                        c =>
                          c.content {
                            script =>
                              script.path.getAbsolutePath
                          }
                      }
                      t.column("Last Modified") {
                        c =>

                          c.content {
                            f => DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(f.path.lastModified()), ZoneId.systemDefault()))
                          }

                      }

                      t.column("Number of Top Objects") {
                        c =>

                          c.content {
                            f => DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(f.path.lastModified()), ZoneId.systemDefault()))
                          }

                      }

                      t.column("Actions") {
                        c =>

                          c.content {
                            script =>
                              button(s"Rerun") {
                                b =>
                                  b.onClickFork {
                                    script.rerun

                                  }
                              }
                          }

                      }

                      // Session Save/Restore
                      //-------------------
                      t.onObjectAdded {
                        script =>

                          println(s"Added Object: $script")
                          var topScripts = InDesign.sessionDoc.node.getCurrentSession.getValueOrCreate("topScripts")
                          topScripts.contents.find { c => c.toString() == script.path.getAbsolutePath } match {
                            case Some(found) =>
                            case _ =>
                              var c = topScripts.contents.add
                              c.dataFromString(script.path.getAbsolutePath)
                              InDesign.sessionDoc.sync
                          }

                          // Create UI
                          topScriptsTabPane <= new TopScriptUI(script)
                      }

                      // Try to handle drag and drop
                      //------------------------
                      t.base.asInstanceOf[javafx.scene.control.TableView[_]].setOnDragDropped(new EventHandler[DragEvent] {
                        def handle(e: DragEvent) = {
                          import scala.collection.JavaConversions._

                          e.getDragboard.getFiles.toList.foreach {

                            case f if (f.getAbsolutePath.endsWith(".tcl")) =>

                              t.add(new TopScript(f))
                            case _ =>
                          }
                          println(s"Drag Drop: " + e.getDragboard.getFiles)
                        }
                      })
                      t.base.asInstanceOf[javafx.scene.control.TableView[_]].setOnDragOver(new EventHandler[DragEvent] {
                        def handle(e: DragEvent) = {

                          println(s"Drag over: " + e.getDragboard.getFiles)
                          if (e.getDragboard.hasFiles()) {
                            e.acceptTransferModes(TransferMode.COPY, TransferMode.MOVE)
                            e.consume()
                          }
                        }
                      })
                  }

                  filesTable(expandWidth)
                }

                "TopScriptTabs" row {
                  topScriptsTabPane(expandWidth, expand, backgroundColor("white"))
                }

                // Init From Session
                //------------------
                InDesign.sessionDoc.node.getCurrentSession.getValueOrCreate("topScripts").contents.foreach {
                  c =>
                    var ts = new TopScript(new File(c))
                    filesTable.add(ts)
                  //topScriptsTabPane <= new TopScriptUI(ts)
                }

              }
              //--------------------
              // EOF Top Files

              // SVG
              //-----------------------------
              tp <= grid {
                currentGrid.group.name = "SVG"

                "Control" row {

                }

                "Viewer" row {

                  // Canvas 
                  var svgCanvas = new JSVGCanvas
                  svgCanvas.setEnableImageZoomInteractor(true)
                  svgCanvas.setEnableZoomInteractor(true)
                  svgCanvas.setEnableRotateInteractor(false)

                  // File 
                  var targetFile = new File("/home/rleys/git/adl/photoncam/building_blocks/serialiser/serialiser_layout.svg")

                  // Wrape 
                  var snode = new javafx.embed.swing.SwingNode();
                  SwingUtilities.invokeLater(new Runnable() {

                    def run() = {
                      snode.setContent(svgCanvas);
                      svgCanvas.setURI(targetFile.toURI.toString())
                    }
                  });

                  watcher.onFileChange(targetFile) {
                    SwingUtilities.invokeLater(new Runnable() {

                      def run() = {

                        svgCanvas.setURI(targetFile.toURI.toString())
                      }
                    });
                  }

                  var delegate = JavaFXNodeDelegate(snode)
                  delegate(expand)
                  delegate.onKeyTyped {

                    case 'f' =>
                      SwingUtils.invokeLater {
                        svgCanvas.resetRenderingTransform()
                      }

                    case _ =>
                  }
                  // delegate.size(400, 400)

                  delegate

                }
                /*p(expand)
                p.name = "SVG Viewer"*/

              }
              // EOF SVG Grid

              // WEB UI
              //--------------------------
              /*tp <= grid {
                currentGrid.group.name = "WEB"

                "w" row {
                  
                  var w = new javafx.scene.web.WebView()
                  w.getEngine.load("http://localhost:8889/indesign/test.view")
                  var n = new JavaFXNodeDelegate(w)
                  n(expand)
                  n
                  
                }
                
              }*/

          }
      }
      ui.show

    }

  }

}