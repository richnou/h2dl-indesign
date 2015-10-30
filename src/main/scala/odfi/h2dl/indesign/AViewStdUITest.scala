package odfi.h2dl.indesign

import com.idyria.osi.vui.lib.view.StandardAViewCompiler
import odfi.h2dl.indesign.ui.std.ExampleFullUI
import com.idyria.osi.vui.core.components.main.VuiFrame
import com.idyria.osi.vui.javafx.JavaFXRun

object AViewStdUITest extends App {

  println(s"Hello")

  // Compile view from file 
  //var ui = StandardAViewCompiler.doCompile(new File(""))

  JavaFXRun.onJavaFX {
    var ui = StandardAViewCompiler.createView(classOf[ExampleFullUI]).render.asInstanceOf[VuiFrame[Any]]
    ui.show()
  }

}