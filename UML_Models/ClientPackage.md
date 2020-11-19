@startuml

package Client{
	package ViewController{
		abstract class ViewController{
    		}
    		class InventoryController extends ViewController{
    		}
    		class CustomerController extends ViewController{
    		}
			class OrderWriter{
    		}

    	}
 
  	class ClientController {
  	}
  	
  	package View{
    		class CustomerView {
    		}
    		class InventoryView {
    		}
    		class GUI{
    		}

    		
    }

 	ClientController "1" ---o ViewController: Has a 
    InventoryController o--- "1" InventoryView: Has a
    CustomerController o--- "1" CustomerView: Has a
    GUI o--- "1" CustomerView: Has a
    GUI o--- "1" InventoryView: Has a
	InventoryController ...> OrderWriter: Uses

}

@enduml

https://stackoverflow.com/questions/33845152/split-gui-in-few-classes

    		CMSController "1" ---o  ViewController: Has a
    		IMSController "1" ---o ViewController: Has a