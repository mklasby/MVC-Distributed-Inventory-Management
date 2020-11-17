@startuml

ClientController o--- Client

ClientController o--- ViewController

ViewController o--- View

ClientController  <...>  ServerController

ServerController o--- InventoryController

ServerController o--- Server

ServerController o--- DatabaseController

InventoryController o--- Inventory


@enduml
DatabaseController <...> InventoryDatabase