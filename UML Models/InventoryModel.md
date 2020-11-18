@startuml

package InventoryModel{
class InvMgmt{
    -inventory: Inventory
    -procurement: Procurement
    -------------------------
    +InvMgmt()
    +printInventory(): String
    +searchInventory(name: String): String
    +searchInventory(toolID: int): String 
    +checkItemQuantity(toolID: int): String
    +checkItemQuantity(name: String): String
    +makeSale(toolID: int): String
    +returnTool(toolID: int): String
    +generateOrder(): String
    +receiveOrder(fname: String): String
}

class Inventory{
    -tools: ArrayList<Tool>
    -pendingOrders: ArrayList<OrderLine>

    +Inventory(tools: ArrayList<Tool>)
    +searchInventory(toolName: String): String
    +searchInventory(toolID: int): String
    +checkItemQuantity(toolID: int): String
    +checkItemQuantity(name: String): String
    +makeSale(toolID: int): String
    +returnTool(toolID: int): String
    +receiveOrder(orderLines: ArrayList<OrderLine>): String
    +returnTool(toolID: int): String
    +populatePendingOrders(): void
    -restock(tool: Tool): void
    -getTool(int: toolID): Tool
    -getTool(name: String): Tool
    -toolNotFound(query: <V>): String
    
}

class Tool{
    -toolID: int
    -name: String
    -quantity: int
    -price: double
    -supplierID: int
    -powerTool: boolean
    -powerType: String
}

class Procurement {
    -orders: ArrayList<Order>
    -vendors: ArrayList<Supplier>

    +Procurement(vendors: VendorList)
    +generateOrder(orders: ArrayList<OrderLine>): String
    +getVendor(supplierID: int): Supplier
    +getNameByID(supplierID: int): String
    -writeOrder(order: Order): void
    -getNextOrderID(): int
    -getOrder(value: <V>): Order
}

class Supplier{
    -supplierID: int
    -companyName: String
    -address: String
    -salesContact: String
    -internationalSupplier: Boolean
    -importTax: double
}

class Order{
    -orderID: int
    -date: String
    -items: ArrayList<OrderLine>
    
    +Order(items: ArrayList<OrderLine>, date: String, id: int)
    +toString(): String
    +getOrderID(): int
    +setOrderID(id: int): void
    +getDate(): String
    +setDate(date: String): void
    +getItems(): ArrayList<OrderLine>
    +setItems(): ArrayList<OrderLine>
    +addItem(item: OrderLine): void
    +deleteItem(item: OrderLine): OrderLine
    +deleteItem(desc: String): OrderLine
}

class OrderLine{
    -description: String
    -amount: int
    -supplier: String
    -supplierID: int
    -toolID: int
    
    +OrderLine(toolID: int, desc: String, amt: int, supplierID: int, supplier: String)
    +getDescription(): String
    +setDescription(desc: String): void
    +getAmount(): int
    +setAmount(i: int): void
    +getSupplier(): String
    +setSupplier(supplier: String): void    
    +getSupplierID(): int
    +setSupplierId(id: int): void
    +getToolID(): int
    +setToolID(toolID: int): void
    +toString(): String
}


class OrderWriter {
    -{static} workingDir: String
    -{static}bw: BufferedWriter

    -OrderWriter()
    +{static}writeOrder(order: Order): String
}

Procurement ... OrderWriter: Uses
Procurement *--- "*" Order: Creates
Procurement "1" o--- "1...*" Supplier: Has 
Inventory o-- "1...*" Tool: Consists of 1 or more
Inventory  *--- "*" OrderLine: Generates
InvMgmt "1" *-- "1" Inventory: Creates
InvMgmt "1" *-- "1" Procurement: Creates
Order "1" o-- "1...*" OrderLine: Has
OrderWriter ... Order: Uses

@enduml

Unused: 

    +addTool(tool: Tool): Tool
    +deleteTool(toolID: int): Tool
    

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