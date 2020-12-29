# Getting Started
Launch the application from the CspApplication.java

## Customer Statement Processor

This application receives the customer statement JSON as a POST data, and performs the below Validations:

1. Checks if all transaction references are unique
2. Checks if the Start Balance +/- Mutation = End Balance 

##Input end point
A rest end point has been implemented and can be found as:
http://localhost:8080/customerstatementprocessor/customerStatementValidator

## Output

| Http Status Code  | Condition                                                         |  Response format |
|---                |---                                                                |---               |
| 200               | When there are no duplicate reference and correct end balance     | `{"result" : "SUCCESSFUL", "errorRecords" : []}`|
| 200               |When there are duplicate reference and correct balance             |[duplicateReferenceAndcorrectBalance Json](./duplicateReferenceAndcorrectBalance.json)|
| 200               |When there are no duplicate reference and In correct balance       |[IncorrectBalance Json](./IncorrectBalance.json)|
| 200               |When there are duplicate reference and In correct balance          |[duplicateReferenceAndIncorrectBalance Json](./duplicateReferenceAndIncorrectBalance.json)|
| 400               |Error during parsing JSON                                          | `{"result" : "BAD_REQUEST", "errorRecords" : []}`|
| 500               |Any other situation                                                |`{"result" : "INTERNAL_SERVER_ERROR","errorRecords" : [] }`|

##Test Data:
The test JSON data can be found in the src/test/resources.

##Git Bundle:
The git bundle customer-statement-processor.bundle is attached in the email.
 If usual unbundling does not work , please execute the below command: 

 "git checkout remotes/origin/master"

 else clone from "https://github.com/prachidas05/customer-statement-processor.git"