@startuml

ClientController o... Client

ClientController o... ViewController

ViewController o... View

ClientController "1" ... "1" ServerController

ServerController o... InventoryController

ServerController o... Server

ServerController o... DatabaseController

InventoryController o... Inventory

DatabaseController "1" ... "1" InventoryDatabase


@enduml