package odfi.h2dl.indesign.lib.ui

import com.idyria.osi.vui.impl.html.view.HTMLView
import com.idyria.osi.vui.lib.view.AViewCompiler
import java.io.File

class StandaloneHTMLView extends HTMLView with StandaloneHTMLUIBuilder {

}

object StandaloneHTMLViewCompiler extends AViewCompiler[StandaloneHTMLView] {

  var eout = new File("target/classes")
  eout.mkdirs()
  compiler.settings2.outputDirs.setSingleOutput(eout.getAbsolutePath)

}