@startuml
package "Client"{
    package ClientController{}
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