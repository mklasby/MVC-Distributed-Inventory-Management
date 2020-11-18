@startuml

package InventoryModel{
class InvMgmt{
}

class Inventory{
}

abstract class Tool{
}

class HandTool extends Tool{
    
}

class ElectricalTool extends Tool{

}

class Procurement {

}

abstract class Supplier{
}

class InternationalSupplier extends Supplier{

}

class LocalSupplier extends Supplier{

}


class Order{
}

class OrderLine{
}


Inventory *--- "1...*" Tool: Generates 1 or more 
Procurement *--- "1" Order: Generates
Procurement *--- "1...*" Supplier: Has 

InvMgmt  ...>  OrderLine: Uses
InvMgmt *-- "1" Inventory: Creates
InvMgmt *-- "1" Procurement: Creates
Order o-- "1...*" OrderLine: Has

@enduml

Unused: 

    +addTool(tool: Tool): Tool
    +deleteTool(tool: ResultSet): Tool
    

    +addVendor(supplier: Supplier): Supplier
    +removeVendor(supplierID: int): Supplier
    -searchVendors(supplierID: int): Supplier
    -searchVendors(supplierName: String): Supplier
}



class dbReader{
    -{static}SUPPLIER_FILE: String
    -{static}TOOL_FILE: String
    -{static}br: BufferedReader

    -dbReader()
    +{static}readSuppliers(): VendorList
    +{static}readTools(): Inventory
    +{static}readOrder(fname: String): Order
}


    +Tool(toolID:int, description: String, quantity: int, price: double, supplierID: int, powerTool: boolean, powerType: String)

    class OrderWriter {
    -{static} workingDir: String
    -{static}bw: BufferedWriter

    -OrderWriter()
    +{static}writeOrder(order: Order): String
}