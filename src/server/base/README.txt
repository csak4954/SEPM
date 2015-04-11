Operating System Family: windows
Virtual Machine Architecture: x64
Virtual Machine Name: hotspot
========================================================================================================================
Template: base
Version: 3.1.0.RELEASE
Build Date: 20150302232109

* Sets Xmx to 512M
* Sets Xss to 256K
* Adds a control script to the instance
* Adds the Windows service wrapper libraries
* Adds a default jmxremote configuration with a read/write user called 'admin' with a generated password
* Adds a default JULI logging configuration
* Adds a default server configuration containing:
	* A JRE memory leak prevention listener
	* A tc Runtime Deployer listener
	* A JMX socket listener
	* A LockOutRealm to prevent attempts to guess user passwords via a brute-force attack
	* An in-memory user database
	* A threadpool that has up to 300 threads
	* A host that uses 'webapps' as its app base
	* An AccessLogValve
* Adds a default Tomcat user configuration that is empty
* Adds an init.d script configured to start the instance as a specific user
* Adds a root web application
========================================================================================================================
Template: base-tomcat-8
Version: 3.1.0.RELEASE
Build Date: 20150302232109

* Add Tomcat 8-specific Servlet 3.1 Specification
* Add Tomcat 8-specific web.xml as a watched resource
========================================================================================================================
Template: bio
Version: 3.1.0.RELEASE
Build Date: 20150302232109

* Adds a Blocking IO (BIO) connector for HTTP
