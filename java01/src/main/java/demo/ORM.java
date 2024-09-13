package demo;

import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
//import org.joda.time.DateTime;
import system.Sys;
import system.Waiter;

import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.List;

public class ORM {
    public static void main(String[] args) throws Exception {
        //String databaseUrl = "jdbc:h2:mem:account";
        String databaseUrl = "jdbc:h2:./test";
        //String databaseUrl = "jdbc:sqlite:test.db3";
        // create a connection source to our database
        ConnectionSource connectionSource =
                new JdbcConnectionSource(databaseUrl);

        // instantiate the DAO to handle Account with String id
        Dao<Account, String> accountDao =
                DaoManager.createDao(connectionSource, Account.class);

        // if you need to create the 'accounts' table make this call
        TableUtils.createTableIfNotExists(connectionSource, Account.class);

        TransactionManager.callInTransaction(connectionSource, new Callable<Void>() {
            public Void call() throws Exception {
                try (CloseableWrappedIterable<Account> iterable
                             = accountDao.getWrappedIterable()) {
                    iterable.forEach(account -> {
                        Sys.echo(account.getName());
                        try {
                            accountDao.delete(account);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                //var dt = new org.joda.time.DateTime();
                //dt = dt.plusDays(1);
                //Waiter.waitOnConsole(dt);
                return null;
            }
        });

        // create an instance of Account
        String name = "Jim Smith";
        Account account = new Account(name, "_secret");

        // persist the account object to the database
        accountDao.create(account);

        // retrieve the account
        Account account2 = accountDao.queryForId(name);
        // show its password
        System.out.println("Account: " + account2.getPassword());

        List<Account> list = accountDao.queryBuilder()
                .where()
                .in("name", accountDao.queryBuilder()
                        .selectColumns("name")
                        .groupBy("password")
                        .having("count(*) > 0"))
                .query();

        list.stream().forEach((a) -> {
            Sys.echo(a, "a");
        });

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

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
