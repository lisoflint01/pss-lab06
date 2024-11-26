package it.unibo.inheritance.impl;

public class ExtendedStrictBankAccount extends SimpleBankAccount {
    
    private static final double TRANSACTION_FEE = 0.1;

    public ExtendedStrictBankAccount(final int id, final double balance) {
        super(id, balance);
    }

    public void withdraw(final int id, final double amount) {
        if (isWithdrawAllowed(amount)) {
            if (checkUser(id)) {
                setBalance(getBalance() - amount);
                incrementTransactions();
            }
        }
    }

    public void withdrawFromATM(final int id, final double amount) {
        this.withdraw(id, amount + ExtendedStrictBankAccount.ATM_TRANSACTION_FEE);
    }

    private boolean isWithdrawAllowed(final double amount) {
        return super.getBalance() >= amount;
    }

    public void chargeManagementFees(final int id) {
        final double feeAmount = MANAGEMENT_FEE + getTransactionsCount() * ExtendedStrictBankAccount.TRANSACTION_FEE;
        if (checkUser(id) && isWithdrawAllowed(feeAmount)) {
            setBalance(getBalance() - feeAmount);
            resetTransactions();
        }
    }

}
