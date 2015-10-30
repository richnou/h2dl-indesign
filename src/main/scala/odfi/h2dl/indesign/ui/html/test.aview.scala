
package odfi.h2dl.indesign.ui.html

import odfi.h2dl.indesign.lib.ui.StandaloneHTMLView
import java.io.File

class TestView extends StandaloneHTMLView {

  this.content {

    html {
      head {

        /*meta {
          attribute(" http-equiv" -> "X-UA-Compatible")
          attribute("content" -> "IE=edge")
        }

        meta {

          attribute("name" -> "viewport")
          attribute("content" -> "width=device-width, initial-scale=1.0")
        }

        stylesheet("http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css")
        stylesheet("http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css")

        script {
          attribute("src" -> "//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js")
        }
       
*/
        script {
          //attribute("src" -> "https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js")
          attribute("src" -> new File("src/main/resources/js/jquery-2.1.4.min.js").toURI().toURL().toString())
          //attribute("src" -> new File("src/main/resources/js/jquery-1.11.3.min.js.js").toURI().toURL().toString())
        }

        stylesheet(new File("src/main/resources/semantic/dist/semantic.min.css").toURI().toURL().toString())
        script {
          attribute("src" -> new File("src/main/resources/semantic/dist/semantic.min.js").toURI().toURL().toString())
        }
        script {
          attribute("src" -> new File("src/main/resources/js/main.js").toURI().toURL().toString())
        }

      }

      body {
        h1("Test")
        classes("menu")

        div {
          var b = button("Test") {
            classes("ui", "primary", "button")
          }
          b {
            b =>

              b.onClickFork {
                println(s"Hello World from button")
              }
          }
        }

        div {
          text("""<div class="ui buttons">
  <button class="ui button">Cancel</button>
  <div class="or"></div>
  <button class="ui positive button">Save</button>
</div>""")
        }

        div {
          text("""<div class="ui text menu">
  <div class="item">
    <img src="/images/new-school.jpg">
  </div>
  <a class="browse item">
    Browse Courses
    <i class="dropdown icon"></i>
  </a>
  <div class="ui right dropdown item">
    More
    <i class="dropdown icon"></i>
    <div class="menu">
      <div class="item">Applications</div>
      <div class="item">International Students</div>
      <div class="item">Scholarships</div>
    </div>
  </div>
</div>
<div class="ui flowing basic admission popup">
  <div class="ui three column relaxed divided grid">
    <div class="column">
      <h4 class="ui header">Business</h4>
      <div class="ui link list">
        <a class="item">Design &amp; Urban Ecologies</a>
        <a class="item">Fashion Design</a>
        <a class="item">Fine Art</a>
        <a class="item">Strategic Design</a>
      </div>
    </div>
    <div class="column">
      <h4 class="ui header">Liberal Arts</h4>
      <div class="ui link list">
        <a class="item">Anthropology</a>
        <a class="item">Economics</a>
        <a class="item">Media Studies</a>
        <a class="item">Philosophy</a>
      </div>
    </div>
    <div class="column">
      <h4 class="ui header">Social Sciences</h4>
      <div class="ui link list">
        <a class="item">Food Studies</a>
        <a class="item">Journalism</a>
        <a class="item">Non Profit Management</a>
      </div>
    </div>
  </div>
</div>""")
        }

        div {
          text("""<div class="ui pointing menu">
			<a class="item"  data-tab="first"> Home </a> 
			<a class="item active"  data-tab="first2"> Messages </a> 
			<div class="right menu">
				<div class="item">
					<div class="ui transparent icon input">
						<input type="text" placeholder="Search..."> <i
							class="search link icon"></i>
					</div>
				</div>
			</div>
		</div>
		<div class="ui tab active" data-tab="first2">
			<p>2</p>
		</div>
		<div class="ui tab" data-tab="first">
			<p>1</p>
		</div>

""")
        }

        // DDOWN
        //--------------------
        div {
          text("""<div class="column">
		<h1 class="ui header">Dropdown menus</h1>
		<div class="ui divider"></div>
		<div class="ui compact menu">
			<div class="ui dropdown item dropdown-showcase">
				Dropdown <i class="dropdown icon"></i>
				<div class="menu">
					<div class="item active">Action</div>
					<div class="item">Another action</div>
					<div class="item">Something else here</div>
					<div class="ui horizontal divider"></div>
					<div class="item">Seperated link</div>
					<div class="item">One more seperated link</div>
				</div>
			</div>
		</div>
	</div>""")
        }
        
        // Shape
        //---------------
        div {
          
          text("""<div class="ui cube shape"  ondragover="allowDrop(event)" ondrop="base.drop(event)">
  <div class="sides">
    <div class="side">
      <div class="content">
        <div class="center">
          1
        </div>
      </div>
    </div>
    <div class="side active">
      <div class="content">
        <div class="center">
          2
        </div>
      </div>
    </div>
    <div class="side">
      <div class="content">
        <div class="center">
          3
        </div>
      </div>
    </div>
    <div class="side">
      <div class="content">
        <div class="center">
          4
        </div>
      </div>
    </div>
    <div class="side">
      <div class="content">
        <div class="center">
          5
        </div>
      </div>
    </div>
    <div class="side">
      <div class="content">
        <div class="center">
          6
        </div>
      </div>
    </div>
  </div>
</div>
""")
          
          
        }
        div {
          text("""<div class="ui ignored icon direction buttons">
        <div class="ui button" data-animation="flip" data-direction="left" title="Flip Left"><i class="left long arrow icon"></i></div>
        <div class="ui button" data-animation="flip" data-direction="up" title="Flip Up"><i class="up long arrow icon"></i></div>
        <div class="ui icon button" data-animation="flip" data-direction="down" title="Flip Down"><i class="down long arrow icon"></i></div>
        <div class="ui icon button" data-animation="flip" data-direction="right" title="Flip Right"><i class="right long arrow icon"></i></div>
      </div>""")
        }
      }
    }
  }

}