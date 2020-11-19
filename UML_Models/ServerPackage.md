@startuml
package Server{
	package ServerController{
        class Server{
        }
        class ServerController{
        }
        class DBInit{
        }
        Server *--- "*" ServerController: Owns several
        Server ...> DBInit: Uses a
    }
    package ModelController{
        abstract class ModelController{
        }
        class InventoryController extends ModelController{
        }
        class CustomerController extends ModelController{
        }

    }
    
   package model{
        package InventoryModel{
        }
        package CustomerModel{
        }
    }
    
   package DBController{
    		abstract class DBController{
    		}
    		class InventoryDBController extends DBController{
    		}
    		class CustomerDBController extends DBController{
    		}

    }

}
    ServerController "1" ---o ModelController: Has a
    InventoryController o--- "1" InventoryDBController
    CustomerController o--- "1" CustomerDBController
    InventoryController ...> InventoryModel
    CustomerController ...> CustomerModel



@enduml


                DBController ... DBInit: Uses