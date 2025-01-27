package za.co.atm.discovery.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository {
/*
    Reporting – Find the transactional account per client with the highest balance

    SELECT c.CLIENT_ID, c.SURNAME, a.CLIENT_ACCOUNT_NUMBER, a.DISPLAY_BALANCE, t.DESCRIPTION
    FROM CLIENT_ACCOUNT a, CLIENT c, ACCOUNT_TYPE t
    WHERE a.DISPLAY_BALANCE IN (
        SELECT MAX(ca.DISPLAY_BALANCE)
    FROM CLIENT_ACCOUNT ca, ACCOUNT_TYPE at
    WHERE ca.ACCOUNT_TYPE_CODE = at.ACCOUNT_TYPE_CODE
    AND at.TRANSACTIONAL IS TRUE
    GROUP BY ca.CLIENT_ID
        )
    AND c.CLIENT_ID = a.CLIENT_ID
    AND t.ACCOUNT_TYPE_CODE = a.ACCOUNT_TYPE_CODE
    ORDER BY c.CLIENT_ID;

 */


    /*
    Reporting - Calculate aggregate financial position per client
    -Please note I didn't include the foreign currency accounts

    SELECT
    a.CLIENT_ID, CONCAT(c.TITLE , ' ',  c.NAME , ' ', c.SURNAME) AS Client_Details,
    SUM(CASE WHEN a.ACCOUNT_TYPE_CODE like '%LOAN%' THEN a.DISPLAY_BALANCE ELSE 0 END) AS Loan_Balance,
    SUM(CASE WHEN a.ACCOUNT_TYPE_CODE = t.ACCOUNT_TYPE_CODE AND t.TRANSACTIONAL is true  THEN a.DISPLAY_BALANCE ELSE 0 END) AS Transactional_Balance,
    SUM(CASE WHEN a.ACCOUNT_TYPE_CODE like '%LOAN%' THEN a.DISPLAY_BALANCE ELSE 0 END) + SUM(CASE WHEN a.ACCOUNT_TYPE_CODE = t.ACCOUNT_TYPE_CODE AND t.TRANSACTIONAL is true  THEN a.DISPLAY_BALANCE ELSE 0 END)
        AS Net_Position
    FROM
        CLIENT_ACCOUNT a, ACCOUNT_TYPE t, CLIENT c
    WHERE a.CLIENT_ID = c.CLIENT_ID
    GROUP BY a.CLIENT_ID
     */
}

