package odfi.h2dl.indesign.lib.ui.semanticui

import com.idyria.osi.vui.core.components.containers.TabPaneBuilderInterface
import com.idyria.osi.vui.core.components.containers.VUITabPane
import com.idyria.osi.vui.impl.html.HtmlTreeBuilder
import com.idyria.osi.vui.impl.html.components.Div
import com.idyria.osi.vui.impl.html.components.HTMLNode
import com.idyria.osi.vui.impl.html.components.Html
import com.idyria.osi.vui.core.components.containers.VUITab

trait SemanticUIBuilder extends HtmlTreeBuilder with TabPaneBuilderInterface[Any] {

  // Module usage
  //----------------------

  // Building
  //--------------------
  override def html(cl: => Any): Html = {
    super.html {

      head {
        script {
          //attribute("src" -> "https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js")
          attribute("src" -> getClass.getClassLoader.getResource("js/jquery-2.1.4.min.js").toString())
          //attribute("src" -> new File("src/main/resources/js/jquery-1.11.3.min.js.js").toURI().toURL().toString())
        }

        stylesheet(getClass.getClassLoader.getResource("semantic/dist/semantic.min.css").toString())
        script {
          attribute("src" -> getClass.getClassLoader.getResource("semantic/dist/semantic.min.js").toString())
        }

        stylesheet(getClass.getClassLoader.getResource("semantic/dist/semantic.min.css").toString())
      }

      cl

      // Update head
      /*var headNode = currentNode.children.find { n => n.isInstanceOf[Head] } match {
        case Some(head) => head
        case None =>
      }
      onNode(headNode.asInstanceOf[HTMLNode]) {

      }*/
    }
  }

  // Language
  //---------------
  /*class ApplyClasses(cl: String) extends HtmlTreeBuilder {

    def apply(n:HTMLNode) : HTMLNode = {
      
      onNode(n) {
        addClasses(cl)
      }
      n
      
    }
    
  }
  
  implicit def convertStringToApplyClasses(str:String) : ApplyClasses = new ApplyClasses(str)*/

  // Tabs
  //------------------
  def tabpane = {
    
    // Create Container Div
    //---------------------------
    var tp = new Div with VUITabPane[Any] {
      
      "ui tabpane" :: this
      
      // Header
      //--------------
      var headerDiv = new Div {
        "ui top attached tabular menu" :: this
      }
      this.addChild(headerDiv)
      
     
      
      
      // Tab Add methods
      //-------------------
      def addTab[NT <: com.idyria.osi.vui.core.components.scenegraph.SGNode[Any]](title: String)(content: NT): com.idyria.osi.vui.core.components.containers.VUITab[Any] = {
        
       
        
        // Create Tab Div
        //----------------------
        var tabdiv = new Div with VUITab[Any] {
          
          "ui  bottom attached tab segment" :: this
          
          def setClosable(b:Boolean) = {
            
          }
          
          override def apply(cl: VUITab[Any] => Unit) : VUITab[Any] = {
            switchToNode(this, {
              cl(this)
            })
            this
          }
        }

        // Add to container
        this.addChild(tabdiv)
        
         // Add Content to tab
        tabdiv <= content
        
        // Create ID From Title
        //-------------------------
        var id = title.toLowerCase().replace(" ","_")
        tabdiv("data-tab" -> id)
        
        // Create Header Div (Header containre is first child of "this")
        //----------------------
        onNode(headerDiv) {
          "ui item" :: div {
            
            //-- First one is active
            if (headerDiv.children.size==1) {
              classes("active")
              "active" :: tabdiv
            }
           
            //-- Add Id 
            attribute("data-tab" -> id)
            
            //-- Add Header as text 
            text(title)
            
            
            
          }
        }
        
        tabdiv
        
      }
      
      def node[NT <: com.idyria.osi.vui.core.components.scenegraph.SGNode[Any]](title: String)(content: NT): NT = this.addTab(title)(content).asInstanceOf[NT]
    }
    
    // Configure Tab container
    //"ui tabular menu" :: tp
    //---------------------------------
    switchToNode(tp, {
      
    })
    
    tp
  }
  
}