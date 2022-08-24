package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.enums.Operation;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionParserImpl implements TransactionParser {
    private static final String COMMA_REGEX = ",";
    private static final String TITLE = "type,fruit,quantity";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private FruitDao fruitDao = new FruitDaoImpl();

    @Override
    public List<FruitTransaction> getTransactions(String fileName) {
        List<String> records = fruitDao.getRecords(fileName);
        List<FruitTransaction> transactions = new ArrayList<>();
        for (String record : records) {
            if (record.equals(TITLE)) {
                continue;
            }
            transactions.add(parseRecord(record));
        }
        return transactions;
    }

    private FruitTransaction parseRecord(String record) {
        String[] recordDetails = record.split(COMMA_REGEX);
        Operation operationType = Operation.getType(recordDetails[OPERATION_INDEX]);
        String fruit = recordDetails[FRUIT_INDEX];
        int quantity = Integer.parseInt(recordDetails[QUANTITY_INDEX]);
        return new FruitTransaction(operationType, fruit, quantity);
    }
}
