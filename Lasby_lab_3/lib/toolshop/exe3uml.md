@startuml
class App{
    -br: BufferedReader
    -invMgmt: InvMgmt

    +App()
    +getInvMgmt(): InvMgmt
    +setInvMgmt(InvMgmt im): void
    +displayInventory(): void
    +searchInventory(name: String): void
    +searchInventory(toolID: int): void
    +checkItemQuantity(toolID: int): void
    +checkItemQuantity(name: String): void
    +makeSale(toolID: int): void
    +returnTool(toolID: int): void
    +generateOrder(): void
    +writeOrder(): void
    +receiveOrder(fname: String): void
    +run(): void
    -displayMenu(): void
    -getID(): int
    -getInt(): int
    -getString(): String
    -getName(): String
    -returnToMenu(): void
    -clear(): void
}

class InvMgmt{
    -inventory: Inventory
    -procurement: Procurement
    -------------------------
    +InvMgmt()
    +getInventory(): Inventory
    +setInventory(inventory: Inventory): void
    +getProcurement(): Procurement 
    +setProcurement(procurement: Procurement): void
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
    +getTools(): ArrayList<Tool>
    +setTools(ArrayList<Tool>): void
    +getPendingOrders(): ArrayList<OrderLine>
    +setPendingOrders(ArrayList<OrderLine>): void
    +toString(): String
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
    -description: String
    -quantity: int
    -price: double
    -supplierID: int
    -lowSupply: int
    -targetSupply: int 

    +Tool(toolID:int, description: String, quantity: int, price: double, supplierID: int)
    +toString(): String
    +toQuantityString(): String
    +getToolId(): int
    +setToolId(id: int): void
    +getToolName(): String
    +setToolName(name: String): void
    +getQuantity(): int
    +setQuantity(quant: int): void
    +getPrice(): double
    +setPrice(price: double): void
    +getSupplierID(): int
    +setSupplierID(id: int): void
    +getLowSupply(): int
    +setLowSupply(i: int): void
    +getTargetSupply(): int
    +setTargetSupply(i: int): void
}

class Procurement {
    -orders: ArrayList<Order>
    -vendors: VendorList

    +Procurement(vendors: VendorList)
    +getOrders(): ArrayList<Order>
    +setOrders(orders: ArrayList<Order>): void
    +getVendors(): VendorList
    +setVendors(vendors: VendorList): void
    +addOrder(order: Order): String
    +deleteOrder(orderID: int): String
    +addVendor(vendor: Vendor): String
    +deleteOrder(supplierID: int): String
    +generateOrder(orders: ArrayList<OrderLine>): String
    -writeOrder(order: Order): void
    -getNextOrderID(): int
    -getOrder(value: <V>): Order
}

class VendorList{
    -vendors: ArrayList<Supplier>

    +VendorList(ArrayList<Supplier>)
    +toString(): String
    +getVendors(): ArrayList<Supplier>
    +setVendors(vendors: ArrayList<Supplier>): void
    +getVendor(supplierID: int): Supplier
    +getNameByID(supplierID: int): String
}

class Supplier{
    -supplierID: int
    -companyName: String
    -address: String
    -salesContact: String

    +Supplier(id: int, name: String, address: String, salesContact: String)
    +toString(): String
    +getSupplierID(): int
    +setSupplierID(id: int): void
    +getCompanyName(): String
    +setCompanyName(name: String): void
    +getAddress(): String
    +setAddress(address: String): void
    +getSalesContact(): String
    +setSalesContact(contact: String): void
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

class dbReader{
    -{static}SUPPLIER_FILE: String
    -{static}TOOL_FILE: String
    -{static}br: BufferedReader

    -dbReader()
    +{static}readSuppliers(): VendorList
    +{static}readTools(): Inventory
    +{static}readOrder(fname: String): Order
}
Procurement ... OrderWriter: Uses
Procurement "1" o--- "1" VendorList: Has 
Procurement "1" *--- "*" Order: Creates
VendorList "1" o--- "1...*" Supplier: Has 
dbReader ... VendorList: Generates
dbReader ... Inventory: Generates
dbReader ... OrderLine: Generates
dbReader ... Supplier: Uses
App "1" ---> "1" InvMgmt: Has a
Inventory "1" o-- "1...*" Tool: Consists of 1 or more
Inventory  "1" ---> "*" OrderLine: Uses >
InvMgmt "1" *-- "1" Inventory: Creates
InvMgmt "1" *-- "1" Procurement: Creates
InvMgmt  ... dbReader: Uses
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