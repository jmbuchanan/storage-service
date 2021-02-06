package com.storage.site.model.rowmapper;

import com.storage.site.model.Transaction;
import com.storage.site.util.DateUtil;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TransactionRowMapper implements RowMapper<Transaction> {

    @Override
    public Transaction mapRow(ResultSet rs, int rowNumber) throws SQLException {

        return new Transaction(
                rs.getInt("id"),
                Transaction.Type.valueOf(rs.getString("transaction_type").toUpperCase()),
                rs.getDate("request_date"),
                rs.getDate("execution_date"),
                rs.getInt("customer_id"),
                rs.getInt("unit_id"),
                rs.getInt("payment_method_id")
        );
    }
}
