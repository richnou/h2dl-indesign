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

/**
 * @author zm4632
 */
object InDesign extends App {

  println(s"Testing Sourcing Script")

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
  
  var top : NXObject = interpreter.eval("$top")
  
  println(s"Got top")

  println(s"Top is of type: ${top("info class")}")
  
  var stop : NXObject = top("""findFirstInTree { $it isClass ::odfi::dev::hw::h2dl::sim2::Stop }""")

  println(s"Stop at time: "+stop("parent").toNXObject.absoluteTime)
  
  interpreter.close
  
  // Launch Web Stuff
  //  - Create AIB App server and add WSB Webapp as an application
  //-------------------------
  var server =  new AIBAppServ
  server.applicationConfig.gui = true 
  server.start
  
  // Add WSB Web App application
  var webserver = new WSBServerApplication
  server.aib send webserver
  println(s"Wait for init")
  webserver.waitForState("init")
  println(s"State init was reached")
  
  // Create a new simple application
  var webapp = new AIBURLSourceWebApp(new File("src/main/webapp").toURI.toURL(),"/indesign") 
  
  // Send to webserver 
  //webserver.aibBus.send(new DeployWebApp(webapp))
  webserver.addChildApplication(webapp)
  /*
  var server = new AppServer
  server.addHTTPConnector("localhost", 8888)
  
  //-- App
  var app = new MavenProjectWebApplication(new File("pom.xml"),"/indesign")
  server.addApplication(app)
  
  server.start()*/
}