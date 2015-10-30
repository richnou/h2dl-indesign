package odfi.h2dl.indesign.h2dl.interpreter

import tcl.TclInterpreter
import tcl.ExtendedTclList
import odfi.h2dl.indesign.h2dl.H2DLObject
import nx.NXObject

class H2DLInterpreter extends TclInterpreter {

  def getRootH2DLInstances: List[H2DLObject] = {

    var objects = this.eval("""
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