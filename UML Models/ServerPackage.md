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
        Server "1" ...> "1" DBInit: Uses a
    }
    
    package ModelController{
        interface ModelController{

        }
        class InventoryController implements ModelController{

        }
        class CustomerController implements ModelController{

        }

        ServerController "1" ---o InventoryController: Has a
        ServerController "1 "---o CustomerController: Has a
        InventoryController o--- "1" DBController: Has a
        CustomerController o--- "1" DBController: Has a
    }

    package model{
        package InventoryModel{

        }

        package CustomerModel{

        }
    }



    package DBController{

        class DBController{

        }

        class InventoryDBController{

        }

        class CustomerDBController{

        }


        DBController *--- "1" InventoryDBController: Owns a
        DBController *--- "1" CustomerDBController: Owns a

    }

    InventoryController ... InventoryModel
    CustomerController ... CustomerModel
}



@enduml


                DBController ... DBInit: Uses