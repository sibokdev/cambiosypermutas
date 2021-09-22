package app.oficiodigital.cliente.storage;


import app.oficiodigital.cliente.utils.TableConstants.AccountStatusTable;
import app.oficiodigital.cliente.utils.TableConstants.AddressTable;
import app.oficiodigital.cliente.utils.TableConstants.AuditTable;
import app.oficiodigital.cliente.utils.TableConstants.ClarificationTable;
import app.oficiodigital.cliente.utils.TableConstants.CreditCardsTable;
import app.oficiodigital.cliente.utils.TableConstants.DocumentInServiceTable;
import app.oficiodigital.cliente.utils.TableConstants.DocumentTable;
import app.oficiodigital.cliente.utils.TableConstants.FriendsTable;
import app.oficiodigital.cliente.utils.TableConstants.MovementsTable;
import app.oficiodigital.cliente.utils.TableConstants.ServiceTable;
import app.oficiodigital.cliente.utils.TableConstants.SubTypesTable;
import app.oficiodigital.cliente.utils.TableConstants.TypesTable;

public class TableStructures {

    public static final String DATABASE_NAME = "boveda.s3db";

    public static class TableUser {
        public final static String NAME = "user";

        public final static String[] FIELDS = {
                "id", "name", "lastname", "lastname2", "genre", "birthday", "phone", "email", "password",
                "recovery_question", "recovery_answer"
        };

        public final static String CREATE = "CREATE TABLE " + NAME + " (" +
                FIELDS[0] + " text, " + FIELDS[1] + " text, " + FIELDS[2] + " text, " + FIELDS[3] + " text, " +
                FIELDS[4] + " integer, " + FIELDS[5] + " text, " + FIELDS[6] + " text, " + FIELDS[7] + " text," +
                FIELDS[8] + " text, " + FIELDS[9] + " text, " + FIELDS[10] + " text); ";
    }

    public static class TableServices {

        public final static String CREATE = "CREATE TABLE " + ServiceTable.TABLE_NAME + " (" +
                ServiceTable.ID_CLIENT      + " text, " +
                ServiceTable.SERVICE_TYPE   + " text, " +
                ServiceTable.URGENT         + " text, " +
                ServiceTable.LATITUDE       + " text, " +
                ServiceTable.LONGITUDE      + " text, " +
                ServiceTable.DATE           + " text, " +
                ServiceTable.ADDRESS        + " text, " +
                ServiceTable.NOTES          + " text, " +
                ServiceTable.DOCUMENTS      + " text, " +
                ServiceTable.ID             + " text, " +
                ServiceTable.TYPE           + " text, " +
                ServiceTable.STATUS         + " text, " +
                ServiceTable.FOLIO          + " text, " +
                ServiceTable.DESCRIPTION    + " text, " +
                ServiceTable.CREATED_AT     + " text, " +
                ServiceTable.UPDATED_AT     + " text);";
    }

    public static class TableDocuments {

        public final static String CREATE = "CREATE TABLE " + DocumentTable.TABLE_NAME + " (" +
                DocumentTable.ID            + " text, " +
                DocumentTable.FOLIO         + " text, " +
                DocumentTable.LOCATION      + " integer, " +
                DocumentTable.TYPE          + " text, " +
                DocumentTable.SUBTYPE       + " text, " +
                DocumentTable.ALIAS         + " text, " +
                DocumentTable.NUMBER        + " text, " +
                DocumentTable.EXPEDITION    + " integer, " +
                DocumentTable.EXPIRATION    + " text, " +
                DocumentTable.PICTURE       + " text, " +
                DocumentTable.NOTES         + " text, " +
                DocumentTable.STATUS        + " text, " +
                DocumentTable.CUSTOMER_ID   + " text, " +
                DocumentTable.CREATED_AT    + " text, " +
                DocumentTable.UPDATED_AT    + " text, " +
                DocumentTable.IS_IN_SERVICE + " integer);";

    }

