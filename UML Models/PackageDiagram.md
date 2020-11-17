@startuml
package "Client"{
    class ClientController{}
    package ViewController{}
    package View{}
}

package "Server"{
    package ServerController{}
    package DatabaseController{}
    package Model{}
    package ModelController{}
}

ClientController ... ServerController
ClientController ... ViewController
ViewController ... View

ModelController ... Model
ModelController ... DatabaseController
ServerController ... ModelController

@enduml

database "mySQL"{

}
DatabaseController ... mySQL