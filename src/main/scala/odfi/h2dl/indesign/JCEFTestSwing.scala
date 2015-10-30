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
import javafx.embed.swing.SwingNode
import com.idyria.osi.vui.impl.swing.builders.SwingNodeWrapper
import com.idyria.osi.vui.impl.swing.builders.SwingJComponentCommonDelegate



object JCEFTestSwing extends App with GridBuilder {

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
  var browser = client.createBrowser("file:///home/rleys/git/adl/h2dl-indesign/test.html", OS.isLinux(), false);
  var browserUI = browser.getUIComponent();

 

    val ui = frame {
      f =>
        f title ("InDesign")
        f.size(1024, 768)
        
        var jp = new JPanel
       
        
        
        
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
            browser.loadURL("file:///home/rleys/git/adl/h2dl-indesign/test.html")
           
            
       // var nd =  new JavaFXNodeDelegate(sn)
        
       // nd.delegate.minWidth(500)
        // nd.delegate.minHeight(500)
        
       /*f <= panel {
          p => 
            p.layout = hbox
            
            
       
            
            //p <=  new JavaFXNodeDelegate(snb)
            p <=  new SwingNodeWrapper(jp)
        }*/
            f <= new SwingJComponentCommonDelegate(jp)
       
        /*f <= grid {
          "--" row {
            
          }
          
          "-" row {
            
            
            (new JavaFXNodeDelegate(sn) using(expand))
            
            
          }
        }*/
        
        f.onClose {
          browser.close()
          cefApp_.dispose()
          
        }

    }
    ui.show()
  

}