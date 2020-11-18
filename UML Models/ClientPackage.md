@startuml

package Client{
	package ViewController{
		abstract class ViewController{
    		}
    		class IMSController{
    		}
    		class CMSController{
    		}
    		CMSController "1" ---o  ViewController: Has a
    		IMSController "1" ---o ViewController: Has a
    	}
 
  	class ClientController {
  	}
  	
  	package View{
    		class CMSView {
    		}
    		class IMSView {
    		}
    		class GUI{
    		}
    		class OrderWriter{
    		}
    		GUI ...> OrderWriter: Uses
    }

 	ClientController "1" ---o ViewController: Has a 
    IMSController o--- "1" IMSView: Has a
    CMSController o--- "1" CMSView: Has a
    GUI o--- "1" CMSView: Has a
    GUI o--- "1" IMSView: Has a

}

@enduml

https://stackoverflow.com/questions/33845152/split-gui-in-few-classes