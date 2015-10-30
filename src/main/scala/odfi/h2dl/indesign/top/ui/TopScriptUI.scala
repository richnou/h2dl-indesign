package odfi.h2dl.indesign.top.ui

import com.idyria.osi.vui.core.stdlib.node.SGCustomNode
import com.idyria.osi.vui.lib.gridbuilder.GridBuilder
import odfi.h2dl.indesign.top.TopScript
import odfi.h2dl.indesign.lib.ui.PropertyTable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.ZoneId
import java.time.Instant

class TopScriptUI(val topScript : TopScript) extends SGCustomNode with GridBuilder {

  this.name = topScript.path.getName 
  
  def createUI = grid {
    currentGrid.group.name = topScript.path.getName
    "Info" row subgrid {
      
      "-" row {
        
        var pt = new PropertyTable
        
        pt <= ("Name",topScript.path.getName)
        pt <= ("Last Modified", {DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(topScript.path.lastModified()), ZoneId.systemDefault()))})
        
        pt(expandWidth)
        
        
      }
    }
    
    
  }

}