    public static class TableMovements {
        public final static String CREATE = "CREATE TABLE " + MovementsTable.MOVEMENTS_TABLE + "(" +
                MovementsTable.ID               + " text, " +
                MovementsTable.DOCUMENT_ID      + " text, " +
                MovementsTable.NEW_LOCATION     + " text, " +
                MovementsTable.DATE             + " text, " +
                MovementsTable.CREATED_AT       + " text, " +
                MovementsTable.UPDATED_AT       + " text );";
    }

    public static class TableDocInServices {
        public final static String CREATE = "CREATE TABLE " + DocumentInServiceTable.TABLE_NAME + " (" +
                DocumentInServiceTable.ID           + " text, " +
                DocumentInServiceTable.ALIAS        + " text, " +
                DocumentInServiceTable.DOCUMENT_ID  + " text, " +
                DocumentInServiceTable.LOCATION     + " text, " +
                DocumentInServiceTable.SERVICE_ID   + " text);";
    }

    public static class TableFriends {
        public final static String CREATE = "CREATE TABLE " + FriendsTable.FRIENDS_TABLE + "( " +
                FriendsTable.EMAIL + " text);";
    }

    public static class TableAudits {
        public final static String CREATE = "CREATE TABLE " + AuditTable.TABLE_NAME + " ( " +
                AuditTable.ID           + " text, " +
                AuditTable.CLIENT_ID    + " text, " +
                AuditTable.DATE         + " text, " +
                AuditTable.STATUS       + " text, " +
                AuditTable.NOTES        + " text, " +
                AuditTable.DOCUMENTS    + " text); ";
    }

    public static class TableCreditCards {
        public final static String CREATE = "CREATE TABLE " + CreditCardsTable.TABLE_NAME + " ( " +
                CreditCardsTable.ID             + " text, " +
                CreditCardsTable.TITULAR        + " text, " +
                CreditCardsTable.CARD_NUMBER    + " text, " +
                CreditCardsTable.ACCOUNT_ID     + " text, " +
                CreditCardsTable.MONTH          + " text, " +
                CreditCardsTable.YEAR           + " text, " +
                CreditCardsTable.MAIN           + " text);";
    }

    public static class TableTypes {
        public final static String CREATE = "CREATE TABLE " + TypesTable.TABLE_NAME + " ( " +
                TypesTable.ID       + " text, " +
                TypesTable.NAME     + " text);";
    }

    public static class TableSubTypes {
        public final static String CREATE = "CREATE TABLE " + SubTypesTable.TABLE_NAME + " ( " +
                SubTypesTable.ID_SUBTYPE    + " text, " +
                SubTypesTable.ID_TYPE       + " text, " +
                SubTypesTable.NAME          + " text);";
    }

    public static class TableAddress {
        public final static String CREATE = "CREATE TABLE " + AddressTable.TABLE_NAME + " ( " +
                AddressTable.ID_ADDRESS     + " text, " +
                AddressTable.ADDRESS        + " text, " +
                AddressTable.ALIAS          + " text, " +
                AddressTable.LATITUDE       + " text, " +
                AddressTable.LONGITUDE      + " text);";
    }

    public static class TableClarification {
        public final static String CREATE = "CREATE TABLE " + ClarificationTable.TABLE_NAME + " ( " +
                ClarificationTable.CLIENT_ID            + " text, " +
                ClarificationTable.CLARIFICATION_TYPE   + " text, " +
                ClarificationTable.CONTENT              + " text, " +
                ClarificationTable.STATUS               + " text, " +
                ClarificationTable.FOLIO                + " text);";
    }

    public static class TableAccountStatus {
        public final static String CREATE = "CREATE TABLE " + AccountStatusTable.TABLE_NAME + " ( " +
                AccountStatusTable.ID               + " text, " +
                AccountStatusTable.DATE             + " text, " +
                AccountStatusTable.AMOUNT           + " text, " +
                AccountStatusTable.PAYMENT_METHOD   + " text, " +
                AccountStatusTable.STATUS           + " text);";
    }

}
