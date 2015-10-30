package odfi.h2dl.indesign.session

import com.idyria.osi.ooxoo.model.producers
import com.idyria.osi.ooxoo.model.ModelBuilder
import com.idyria.osi.ooxoo.model.producer
import com.idyria.osi.ooxoo.model.out.markdown.MDProducer
import com.idyria.osi.ooxoo.model.out.scala.ScalaProducer

@producers(Array(
  new producer(value = classOf[ScalaProducer]),
  new producer(value = classOf[MDProducer])))
object SessionModel extends ModelBuilder {

  "Session" is {
    attribute("id")
    attribute("createdOn") ofType ("time")
    elementsStack.head.makeTraitAndUseCustomImplementation

    // parameters content 
    "Value" multiple {
      attribute("name")
      ofType("string")
      
      "Content" multiple "cdata"
    }
  }

  "Sessions" is {

    importElement("Session").setMultiple
    elementsStack.head.makeTraitAndUseCustomImplementation
  }

}