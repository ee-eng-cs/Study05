<!DOCTYPE html>
<HTML>
<HEAD>
	<META charset="UTF-8">
</HEAD>
<BODY>
<IMG src="images/ColorScheme.png" height="25" width="800"/>
<H2 id="contents">Study05 README Contents</H2>
<OL>
	<LI><A href="#reactive">Research Reactive RESTful Web Services</A></LI>
	<LI><A href="#hypermedia">Research Hypermedia-based RESTful Web Services</A></LI>
	<LI><A href="#web">Research Web Application</A></LI>
</OL>

<HR/>
<H2 id="reactive">❶ Research Reactive RESTful Web Services</H2>

<P>Java source code:<BR/>
<img src="images/aquaHR-500.png"><BR/>
<img src="images/aquaSquare.png"> package 
	<a href="https://github.com/ee-eng-cs/Study05/tree/master/reactive-rest/src/main/java/kp/company/">kp.company</a><BR/>
<img src="images/aquaSquare.png"> package
	<a href="https://github.com/ee-eng-cs/Study05/tree/master/reactive-rest/src/main/java/kp/client/">kp.client</a><BR/>
<img src="images/aquaHR-500.png"></P>

<P>The example uses Spring Boot and Spring WebFlux reactive web framework.</BR>
By default it is deployed on an embedded Reactor Netty server.
</P>

<P>
<TABLE STYLE="border: 1px solid black;">
<CAPTION>Comparing the stacks</CAPTION>
<TR>
	<TH STYLE="border: 1px solid black;">Framework</TH>
	<TH STYLE="border: 1px solid black;">Used Stack</TH>
</TR><TR>
	<TD STYLE="border: 1px solid black;">Spring WebFlux</TD>
	<TD STYLE="border: 1px solid black;">Reactive Stack</TD>
</TR><TR>
	<TD STYLE="border: 1px solid black;">Spring MVC</TD>
	<TD STYLE="border: 1px solid black;">Servlet  Stack</TD>
</TR>
</TABLE>
</P>

<P>Actions:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> 1. With batch file <I>"01 MVN run.bat"</I> start the server.<BR/>
<img src="images/spacer-32.png">After starting the server it executes single request from the non-blocking web client.<BR/>
<img src="images/orangeCircle.png"> 2. With batch file <I>"02 call server with curl.bat"</I> execute the request to the server.<BR/>
<img src="images/orangeHR-500.png"></P>

<P><IMG src="images/ConsoleLogReactiveServerAndClient.png" height="111" width="930"/><BR>
<I>First action result: console log from reactive server and from non-blocking, reactive client call.</I></P>

<P><IMG src="images/ConsoleLogCurl.png" height="96" width="930"/><BR>
<I>Second action result: console log from curl.</I></P>

<P><IMG src="images/ConsoleLogReactiveServer.png" height="96" width="930"/><BR>
<I>Second action result: console log from reactive server.</I></P>

<H3>Application Tests</H3>
<UL>
  <LI>Test with non-blocking, reactive client <B>WebTestClient</B>.</LI>
</UL>

<HR/>
<H2 id="hypermedia">❷ Research Hypermedia-based RESTful Web Services</H2>


<P>Java source code:<BR/>
<img src="images/aquaHR-500.png"><BR/>
<img src="images/aquaSquare.png"> package 
	<a href="https://github.com/ee-eng-cs/Study05/tree/master/rest-hateoas/src/main/java/kp/company/">kp.company</a><BR/>
<img src="images/aquaHR-500.png"></P>

<P>The example uses Spring Boot, Spring HATEOAS, Spring MVC, embedded H2 database, and Spring Boot Actuator.</BR>
It is deployed on an embedded Apache Tomcat 9 server.
</P>

<P>Actions:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> 1. With batch file <I>"01 MVN run.bat"</I> start the server.<BR/>
<img src="images/orangeCircle.png"> 2. With batch file <I>"02 CURL read all.bat"</I> get JSON response with all departments and all employees.<BR/>
<img src="images/orangeCircle.png"> 3. With batch file <I>"03 CURL CRUD.bat"</I> create, read, update, and delete departments and employees.<BR/>
<img src="images/orangeCircle.png"> 4. With batch file <I>"04 CURL show profile.bat"</I> show profiles for departments and employees.<BR/>
<img src="images/orangeCircle.png"> 5. Open in web browser the file <I>"links.html"</I>.<BR/>
<img src="images/spacer-32.png">On that page there are links for showing departments and employees, its profiles and actuator endpoints.<BR/>
<img src="images/orangeHR-500.png"></P>

<P><IMG src="images/JsonResponseGet.png" height="456" width="550"/><BR>
<I>JSON response for GET all departments request.</I></P>

<P><IMG src="images/JsonResponseCreate.png" height="171" width="550"/><BR>
<I>JSON response for CREATE department request.</I></P>

<P><IMG src="images/JsonResponseUpdate.png" height="171" width="550"/><BR>
<I>JSON response for UPDATE department request.</I></P>

