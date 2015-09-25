puts "In Script"

package require odfi::dev::hw::h2dl 2.0.0
package require odfi::dev::hw::h2dl::sim2 2.0.0

puts "In Script"

odfi::dev::hw::h2dl::module top {
    
    
 
    :sim:at 150 {
        :stop now
    }
    
}