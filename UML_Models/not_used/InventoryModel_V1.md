@startuml

package InventoryModel{
class InvMgmt{
    +printInventory(tools: ResultSet): JsonObject
    +searchInventory(tool: ResultSet): JsonObject
    +checkItemQuantity(tool: ResultSet): JsonObject
    +makeSale(tool: ResultSet): JsonObject
    +returnTool(tool: ResultSet): JsonObject
    +generateOrder(orderLines: ResultSet): JsonObject
    +restock(toolID: int, amount: int): JsonObject
}

class Inventory{
    +Inventory(tools: ResultSet)
    +searchInventory(tool: ResultSet): JsonObject
    +checkItemQuantity(tool: ResultSet): JsonObject
    +makeSale(tool: ResultSet): JsonObject
    +returnTool(tool: ResultSet): JsonObject
}

class Tool{
    -toolID: int
    -name: String
    -quantity: int
    -price: double
    -supplierID: int

    +Tool(attributes: ResultSet)
    +encode(): JsonObject
}

class ElectricalTool extends Tool{
    -powerType: String
    +ElectricalTool(attributes: ResultSet)
    +encode(): JsonObject

}

class Procurement {
    +generateOrder(orders: ArrayList<OrderLine>): JsonObject
    +getSupplier(supplier: ResultSet): Supplier
}

class Supplier{
    -supplierID: int
    -companyName: String
    -address: String
    -salesContact: String

    +Supplier(attributes: ResultSet)
    +encode(): JsonObject
}

class InternationalSupplier extends Supplier{
    -importTax: double
    +InternationalSupplier(attributes: ResultSet)
    +encode(): JsonObject

}


class Order{
    -orderID: int
    -date: String
    -items: ArrayList<OrderLine>
    +Order(items: ArrayList<OrderLine>, date: String, id: int)
    +encode(): JsonObject
}

class OrderLine{
    -amount: int
    -toolID: int
    +OrderLine(toolId: int, amt: int)
    +encode(): JsonObject
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