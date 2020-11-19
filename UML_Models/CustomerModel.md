@startuml
package CustomerModel{
    class CustomerList{

    }
    abstract class Customer{

    }

    class CommercialCustomer extends Customer{

    }

    class ResidentialCustomer extends Customer{

    }

    CustomerList *--- "*" Customer
}

@enduml
