# ENSF 607/608 COMBINED PROJECT
## By: Michael Lasby and Tong Xu
## Submitted On: Nov. 30, 2020

### Project directory map
* See `./InventoryAppClient/src/` for client side source files.
* See `./InventoryAppServer/src/` for server side source files. 
### Compiling and running
* To compile and run the **server**, please complete the following steps: 
    * Execute the file `./create-toolshop-objects_v1.sql` in MySQL workbench to initialize the database.  
    * Save your MySQL username and password in `./InventoryAppServer/src/DBController/DBCredentials.java`
    * Add the two `.jar` files in `./InventoryAppServer/lib` to the classpath.
    * Start the server by launching `./InventoryAppServer/bin/ServerController/Server.class.` 
* To compile and run the **client**, please complete the following steps: 
    * Setup, compile, and start the server.
    * Add the two `.jar` files in `./InventoryAppServer/lib` to the classpath.
    * Start the client app by launching `./InventoryAppClient/bin/Client/App.class`

### Notes
The server is currently setup as localhost on port 4444. To host over TCP/IP, input your IP address where indicated in `./InventoryAppClient/src/Client/App.java` and uncomment the indicated lines in `./InventoryAppClient/src/Client/ClientController/ClientController.java`. Your server's router must be set to forward port 4444 to your server. 


