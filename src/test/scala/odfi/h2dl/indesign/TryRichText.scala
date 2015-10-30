package odfi.h2dl.indesign

import com.idyria.osi.vui.lib.gridbuilder.GridBuilder
import com.idyria.osi.vui.javafx.JavaFXRun
import org.fxmisc.richtext.InlineStyleTextArea
import java.awt.Color
import java.time.format.TextStyle
import org.fxmisc.richtext.CodeArea
import com.idyria.osi.vui.javafx.JavaFXNodeDelegate
import org.fxmisc.richtext.LineNumberFactory

object TryRichText extends App with GridBuilder {
  
  JavaFXRun.onJavaFX {
    
    
    var ui = frame {
      f => 
        f.size(1024,768)

        
        // RichText
        var codeArea = new CodeArea();
        codeArea.replaceText(0, 0, "package require odfi::h2dl");
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        
        f <= grid {
          
          "-" row {
            (new JavaFXNodeDelegate(codeArea) using(expand))
          }
          
        }
        
       
    }
    
    ui.show()
    
  }
  
}