<H3>Application Tests</H3>
<OL>
  <LI>The client side tests. They use <B>TestRestTemplate</B> and the server is started.</LI>
  <LI>Tests with server-side support. They use <B>MockMvc</B> and the server is not started.</LI>
  <LI>The JPA repository tests with an embedded in-memory database.</LI>
</OL>

<HR/>
<H2 id="web">❸ Research Web Application</H2>

<P>Java source code:<BR/>
<img src="images/aquaHR-500.png"><BR/>
<img src="images/aquaSquare.png"> package 
	<a href="https://github.com/ee-eng-cs/Study05/tree/master/web/src/main/java/kp/company/">kp.company</a><BR/>
<img src="images/aquaHR-500.png"></P>

<P>The example uses Spring Boot, Spring MVC, and Thymeleaf view template library.
</P>

<P>Actions:<BR/>
<img src="images/orangeHR-500.png"><BR/>
<img src="images/orangeCircle.png"> 1. With batch file <I>"01 MVN run.bat"</I> start the server.<BR/>
<img src="images/orangeCircle.png"> 2. Go to a page <A href="http://localhost:8080/">http://localhost:8080/</A> and execute CRUD actions.<BR/>
<img src="images/orangeCircle.png"> 3. With batch file <I>"02 call server with lynx.bat"</I> send requests to the web application from Lynx browser.<BR/>
<img src="images/orangeHR-500.png"></P>

<H3>Use Cases</H3>
<TABLE>
  <TR><TD><B>Company</B></TD><TD>welcome page</TD></TR>
  <TR><TD><B>Show departments</B></TD><TD>table view of company's departments and links to its employees</TD></TR>
  <TR><TD><B>Show employees</B></TD><TD>table view of selected department's employees</TD></TR>
  <TR><TD><B>Edit the existing employee</B></TD><TD>edit the information pertaining to an employee</TD></TR>
  <TR><TD><B>Update the existing employee</B></TD><TD>update the information pertaining to an employee</TD></TR>
  <TR><TD><B>Delete the employee</B></TD><TD>delete employee from the department</TD></TR>
  <TR><TD><B>Add a new employee</B></TD><TD>add a new employee to the department</TD></TR>
  <TR><TD><B>Edit the existing department</B></TD><TD>edit the information pertaining to a department</TD></TR>
  <TR><TD><B>Update the existing department</B></TD><TD>update the information pertaining to a department</TD></TR>
  <TR><TD><B>Delete the department</B></TD><TD>delete existing department (with its employees) from the company</TD></TR>
  <TR><TD><B>Add a new department</B></TD><TD>add a new department to the company</TD></TR>
</TABLE>

<H3>Use Case Diagram</H3>
<IMG src="images/uml_use_case_diagram.jpg"/><BR/>

<H3>Application Screens</H3>
<P><IMG src="images/Company.png" height="118" width="530"/><BR>
<I>Welcome page of the application. Overview of the company.</I>
</P>
<P><BR><IMG src="images/ListDepartments.png" height="235" width="530"/><BR>
<I>Listing all departments.</I>
</P>
<P><BR><IMG src="images/EditDepartment.png" height="117" width="515"/><BR>
<I>Editing the existing department.</I>
</P>
<P><BR><IMG src="images/DeleteDepartment.png" height="117" width="516"/><BR>
<I>Deleting the existing department.</I>
</P>
<P><BR><IMG src="images/AddDepartment.png" height="201" width="512"/><BR>
<I>Adding a new department. Displayed validation messages.</I>
</P>
<P><BR><IMG src="images/ListEmployees.png" height="330" width="530"/><BR>
<I>Listing all employees of the selected department.</I>
</P>
<P><BR><IMG src="images/EditEmployee.png" height="176" width="514"/><BR>
<I>Editing the existing employee.</I>
</P>
<P><BR><IMG src="images/DeleteEmployee.png" height="183" width="517"/><BR>
<I>Deleting the existing employee.</I>
</P>
<P><BR><IMG src="images/AddEmployee.png" height="351" width="517"/><BR>
<I>Adding a new employee. Displayed validation messages.</I>
</P>

<H3>Application Tests</H3>
<OL>
  <LI>The client side tests. They use <B>TestRestTemplate</B> and the server is started.</LI>
  <LI>Tests with server-side support. They use <B>MockMvc</B> and the server is not started.</LI>
</OL>

<HR/>
<A href="../reactive-rest/docs/apidocs/index.html?overview-summary.html" >API Specifications</A> for Reactive RESTful Web Services.<BR/>
<A href="../rest-hateoas/docs/apidocs/index.html?overview-summary.html" >API Specifications</A> for Hypermedia-based RESTful Web Services.<BR/>
<A href="../web/docs/apidocs/index.html?overview-summary.html" >API Specifications</A> for Web Application.<BR/>
(API was not commited to <B>GitHub</B>; those links should be active after local build with <I>'mvn javadoc'</I>)
<HR/>
<HR/>
</BODY>
</HTML>