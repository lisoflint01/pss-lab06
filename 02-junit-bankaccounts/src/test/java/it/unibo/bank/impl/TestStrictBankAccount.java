package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Configuration step: this is performed BEFORE each test.
     */
    @BeforeEach

        public void setUp() {
            this.mRossi = new AccountHolder("Mario", "Rossi", 1);
            this.bankAccount = new StrictBankAccount(mRossi, 0.0);
        }

    /**
     * Check that the initialization of the BankAccount is created with the correct values.
     */
    @Test
        void testBankAccountInitialization() {
            assertEquals(0.0, bankAccount.getBalance());
            assertEquals(0, bankAccount.getTransactionsCount());
            assertEquals(mRossi, bankAccount.getAccountHolder());
        }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
        
        public void testManagementFees() {
            bankAccount.deposit(mRossi.getUserID(),100);
            assertEquals(100, bankAccount.getBalance());
            assertEquals(1, bankAccount.getTransactionsCount());

            bankAccount.chargeManagementFees(mRossi.getUserID());
            assertEquals(100 - 0.1, bankAccount.getBalance());
        }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        bankAccount.withdraw(mRossi.getUserID(),-10);
        assertEquals(100 - 0.1, bankAccount.getBalance());
        assertEquals(2, bankAccount.getTransactionsCount());
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        bankAccount.withdraw(mRossi.getUserID(),10);
        assertEquals(100 - 0.1 - 10, bankAccount.getBalance());
        assertEquals(2, bankAccount.getTransactionsCount());

        bankAccount.withdraw(mRossi.getUserID(),200);
        assertEquals(100 - 0.1 - 10, bankAccount.getBalance());
        assertEquals(2, bankAccount.getTransactionsCount());
    }
}
