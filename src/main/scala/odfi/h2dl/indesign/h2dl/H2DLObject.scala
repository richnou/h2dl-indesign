package odfi.h2dl.indesign.h2dl

import nx.NXObject
import tcl.integration.TclObject
import tcl.TclInterpreter

/**
 * @author zm4632
 */
class H2DLObject(interpreter: TclInterpreter, obj: TclObject) extends NXObject(interpreter, obj) {
  
}

object H2DLObject {
  
  implicit def NXObjectToH2DLObject(nx:NXObject) : H2DLObject = new H2DLObject(nx.interpreter,nx.obj) 
}