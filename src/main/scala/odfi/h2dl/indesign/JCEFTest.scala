package odfi.h2dl.indesign

import com.idyria.osi.vui.lib.gridbuilder.GridBuilder
import com.idyria.osi.vui.javafx.JavaFXRun
import org.cef.CefSettings
import org.cef.handler.CefAppHandlerAdapter
import org.cef.CefApp
import org.cef.OS
import javax.swing.JPanel
import com.idyria.osi.vui.javafx.JavaFXNodeDelegate
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import javax.swing.JButton


object JCEFTest extends App with GridBuilder {

  // (1) The entry point to JCEF is always the class CefApp. There is only one
  //     instance per application and therefore you have to call the method
  //     "getInstance()" instead of a CTOR.
  //
  //     CefApp is responsible for the global CEF context. It loads all
  //     required native libraries, initializes CEF accordingly, starts a
  //     background task to handle CEF's message loop and takes care of
  //     shutting down CEF after disposing it.
  CefApp.addAppHandler(new CefAppHandlerAdapter(null) {
    /*@Override
      public void stateHasChanged(org.cef.CefApp.CefAppState state) {
        // Shutdown the app if the native CEF part is terminated
        if (state == CefAppState.TERMINATED)
          System.exit(0);
      }*/
  });
  var settings = new CefSettings();
  settings.windowless_rendering_enabled = OS.isLinux();
 
  var cefApp_ = CefApp.getInstance(settings);

  // CEF Client
  //-------------------
  var client = cefApp_.createClient();

  // CEF Browser
  //-------------------
  var browser = client.createBrowser("http://www.google.com", OS.isLinux(), false);
  var browserUI = browser.getUIComponent();

  JavaFXRun.onJavaFX {

    val ui = frame {
      f =>
        f title ("InDesign")
        f.size(1024, 768)
        
        var jp = new JPanel
        
        
        var sn = new javafx.embed.swing.SwingNode()
        sn.setContent(jp)
        
        
        
        jp.setLayout(new BorderLayout)
        jp.add(browserUI,BorderLayout.CENTER)
        //jp.add(new JButton("test"),BorderLayout.CENTER)
            //browserUI.revalidate()
            //jp.revalidate()
            jp.setSize(500, 500)
            jp.setPreferredSize(new Dimension(500,500))
           // jp.setBackground(Color.GREEN)
            jp.setVisible(true)
            browserUI.setVisible(true)
            browser.loadURL("http://www.google.com")
           
            
       // var nd =  new JavaFXNodeDelegate(sn)
        
       // nd.delegate.minWidth(500)
        // nd.delegate.minHeight(500)
        
       f <= panel {
          p => 
            p.layout = hbox
            
            
            var snb = new javafx.embed.swing.SwingNode()
            snb.setContent(new JButton("test"))
            
            //p <=  new JavaFXNodeDelegate(snb)
            p <=  new JavaFXNodeDelegate(sn)
        }
       
        /*f <= grid {
          "--" row {
            
          }
          
          "-" row {
            
            
            (new JavaFXNodeDelegate(sn) using(expand))
            
            
          }
        }*/
        
       /* f.onClose {
          cefApp_.dispose()
        }*/

    }
    ui.show()
  }

}