package odfi.h2dl.indesign.lib.ui

import com.idyria.osi.vui.core.stdlib.node.SGCustomNode
import com.idyria.osi.vui.core.VBuilder

class PropertyTable extends SGCustomNode with VBuilder{
  
  val pTable = table[(String, () => Any  )]
  
  def createUI = pTable {
    t => 
      t.column("Name") {
        c => 
          c.content {
            case (name,v) => name
          }
          
      }
      t.column("Value") {
        c => 
          c.content {
            case (name,v) => v()
          }
      }
  }
  
  def <=(name:String,v: => Any) = pTable.add(name,{ ()=> v})
  
}