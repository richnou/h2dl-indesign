package odfi.h2dl.indesign

import java.io.File
import tcl.TclInterpreter
import nx.NXObject
import nx.NXObject._
import com.idyria.osi.wsb.webapp.mains.AppServer
import com.idyria.osi.wsb.webapp.MavenProjectWebApplication
import com.idyria.osi.aib.appserv.AIBAppServ
import com.idyria.osi.wsb.webapp.appserv.WSBServerApplication
import com.idyria.osi.wsb.webapp.appserv.AIBURLSourceWebApp
import com.idyria.osi.wsb.webapp.appserv.DeployWebApp
import com.idyria.osi.tea.logging.TeaLogging
import com.idyria.osi.wsb.webapp.WebApplication
import com.idyria.osi.tea.logging.TLog
import com.idyria.osi.wsb.webapp.http.connector.HTTPConnector
import com.idyria.osi.wsb.webapp.http.connector.HTTPProtocolHandler
import com.idyria.osi.wsb.webapp.http.connector.websocket.WebsocketProtocolhandler
import odfi.h2dl.indesign.h2dl.H2DLObject
import tcl.ExtendedTclList
import com.idyria.osi.wsb.webapp.db.OOXOODatabase
import com.idyria.osi.ooxoo.db.store.fs.FSStore
import odfi.h2dl.indesign.session.Sessions

/**
 * @author zm4632
 */
object InDesign extends App {

  println(s"Testing Sourcing Script")

 // TLog.setLevel(classOf[WebApplication], TLog.Level.FULL)
  //TLog.setLevel(classOf[HTTPConnector], TLog.Level.FULL)
  //TLog.setLevel(classOf[HTTPProtocolHandler], TLog.Level.FULL)
  
  
  //TLog.setLevel(classOf[WebsocketProtocolhandler], TLog.Level.FULL)

  // Create Interpreter
  //-------------

  var interpreter = new TclInterpreter
  interpreter.open

  var packages = """
/home/rleys/odfi/install/implementation-virtuoso/tcl 
/home/rleys/odfi/install/dev-hw/tcl 
/home/rleys/odfi/install/dev-tcl-scenegraph/tcl 
/home/rleys/odfi/install/implementation-physicaldesign/tcl 
/home/rleys/odfi/install/dev-tcl/tcl 
""".trim.split(" ").toList.map(_.trim)

  packages.foreach(f => interpreter.addPackageSource(new File(f)))

  interpreter.eval(new File("src/main/resources/tests/simple/simple_top_with_sim.tcl"))

  interpreter.eval(""" puts "Top is $top" """)

  var top: NXObject = interpreter.eval("$top")

  println(s"Got top")

  println(s"Top is of type: ${top("info class")}")

  var stop: NXObject = top("""findFirstInTree { $it isClass ::odfi::dev::hw::h2dl::sim2::Stop }""")

  println(s"Stop at time: " + stop("parent").toNXObject.absoluteTime)

  sys.addShutdownHook {
    interpreter.close
  }
  
  this.getRootH2DLInstances.foreach {
    instance => 
      println(s"Found Instance: $instance")
  }
  
  //sys.exit()
  //interpreter.close

  // Launch Web Stuff
  //  - Create AIB App server and add WSB Webapp as an application
  //-------------------------
  var server = new AIBAppServ
  server.applicationConfig.gui = true
  server.start

  // Add WSB Web App application
  var webserver = new WSBServerApplication
  webserver.initialState = Some("start")

  server.aib send webserver
  println(s"Wait for init")
  webserver.waitForState("init")
  println(s"State init was reached")

  // Create a new simple application
  var webapp = new AIBURLSourceWebApp(new File("src/main/webapp").toURI.toURL(), "/indesign")

  // Send to webserver 
  webserver.addChildApplication(webapp)
  


  
  // Session DB
  //---------------------
  var db = new OOXOODatabase(new FSStore(new File("db")))
  val sessionContainer = db.container("session")
  val sessionDoc = sessionContainer.documentWrapper("sessions.xml",new Sessions).get
  
  // UI
  //-----------------
  var uiApp = new IndesignUI
  uiApp.initialState = Some("start")
  server.aib send uiApp
  
  
  
  // Utilities for InDesign
  //-----------------------------
  def getRootH2DLInstances: List[H2DLObject] = {

    var objects = interpreter.eval("""
## Get Instances 
set nsfBaseObjects [lsort -unique [info commands ::nsf::*]]
set nsfObjects [odfi::flist::MutableList fromList $nsfBaseObjects]

set list [$nsfObjects filter {

    if {[::odfi::common::isClass $it odfi::dev::hw::h2dl::Instance] && [$it isRoot]==true} {
        return true
    } else {
        return false
    }
}]

$list toTCLList
""")

    objects match {
      case lst: ExtendedTclList => lst.toList.map(v => H2DLObject.NXObjectToH2DLObject(NXObject.fromTclValue(v)))
      case obj => List(NXObject.fromTclValue(obj))
    }

  }
}