@startuml
package CustomerModel{
    class CustomerList{

    }
    interface Customer{

    }

    class CommercialCustomer implements Customer{

    }

    class ResidentialCustomer implements Customer{

    }

    CustomerList o--- "*" Customer
}

@enduml
