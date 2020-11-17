@startuml

package Server{
    package ServerController{
        class Server{

        }
        class ServerController{

        }
        Server *--- "0...5" ServerController: Owns several
    }
    
    package ModelController{
        abstract class ModelController{

        }
        class InventoryController extends ModelController{

        }
        class CustomerController extends ModelController{

        }

        ServerController *--- "2" ModelController: Owns 2
        ModelController *--- "2" DBController: Owns 2
    }

    package InventoryModel{

    }

    package CustomerModel{

    }

    package DBController{

        abstract class DBController{

        }

        class InventoryDBController extends DBController{

        }

        class CustomerDBController extends DBController{

        }

        class DBInit{

        }

        DBController ... DBInit: Uses

    }

    InventoryController ... InventoryModel
    CustomerController ... CustomerModel
}



@enduml
