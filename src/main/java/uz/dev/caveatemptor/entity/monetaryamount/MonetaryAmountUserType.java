package uz.dev.caveatemptor.entity.monetaryamount;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;
import java.util.Objects;
import java.util.Properties;

import static java.util.Objects.requireNonNullElse;

public class MonetaryAmountUserType implements CompositeUserType, DynamicParameterizedType {

    private Currency convertTo;

    @Override
    public void setParameterValues(Properties properties) {
        String convertToParameter = properties.getProperty("convertTo");
        convertTo = Currency.getInstance(
                requireNonNullElse(convertToParameter, "USD"));

    }

    @Override
    public Class returnedClass() {
        return MonetaryAmount.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return Objects.equals(o, o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException {
        return o.toString();
    }

    @Override
    public Object assemble(Serializable cached, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return MonetaryAmount.fromString((String) cached);
    }

    @Override
    public Object replace(Object o, Object o1, SharedSessionContractImplementor sharedSessionContractImplementor, Object o2) throws HibernateException {
        return o;
    }


    @Override
    public String[] getPropertyNames() {
        return new String[]{"value", "currency"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{
                StandardBasicTypes.BIG_DECIMAL,
                StandardBasicTypes.CURRENCY
        };
    }

    @Override
    public Object getPropertyValue(Object component, int propertyIndex) throws HibernateException {
        MonetaryAmount amount = (MonetaryAmount) component;
        if (propertyIndex == 0)
            return amount.getValue();
        else
            return amount.getCurrency();
    }

    @Override
    public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
        throw new UnsupportedOperationException("MonetaryAmount class is immutable");
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        BigDecimal amount = resultSet.getBigDecimal(names[0]);
        if (resultSet.wasNull())
            return null;
        Currency currency = Currency.getInstance(resultSet.getString(names[1]));
        return new MonetaryAmount(amount, currency);
    }

    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value != null) {
            MonetaryAmount amount = (MonetaryAmount) value;
            MonetaryAmount dbAmount = convert(amount, convertTo);
            statement.setBigDecimal(index, dbAmount.getValue());
            statement.setString(index + 1, convertTo.getCurrencyCode());
        } else {
            setNullStatement(statement, index);
        }
    }

    private MonetaryAmount convert(MonetaryAmount amount, Currency toCurrency) {
        BigDecimal newPrice = amount.getValue();
        String fromCurrencyStr = amount.getCurrency().getCurrencyCode();
        String toCurrencyStr = toCurrency.getCurrencyCode();
        if (fromCurrencyStr.equals("USD") && toCurrencyStr.equals("EUR")) {
            newPrice = amount.getValue().divide(BigDecimal.valueOf(1.2), RoundingMode.HALF_UP);
        } else if (fromCurrencyStr.equals("EUR") && toCurrencyStr.equals("USD")) {
            newPrice = amount.getValue().multiply(BigDecimal.valueOf(1.2));
        }
        return new MonetaryAmount(
                newPrice,
                toCurrency
        );
    }

    private void setNullStatement(PreparedStatement statement, int index) throws SQLException {
        statement.setNull(index, StandardBasicTypes.BIG_DECIMAL.sqlType());
        statement.setNull(index + 1, StandardBasicTypes.CURRENCY.sqlType());
    }
}
