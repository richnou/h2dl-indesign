  puts "In Script"

package require odfi::dev::hw::h2dl 2.0.0
package require odfi::dev::hw::h2dl::sim2 2.0.0

puts "In Script"

odfi::dev::hw::h2dl::module top {
    
       
 
    :sim:at 150 {
        :stop now
    }
    
}                        

   
return 0
       
## Get Instances 
set nsfBaseObjects [lsort -unique [info commands ::nsf::*]]
set nsfObjects [odfi::flist::MutableList fromList $nsfBaseObjects]
puts "NSD: $nsfObjects [$nsfObjects size] // [llength $nsfBaseObjects]"

set list [$nsfObjects filter {

    if {[::odfi::common::isClass $it odfi::dev::hw::h2dl::Instance] && [$it isRoot]==true} {
        return true
    } else {
        return false
    }
}]

puts "NSD: [$list size]"
$list foreach  {
   puts "-> $it"
}

foreach it  [lsort -unique [info commands ::nsf::*]] {
    #puts "** $it"
}

