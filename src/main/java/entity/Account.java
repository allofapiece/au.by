package entity;

/**
 * Class provides the opportunity of payments by users.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class Account {
    private String number;
    private AccountStatus status;
    private double money;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (Double.compare(account.money, money) != 0) return false;
        if (number != null ? !number.equals(account.number) : account.number != null) return false;
        return status == account.status;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = number != null ? number.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        temp = Double.doubleToLongBits(money);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
