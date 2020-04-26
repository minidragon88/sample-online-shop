package com.phu.onlineshop.model.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.List;

public class SearchMessage
{
    private static final List<Operator> STRING_OPERATORS = Arrays.asList(Operator.LIKE, Operator.EQUAL);
    private static final List<Operator> NUMERIC_OPERATORS = Arrays.asList(Operator.EQUAL, Operator.GREATER, Operator.LESS, Operator.GREATER_THAN_OR_EQUAL, Operator.LESS_THAN_OR_EQUAL);
    private String term = "";
    private int page = 0;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Column column = Column.NAME;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Operator operator = Operator.EQUAL;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ORDER order = ORDER.ASC;

    public String getTerm()
    {
        return term;
    }

    public void setTerm(final String term)
    {
        this.term = term;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(final int page)
    {
        this.page = page;
    }

    public Column getColumn()
    {
        return column;
    }

    public void setColumn(final Column column)
    {
        this.column = column;
    }

    public Operator getOperator()
    {
        return operator;
    }

    public void setOperator(final Operator operator)
    {
        this.operator = operator;
    }

    public ORDER getOrder()
    {
        return order;
    }

    public void setOrder(final ORDER order)
    {
        this.order = order;
    }

    public enum Column
    {
        NAME("name", STRING_OPERATORS),
        PRICE("price", NUMERIC_OPERATORS),
        COLOUR("colour", STRING_OPERATORS),
        BRANCH("branch_id", STRING_OPERATORS),
        CATALOG("catalog_id", STRING_OPERATORS);
        private final String column;
        private final List<Operator> allowOperators;
        Column(final String column, final List<Operator> allowOperators)
        {
            this.column = column;
            this.allowOperators = allowOperators;
        }

        @JsonCreator
        public static Column fromString(final String value)
        {
            for (final Column column : Column.values()) {
                if (column.getColumn().equalsIgnoreCase(value)) {
                    return column;
                }
            }
            return Column.NAME;
        }

        public String getColumn()
        {
            return column;
        }

        public List<Operator> getAllowOperators()
        {
            return allowOperators;
        }
    }

    public enum Operator
    {
        LIKE("like"),
        EQUAL("equal"),
        GREATER("greater"),
        LESS("less"),
        GREATER_THAN_OR_EQUAL("greater_than_or_equal"),
        LESS_THAN_OR_EQUAL("less_than_or_equal");
        private final String name;
        Operator(final String name)
        {
            this.name = name;
        }

        @JsonCreator
        public static Operator fromString(final String value)
        {
            for (final Operator operator : Operator.values()) {
                if (operator.getName().equalsIgnoreCase(value)) {
                    return operator;
                }
            }
            return Operator.EQUAL;
        }

        public String getName()
        {
            return name;
        }
    }

    public enum ORDER
    {
        ASC("asc"),
        DESC("desc");
        private final String name;
        ORDER(final String name)
        {
            this.name = name;
        }

        @JsonCreator
        public static ORDER fromString(final String value)
        {
            for (final ORDER order : ORDER.values()) {
                if (order.getName().equalsIgnoreCase(value)) {
                    return order;
                }
            }
            return ORDER.ASC;
        }

        public String getName()
        {
            return name;
        }
    }
}
