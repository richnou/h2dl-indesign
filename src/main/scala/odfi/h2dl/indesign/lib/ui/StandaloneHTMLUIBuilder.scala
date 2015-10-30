package odfi.h2dl.indesign.lib.ui

import com.idyria.osi.vui.impl.html.HtmlTreeBuilder
import com.idyria.osi.vui.impl.html.components.Button
import com.idyria.osi.vui.impl.html.components.Html

trait StandaloneHTMLUIBuilder extends HtmlTreeBuilder {
  
  override def button(text:String)(cl:  => Any)= {
    
    var b = new StandaloneButton
    b.textContent = text
    
    this.switchToNode(b,{
      //attribute(("id","test"))
      cl
    })
    b
    
  }
  
  override def html(cl:  => Any) : Html = {
    
    var h = new StandaloneHTML 
    switchToNode(h, cl)
    h
    
  }
  
}

class StandaloneHTML extends Html {
  
  var actions = Map[String, () => Any]()
  
  def call(code:String) = {
    
   // println(s"Calling")
    actions.get(code) match {
      case Some(action) => action()
      case None => throw new RuntimeException("Cannot find action to call");
    }
    
  }
  def test(int:java.lang.Integer) = {
    println(s"Calling")
  }
  
  def drop(e: netscape.javascript.JSObject ) = {
    println(s"Calling Drop: "+e.getMember("dataTransfer").asInstanceOf[netscape.javascript.JSObject].call("getData","text"))
  }
}
class StandaloneButton extends Button {
  
  var action : Option[ () => Any] = None
  

  /**
   * Bind the execution of the Closure to a call through embedded JS
   */
  override def onClickFork(cl: => Any) = {
    //super.onClickFork(cl)
    
    action = Some({() => cl})
    println(s"In ONCLICK FORK")
    this("id" -> "test")
    //this.click
    this("onclick" -> s"""base.call('${this.hashCode().toHexString}')""")
    
    //this("onclick" -> s"""top.test()""")
    
    var h = this.getRootParent.asInstanceOf[StandaloneHTML]
    
    h.actions = h.actions + (this.hashCode().toHexString -> { () => cl } )
    
  }
  
  override def click = {
    action match {
      case Some(act) => act()
      case None => 
    }
  }
  
}