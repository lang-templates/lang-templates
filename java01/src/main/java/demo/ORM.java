package demo;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;

public class ORM {
    public static void main(String[] args) throws Exception {
        // this uses h2 but you can change it to match your database
        //String databaseUrl = "jdbc:h2:mem:account";
        String databaseUrl = "jdbc:sqlite:test.db3";
       // create a connection source to our database
        ConnectionSource connectionSource =
                new JdbcConnectionSource(databaseUrl);

       // instantiate the DAO to handle Account with String id
        Dao<Account, String> accountDao =
                DaoManager.createDao(connectionSource, Account.class);

       // if you need to create the 'accounts' table make this call
        TableUtils.createTable(connectionSource, Account.class);

       // create an instance of Account
        String name = "Jim Smith";
        Account account = new Account(name, "_secret");

       // persist the account object to the database
        accountDao.create(account);

       // retrieve the account
        Account account2 = accountDao.queryForId(name);
       // show its password
        System.out.println("Account: " + account2.getPassword());

       // close the connection source
        connectionSource.close();
    }
}

@DatabaseTable(tableName = "accounts")
class Account {

    @DatabaseField(id = true)
    private String name;
    @DatabaseField(canBeNull = false)
    private String password;

    Account() {
    }
    public Account(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
