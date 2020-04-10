This is a test application to test interface application to communicate with NAYAX credit card reader.

You will find build folder for ready to use binary but in case if you want to build from source then execute  build.bat or build.sh depend upon operating system environment.

If you want to enable log mode then please add "logging.properties" file with library and set debug parameter to true during initialize.



Steps to start application.


1. Go to build folder.
2. Run this application : java -jar abb_pt_pos.jar serial_port_id
Serial port identification is required to pass through command line argument to start communication over that serial port.</li></ul>

3. Select option number "6" to register interface of PaymentModuleStatusListener.

4. Select option number "1" to initialize PaymentTerminal to work with NAYAX card reader.

5. After this POS application will be ready for make payment related operations.
