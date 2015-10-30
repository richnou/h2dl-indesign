package odfi.h2dl.indesign.ui.std

import com.idyria.osi.vui.lib.view.StandardAView
import com.idyria.osi.vui.lib.gridbuilder.GridBuilder

class ExampleViewWithButton extends StandardAView with GridBuilder {

  this.content {

    grid {
      "-" row {
        button("Created in a view  ") {
          b =>
            b.onClickFork {
              println("Created in view F")
            }
        }
      }

      "-" row {
        button("Created in a view G") {
          b =>
            b.onClickFork {
              println("Created in view G")
            }
        }
      }
    }

  }

}