@startuml

package Client{
    package ViewController{
        class ViewController{

        }

        class IMSController extends ViewController{

        }

        class CMSController extends ViewController{

        }
    }

    class ClientController{

    }


    package View{
        abstract class GenericView{

        }

        class CMSView extends GenericView{

        }

        class IMSView extends GenericView{

        }

        class GUI extends GenericView{

        }

        class OrderWriter{

        }

        GenericView ... OrderWriter: Uses
    }

    ClientController *... "2" ViewController: Owns 
    IMSController *... "1" IMSView: Owns a
    CMSController *... "1" CMSView: Owns a
    ViewController *... "1" GUI: Owns a
}

@enduml

https://stackoverflow.com/questions/33845152/split-gui-in-few-classes