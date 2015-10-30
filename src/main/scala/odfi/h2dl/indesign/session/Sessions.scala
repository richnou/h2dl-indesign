package odfi.h2dl.indesign.session

import com.idyria.osi.ooxoo.core.buffers.structural.xelement
import com.idyria.osi.ooxoo.core.buffers.datatypes.DateTimeBuffer


@xelement(name="Sessions")
class Sessions extends SessionsTrait {
  
    /**
     * Get/Create a Session with Id="current"
     */
    def getCurrentSession = {
      
      this.sessions.find { s => s.id.toString == "current" } match {
        case Some(c) => c 
        case None => 
          var c = this.sessions.add
          c.id ="current"
          c.createdOn = new DateTimeBuffer
          c
      }
      
    }
  
}