package odfi.h2dl.indesign.session

import com.idyria.osi.ooxoo.core.buffers.structural.xelement

@xelement(name="Session")
class Session extends SessionTrait {
  

  
  def getValueOrCreate(name : String)  = {
    
    this.values.find { v => v.name.toString == name} match {
      case Some(v) => v 
      case None => 
        var v = this.values.add
        v.name = name
        v
    }
  }
  
}