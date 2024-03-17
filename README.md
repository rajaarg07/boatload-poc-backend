## BoatLoad POC

** Requirement: **

Below tasks needs to be completed as per the requirement

(i) Need to receive an API call from Supertokens UI [Angular] which is authenticated using NodeJS [Backend]

(ii) Connect to Zoho CRM, 

(iii) Pull any report,

(iv) Save it to DB,

(v) Send report response to UI to display the report details

** API: **

 (i) Get Open Deals Report API
 
** Tech Stack Used **

*Backend:*

(i) Java 17, 

(ii) Spring Boot 3.1.9

(iii) Spring JPA

(iv) MySQL DB

(v) Main Libraries used are Apache Poi, Spring Rest Template etc.,
 
** How it works **

(i) We get open deals report API call from Angular UI, and we receive this call in backend using Spring RestController

(ii) We then pass the HttpHeader, payload request, access token to Zoho CRM using Spring Rest Template

(iii) Received response from Zoho CRM is saved to a local file (Open+Dealss.xlsx)

(iv) The saved local file (Open+Dealss.xlsx) is read by using Apach Poi library to map data to Open Deals bean

(v) After the List of Open Deals bean is initialized, we then use Spring JPA to connect to MySQL DB

(v) After persisting, we then send the response details to the UI to display the details