package odfi.h2dl.indesign.ui.html

import odfi.h2dl.indesign.lib.ui.StandaloneHTMLView
import odfi.h2dl.indesign.lib.ui.semanticui.SemanticUIBuilder
import scala.languageFeature.postfixOps
import java.io.File
class TestSemanticUIView extends StandaloneHTMLView with SemanticUIBuilder {

  this.content {
    html {

      head {
        
        javaScript("http://d3js.org/d3.v3.min.js")
        
        stylesheet(new File("app.css").toURI().toURL().toString())
       
        javaScript(new File("app.js").toURI().toURL().toString())
      }

      body {

        h1("Hello")
        
        var b = button("Test") {
          
        }
        
        var r = ("ui button" :: b)
        "ui primary button" :: button("Test") {
          id("ttbutton")
        }
        
        // Tabs Support
        //----------
        tabpane {
          tp => 
            
            tp.addTab("A") {
              
              "ui button" :: button("AB") {
                
              }
                
            }
            tp.addTab("B") {
              
              "ui button" :: button("AB") {
                
              }
                
            }
        }
        
        
        // Graph Test
        //---------------------
        div {
          id("graph")
          
          
          
        }
       
        
        //"ui button" button("test") {}
      }

      
      
    }
    // EOF HTML
    
    
    
  }
  // EOF CONTENT
}