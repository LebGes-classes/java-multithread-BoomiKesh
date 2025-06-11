package org.example;

public class CurrencyTransfer extends Thread {
    private int currentAccountIndex;
    private int sendingChoiceIndex;
    private int amount;

    public CurrencyTransfer(int currentAccountIndex, int sendingChoiceIndex, int amount) {
        this.currentAccountIndex = currentAccountIndex;
        this.sendingChoiceIndex = sendingChoiceIndex;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            while (Data.accountsList.get(currentAccountIndex).getStatus() || Data.accountsList.get(sendingChoiceIndex).getStatus()) {
                sleep(200);
            }
        } catch (Exception e) {
        }

        Data.accountsList.get(currentAccountIndex).setStatus(true);
        Data.accountsList.get(sendingChoiceIndex).setStatus(true);
        Data.accountsList.get(currentAccountIndex).changeBalance(-1 * amount);
        Data.accountsList.get(sendingChoiceIndex).changeBalance(amount);
        Data.accountsList.get(currentAccountIndex).setStatus(false);
        Data.accountsList.get(sendingChoiceIndex).setStatus(false);
    }
}
