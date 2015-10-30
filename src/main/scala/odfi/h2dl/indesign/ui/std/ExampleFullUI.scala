
package odfi.h2dl.indesign.ui.std

import com.idyria.osi.vui.lib.view.StandardAView
import com.idyria.osi.vui.lib.gridbuilder.GridBuilder
import com.idyria.osi.vui.lib.view.StandardAViewCompiler

class ExampleFullUI extends StandardAView with GridBuilder {
  
  this.content {
    frame {
      f => 
        f title("Aview Full UI Test")
     
        f <= grid {
          
          "-" row {
            button("Test") {
              b => 
                b.onClickFork {
                  println("Hello World Man!") 
                }
            }
          }
          "Another View" row {
            
            var n = StandardAViewCompiler.createView(classOf[ExampleViewWithButton]).render
            
            n 
            
          }
        }
        
    }
  }
  
}