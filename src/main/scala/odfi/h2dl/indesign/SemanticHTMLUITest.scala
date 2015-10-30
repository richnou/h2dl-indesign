package odfi.h2dl.indesign

import com.idyria.osi.vui.javafx.JavaFXRun
import com.idyria.osi.vui.core.VUIBuilder
import odfi.h2dl.indesign.lib.ui.StandaloneHTMLViewCompiler
import com.idyria.osi.vui.javafx.JavaFXNodeDelegate
import java.io.File
import com.idyria.osi.vui.javafx.web.JFXWeb
import odfi.h2dl.indesign.ui.html.TestSemanticUIView
import scala.languageFeature.implicitConversions
import com.idyria.osi.tea.io.TeaIOUtils
import com.idyria.osi.vui.lib.gridbuilder.GridBuilder
import java.io.StringWriter
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.Transformer

object SemanticHTMLUITest extends App with JFXWeb with GridBuilder {

  // UI
  //--------------------
  JavaFXRun.onJavaFX {

    val f = VUIBuilder.selectedImplementation[Any].frame()

    val ui = f {
      f =>
        f title ("InDesign")
        f.size(1024, 768)

        var w = browser
        //w.getEngine.load("http://localhost:8889/indesign/test.view")
        //var n = new JavaFXNodeDelegate(w)

        f <= grid {

          "-" row {

            button("Print Source") {
              b =>
                b.onClickFork {

                  var txt = w.delegate.asInstanceOf[javafx.scene.web.WebView].getEngine.getDocument.toString()
                  var doc = w.delegate.asInstanceOf[javafx.scene.web.WebView].getEngine.getDocument
                  var domFact = DocumentBuilderFactory.newInstance();

                  var domSource = new DOMSource(doc);
                  var writer = new StringWriter();
                  var result = new StreamResult(writer);
                  var tf = TransformerFactory.newInstance();
                  var transformer = tf.newTransformer();
                  transformer.transform(domSource, result);

                  println(s"${writer.toString()}")
                }
            }
          }

          "-" row {
            w using (expand, spread)
          }

        }

        // Prepare Test HTML
        //----------------

        var view = StandaloneHTMLViewCompiler.createView(classOf[TestSemanticUIView])

        var h = view.render

        var str = h.toString

        var str2 = """
<html>
    <head>
        <title>Canvas and SVG</title>
        <canvas style="border:3px solid darkseagreen;" width="200" height="100">          
        </canvas> 
        <svg>
            <circle cx="100" cy="100" r="50" stroke="black" 
                stroke-width="2" fill="red"/>
        </svg> 
<svg oncontextmenu="return false;" width="1344" height="610" class=""><defs><marker id="end-arrow" viewBox="0 -5 10 10" refX="6" markerWidth="3" markerHeight="3" orient="auto"><path d="M0,-5L10,0L0,5" fill="#000"></path></marker></defs><defs><marker id="start-arrow" viewBox="0 -5 10 10" refX="4" markerWidth="3" markerHeight="3" orient="auto"><path d="M10,-5L0,0L10,5" fill="#000"></path></marker></defs><path class="link dragline hidden" d="M655.6791056298122,198.87976079430993L667,320"></path><g><path class="link" d="M765.5204152025539,295.6393093673965L699.4686982760672,396.634531662542" style="marker-end: url(#end-arrow);"></path><path class="link" d="M680.3402816925559,403.97010447275704L581.1027095421503,334.34862816085814" style="marker-end: url(#end-arrow);"></path></g><g><g transform="translate(772.0885484796593,285.59642007923935)"><circle class="node" r="12" style="fill: rgb(31, 119, 180); stroke: rgb(21, 83, 125);"></circle><text x="0" y="4" class="id">0</text></g><g transform="translate(690.1638428001679,410.8619581540979)"><circle class="node reflexive" r="12" style="fill: rgb(255, 127, 14); stroke: rgb(178, 88, 9);"></circle><text x="0" y="4" class="id">1</text></g><g transform="translate(567.1859979730332,324.5851687789586)"><circle class="node" r="12" style="fill: rgb(44, 160, 44); stroke: rgb(30, 112, 30);"></circle><text x="0" y="4" class="id">2</text></g><g transform="translate(654.6551052786203,199.30008588893583)"><circle class="node" r="12" style="fill: rgb(255, 55, 57); stroke: rgb(149, 27, 28);"></circle><text x="0" y="4" class="id">3</text></g><g transform="translate(669.9389005235237,305.6689283950632)"><circle class="node" r="12" style="fill: rgb(148, 103, 189); stroke: rgb(103, 72, 132);" transform=""></circle><text x="0" y="4" class="id">4</text></g></g></svg>
    </body>
</html>
"""

        w.delegate.asInstanceOf[javafx.scene.web.WebView].getEngine.setJavaScriptEnabled(true)

        //w.delegate.asInstanceOf[javafx.scene.web.WebView].getEngine.getDocument.

        w.loadContent(str)

        //w.delegate.asInstanceOf[javafx.scene.web.WebView].getEngine.getDocument.

        println(h.toString())
        TeaIOUtils.writeToFile(new File("test.html"), str)

        
        
        var we = w.delegate.asInstanceOf[javafx.scene.web.WebView]
        w.delegate.asInstanceOf[javafx.scene.web.WebView].getEngine.getLoadWorker.stateProperty().addListener(new javafx.beans.value.ChangeListener[javafx.concurrent.Worker.State] {

          def changed(v: javafx.beans.value.ObservableValue[_ <: javafx.concurrent.Worker.State], old: javafx.concurrent.Worker.State, n: javafx.concurrent.Worker.State): Unit = {
            if (n == javafx.concurrent.Worker.State.SUCCEEDED) {
              println(s"LOADED")
              
              /*var window = w.getEngine.executeScript("window").asInstanceOf[netscape.javascript.JSObject]
              window.setMember("base", h)
              window.setMember("sys", Runtime.getRuntime)*/

              we.getEngine.setOnError(new javafx.event.EventHandler[javafx.scene.web.WebErrorEvent] {

                def handle(err: javafx.scene.web.WebErrorEvent) = {
                  println(s"ERROR")
                }

              })
              we.getEngine.setJavaScriptEnabled(true)
              we.getEngine.impl_getDebugger().setEnabled(true)
             /* we.getEngine.executeScript("""
//$("#ttbutton").text("CHANGED")
$(document).ready(function(){
	
	
	$("#ttbutton").text("CHANGED")

$.site('enable debug'); 
	
	$('.ui.shape').shape();
	$('.ui.dropdown').dropdown();
	//$('.ui.menu').menu();
	$('.ui.menu .item').tab();
	
	$('.shape').shape('flip up');


	// set up SVG for D3
	var width  = 400,
	    height = 400,
	    colors = d3.scale.category10();

	var svg = d3.select('#graph')
	  .append('svg')
	  .attr('oncontextmenu', 'return false;')
	  .attr('width', width)
	  .attr('height', height);

	// set up initial nodes and links
	//  - nodes are known by 'id', not by index in array.
	//  - reflexive edges are indicated on the node (as a bold black circle).
	//  - links are always source < target; edge directions are set by 'left' and 'right'.
	var nodes = [
	    {id: 0, reflexive: false},
	    {id: 1, reflexive: true },
	    {id: 2, reflexive: false}
	  ],
	  lastNodeId = 2,
	  links = [
	    {source: nodes[0], target: nodes[1], left: false, right: true },
	    {source: nodes[1], target: nodes[2], left: false, right: true }
	  ];

	// init D3 force layout
	var force = d3.layout.force()
	    .nodes(nodes)
	    .links(links)
	    .size([width, height])
	    .linkDistance(150)
	    .charge(-500)
	    .on('tick', tick)

	// define arrow markers for graph links
	svg.append('svg:defs').append('svg:marker')
	    .attr('id', 'end-arrow')
	    .attr('viewBox', '0 -5 10 10')
	    .attr('refX', 6)
	    .attr('markerWidth', 3)
	    .attr('markerHeight', 3)
	    .attr('orient', 'auto')
	  .append('svg:path')
	    .attr('d', 'M0,-5L10,0L0,5')
	    .attr('fill', '#000');

	svg.append('svg:defs').append('svg:marker')
	    .attr('id', 'start-arrow')
	    .attr('viewBox', '0 -5 10 10')
	    .attr('refX', 4)
	    .attr('markerWidth', 3)
	    .attr('markerHeight', 3)
	    .attr('orient', 'auto')
	  .append('svg:path')
	    .attr('d', 'M10,-5L0,0L10,5')
	    .attr('fill', '#000');

	// line displayed when dragging new nodes
	var drag_line = svg.append('svg:path')
	  .attr('class', 'link dragline hidden')
	  .attr('d', 'M0,0L0,0');

	// handles to link and node element groups
	var path = svg.append('svg:g').selectAll('path'),
	    circle = svg.append('svg:g').selectAll('g');

	// mouse event vars
	var selected_node = null,
	    selected_link = null,
	    mousedown_link = null,
	    mousedown_node = null,
	    mouseup_node = null;

	function resetMouseVars() {
	  mousedown_node = null;
	  mouseup_node = null;
	  mousedown_link = null;
	}

	// update force layout (called automatically each iteration)
	function tick() {
	  // draw directed edges with proper padding from node centers
	  path.attr('d', function(d) {
	    var deltaX = d.target.x - d.source.x,
	        deltaY = d.target.y - d.source.y,
	        dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY),
	        normX = deltaX / dist,
	        normY = deltaY / dist,
	        sourcePadding = d.left ? 17 : 12,
	        targetPadding = d.right ? 17 : 12,
	        sourceX = d.source.x + (sourcePadding * normX),
	        sourceY = d.source.y + (sourcePadding * normY),
	        targetX = d.target.x - (targetPadding * normX),
	        targetY = d.target.y - (targetPadding * normY);
	    return 'M' + sourceX + ',' + sourceY + 'L' + targetX + ',' + targetY;
	  });

	  circle.attr('transform', function(d) {
	    return 'translate(' + d.x + ',' + d.y + ')';
	  });
	}

	// update graph (called when needed)
	function restart() {
	  // path (link) group
	  path = path.data(links);

	  // update existing links
	  path.classed('selected', function(d) { return d === selected_link; })
	    .style('marker-start', function(d) { return d.left ? 'url(#start-arrow)' : ''; })
	    .style('marker-end', function(d) { return d.right ? 'url(#end-arrow)' : ''; });


	  // add new links
	  path.enter().append('svg:path')
	    .attr('class', 'link')
	    .classed('selected', function(d) { return d === selected_link; })
	    .style('marker-start', function(d) { return d.left ? 'url(#start-arrow)' : ''; })
	    .style('marker-end', function(d) { return d.right ? 'url(#end-arrow)' : ''; })
	    .on('mousedown', function(d) {
	      if(d3.event.ctrlKey) return;

	      // select link
	      mousedown_link = d;
	      if(mousedown_link === selected_link) selected_link = null;
	      else selected_link = mousedown_link;
	      selected_node = null;
	      restart();
	    });

	  // remove old links
	  path.exit().remove();


	  // circle (node) group
	  // NB: the function arg is crucial here! nodes are known by id, not by index!
	  circle = circle.data(nodes, function(d) { return d.id; });

	  // update existing nodes (reflexive & selected visual states)
	  circle.selectAll('circle')
	    .style('fill', function(d) { return (d === selected_node) ? d3.rgb(colors(d.id)).brighter().toString() : colors(d.id); })
	    .classed('reflexive', function(d) { return d.reflexive; });

	  // add new nodes
	  var g = circle.enter().append('svg:g');

	  g.append('svg:circle')
	    .attr('class', 'node')
	    .attr('r', 12)
	    .style('fill', function(d) { return (d === selected_node) ? d3.rgb(colors(d.id)).brighter().toString() : colors(d.id); })
	    .style('stroke', function(d) { return d3.rgb(colors(d.id)).darker().toString(); })
	    .classed('reflexive', function(d) { return d.reflexive; })
	    .on('mouseover', function(d) {
	      if(!mousedown_node || d === mousedown_node) return;
	      // enlarge target node
	      d3.select(this).attr('transform', 'scale(1.1)');
	    })
	    .on('mouseout', function(d) {
	      if(!mousedown_node || d === mousedown_node) return;
	      // unenlarge target node
	      d3.select(this).attr('transform', '');
	    })
	    .on('mousedown', function(d) {
	      if(d3.event.ctrlKey) return;

	      // select node
	      mousedown_node = d;
	      if(mousedown_node === selected_node) selected_node = null;
	      else selected_node = mousedown_node;
	      selected_link = null;

	      // reposition drag line
	      drag_line
	        .style('marker-end', 'url(#end-arrow)')
	        .classed('hidden', false)
	        .attr('d', 'M' + mousedown_node.x + ',' + mousedown_node.y + 'L' + mousedown_node.x + ',' + mousedown_node.y);

	      restart();
	    })
	    .on('mouseup', function(d) {
	      if(!mousedown_node) return;

	      // needed by FF
	      drag_line
	        .classed('hidden', true)
	        .style('marker-end', '');

	      // check for drag-to-self
	      mouseup_node = d;
	      if(mouseup_node === mousedown_node) { resetMouseVars(); return; }

	      // unenlarge target node
	      d3.select(this).attr('transform', '');

	      // add link to graph (update if exists)
	      // NB: links are strictly source < target; arrows separately specified by booleans
	      var source, target, direction;
	      if(mousedown_node.id < mouseup_node.id) {
	        source = mousedown_node;
	        target = mouseup_node;
	        direction = 'right';
	      } else {
	        source = mouseup_node;
	        target = mousedown_node;
	        direction = 'left';
	      }

	      var link;
	      link = links.filter(function(l) {
	        return (l.source === source && l.target === target);
	      })[0];

	      if(link) {
	        link[direction] = true;
	      } else {
	        link = {source: source, target: target, left: false, right: false};
	        link[direction] = true;
	        links.push(link);
	      }

	      // select new link
	      selected_link = link;
	      selected_node = null;
	      restart();
	    });

	  // show node IDs
	  g.append('svg:text')
	      .attr('x', 0)
	      .attr('y', 4)
	      .attr('class', 'id')
	      .text(function(d) { return d.id; });

	  // remove old nodes
	  circle.exit().remove();

	  // set the graph in motion
	  force.start();
	}

	function mousedown() {
	  // prevent I-bar on drag
	  //d3.event.preventDefault();

	  // because :active only works in WebKit?
	  svg.classed('active', true);

	  if(d3.event.ctrlKey || mousedown_node || mousedown_link) return;

	  // insert new node at point
	  var point = d3.mouse(this),
	      node = {id: ++lastNodeId, reflexive: false};
	  node.x = point[0];
	  node.y = point[1];
	  nodes.push(node);

	  restart();
	}

	function mousemove() {
	  if(!mousedown_node) return;

	  // update drag line
	  drag_line.attr('d', 'M' + mousedown_node.x + ',' + mousedown_node.y + 'L' + d3.mouse(this)[0] + ',' + d3.mouse(this)[1]);

	  restart();
	}

	function mouseup() {
	  if(mousedown_node) {
	    // hide drag line
	    drag_line
	      .classed('hidden', true)
	      .style('marker-end', '');
	  }

	  // because :active only works in WebKit?
	  svg.classed('active', false);

	  // clear mouse event vars
	  resetMouseVars();
	}

	function spliceLinksForNode(node) {
	  var toSplice = links.filter(function(l) {
	    return (l.source === node || l.target === node);
	  });
	  toSplice.map(function(l) {
	    links.splice(links.indexOf(l), 1);
	  });
	}

	// only respond once per keydown
	var lastKeyDown = -1;

	function keydown() {
	  d3.event.preventDefault();

	  if(lastKeyDown !== -1) return;
	  lastKeyDown = d3.event.keyCode;

	  // ctrl
	  if(d3.event.keyCode === 17) {
	    circle.call(force.drag);
	    svg.classed('ctrl', true);
	  }

	  if(!selected_node && !selected_link) return;
	  switch(d3.event.keyCode) {
	    case 8: // backspace
	    case 46: // delete
	      if(selected_node) {
	        nodes.splice(nodes.indexOf(selected_node), 1);
	        spliceLinksForNode(selected_node);
	      } else if(selected_link) {
	        links.splice(links.indexOf(selected_link), 1);
	      }
	      selected_link = null;
	      selected_node = null;
	      restart();
	      break;
	    case 66: // B
	      if(selected_link) {
	        // set link direction to both left and right
	        selected_link.left = true;
	        selected_link.right = true;
	      }
	      restart();
	      break;
	    case 76: // L
	      if(selected_link) {
	        // set link direction to left only
	        selected_link.left = true;
	        selected_link.right = false;
	      }
	      restart();
	      break;
	    case 82: // R
	      if(selected_node) {
	        // toggle node reflexivity
	        selected_node.reflexive = !selected_node.reflexive;
	      } else if(selected_link) {
	        // set link direction to right only
	        selected_link.left = false;
	        selected_link.right = true;
	      }
	      restart();
	      break;
	  }
	}

	function keyup() {
	  lastKeyDown = -1;

	  // ctrl
	  if(d3.event.keyCode === 17) {
	    circle
	      .on('mousedown.drag', null)
	      .on('touchstart.drag', null);
	    svg.classed('ctrl', false);
	  }
	}

	// app starts here
	svg.on('mousedown', mousedown)
	  .on('mousemove', mousemove)
	  .on('mouseup', mouseup);
	d3.select(window)
	  .on('keydown', keydown)
	  .on('keyup', keyup);
	restart();

});
""")*/
            //  we.getEngine.getLoadWorker.

              /*w.getEngine.executeScript("window.aaa.test(0)")
              w.getEngine.executeScript("window.sys.exit")
              window.eval("top.test")*/
            } else  if (n == javafx.concurrent.Worker.State.FAILED) {
              println(s"Somethign Failed")
            }
          }

        })
        
        
        /* var h = html {
          head {

          }

          body {
            h1("Test")

            div {
              var b = button("Test") {}
              b {
                b =>

                  b.onClickFork {
                    println(s"Hello World from button")
                  }
              }
            }
          }
        }*/

        /*w.getEngine.getLoadWorker.stateProperty().addListener(new javafx.beans.value.ChangeListener[javafx.concurrent.Worker.State] {

          def changed(v: javafx.beans.value.ObservableValue[_ <: javafx.concurrent.Worker.State], old: javafx.concurrent.Worker.State, n: javafx.concurrent.Worker.State): Unit = {
            if (n == javafx.concurrent.Worker.State.SUCCEEDED) {
              println(s"LOADED")
              var window = w.getEngine.executeScript("window").asInstanceOf[netscape.javascript.JSObject]
              window.setMember("base", h)
              window.setMember("sys", Runtime.getRuntime)

              w.getEngine.setOnError(new javafx.event.EventHandler[javafx.scene.web.WebErrorEvent] {

                def handle(err: javafx.scene.web.WebErrorEvent) = {
                  println(s"ERROR")
                }

              })
              w.getEngine.setJavaScriptEnabled(true)

              /*w.getEngine.executeScript("window.aaa.test(0)")
              w.getEngine.executeScript("window.sys.exit")
              window.eval("top.test")*/
            }
          }

        })

        w.getEngine.loadContent(h.toString())*/

        //w.getEngine.executeScript(script)

        // window.setMember("top",h);
        //println(s"HTML: ${h.toString}")
        println(s"HTML: ${h.getClass.getCanonicalName}")
      // println(s"Window: ${window.getClass.getCanonicalName}");

    }

    ui.show()
  }

}