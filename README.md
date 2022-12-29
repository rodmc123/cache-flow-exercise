# Invoice App

To start the app just run it from intellij or whatever IDE you are using

Should start by default at localhost:8080

This project is using an in memory H2 database
you can check the H2 DB console from the browser
http://localhost:8080/h2-console 
* **Url**: jdbc:h2:file:~/data/mydb
* **user**: sa
* **password**: password

To test the API's you can use Postman or a similar app...

* Tables to keep history of changes are generated by Envers, you'll see them in the H2 console with name <table-name>_AUD
didn't add a controller to retrieve the list of events but the data is there. 
* No energy for unit tests lol 
* Didn't add logs in this implementation just to save some time with the logback or log4j config.. added some comments though.

Example requests:

## **Create invoice**

### **POST**

localhost:8080/invoice/create

`{
"customerEmail":"test@testerx.com",
"customerName":"potato",
"description":"services provided",
"dueDate":"2022-03-12",
"status":"DRAFT",
"total":"13000"
}`

## Add line items to invoice 
### PUT
localhost:8080/invoice/add-line-item

`{
"invoiceId":1,
"lineItems": [
{
"description":"laptop",
"countryOfOrigin": "China",
"manufacturer": "lenovo",
"total": 1000,
"quantity": 10,
"serialNumber": "A453SDF6",
"model": "thinkpad6"
},
{
"description":"monitor",
"countryOfOrigin": "South Korea",
"manufacturer": "LG",
"total": 650,
"quantity": 1000,
"serialNumber": "B45SDF6",
"model": "ultrawide b6"
}`

## Retrieve invoices by status 

### GET

localhost:8080/invoice/get-by-status/{status} (draft, sent, paid, approved)

## Update invoice status

### PUT
localhost:8080/invoice/update
`{
"invoiceId":1,
"invoiceStatus": "SENT"
}`
