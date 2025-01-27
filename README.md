Hi

Thank you for taking the time to go through my assessment.

Please note that I made a few changes to the DB tables - I altered the way
the Identity columns are created.  I also removed the Identity feature on the
CLIENT_ACCOUNT table as it was linked on a varchar column.  The changed table
creation is in my flyway script.

All of my DB connection info is in application.properties

I used SpringBoot 3.4.1
- Java 17
- Maven 3.9.9
- Intellij IDE

Additional Dependencies that I used:
- Lombok
- Flyway
- Apache commons-lang3

**Endpoints:**
- http://localhost:8080/discovery-atm/queryTransactionalBalances/1

- http://localhost:8080/discovery-atm/queryCcyBalances/1

- http://localhost:8080/discovery-atm/withdraw/client/1/atm/1/account/1053664521/amount/990

My reporting sql is in the ReportRepository file.

Some horrid looking unit tests exist in the test directory

Regards
Celene

