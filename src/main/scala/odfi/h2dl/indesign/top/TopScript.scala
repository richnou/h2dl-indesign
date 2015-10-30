package odfi.h2dl.indesign.top

import java.io.File
import odfi.h2dl.indesign.h2dl.interpreter.H2DLInterpreter

class TopScript(val path: File) {

  // Intepreter
  val interpreter = new H2DLInterpreter
  interpreter.open

  // State
  var error: Option[Throwable] = None
  def clean = error == None

  def rerun = {
    try {
      interpreter.eval(path)
      this.error = None
    } catch {
      case e: Throwable =>this.error = Some(e)
    }
  }

}