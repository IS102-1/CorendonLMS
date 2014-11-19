package corendonlms.model.luggage;

import corendonlms.main.MiscUtil;
import corendonlms.model.DatabaseTables;
import corendonlms.model.IStorable;

/**
 * Represents a piece of luggage for database writing
 *
 * @author Emile
 */
public final class Luggage implements IStorable
{

    private static final DatabaseTables TABLE = DatabaseTables.LUGGAGE;

    private final String color, pattern, brand, passengerId, weight, size;

    /**
     * Initialize a new object of Luggage
     *
     * @param color The luggage's color
     * @param pattern The luggage's pattern
     * @param brand The luggage's brand
     * @param passengerId The luggage's passenger ID
     * @param weight The luggage's weight
     * @param size The luggage's size
     * @throws IllegalArgumentException
     */
    public Luggage(String color, String pattern, String brand, 
            String passengerId, String weight, LuggageSize size)
            throws IllegalArgumentException
    {
        if (MiscUtil.isStringNullOrWhiteSpace(brand) 
                || MiscUtil.isStringNullOrWhiteSpace(passengerId))
        {
            throw new IllegalArgumentException("The luggage's brand and "
                    + "its passenger ID can not be null or whitespace.");
        }
        
        this.color = color;
        this.pattern = pattern;
        this.brand = brand;
        this.passengerId = passengerId;
        this.weight = weight;
        this.size = size.toString();
    }

    /**
     * Get the value of color
     *
     * @return the value of color
     */
    public String getColor()
    {
        return color;
    }

    /**
     * Get the value of pattern
     *
     * @return the value of pattern
     */
    public String getPattern()
    {
        return pattern;
    }

    /**
     * Get the value of brand
     *
     * @return the value of brand
     */
    public String getBrand()
    {
        return brand;
    }

    /**
     * Get the value of passenger ID
     *
     * @return the value of passenger ID
     */
    public String getPassengerId()
    {
        return passengerId;
    }

    /**
     * Get the value of weight
     *
     * @return the value of weight
     */
    public String getWeight()
    {
        return weight;
    }

    /**
     * Get the value of size
     *
     * @return the value of size
     */
    public String getSize()
    {
        return size;
    }

    @Override
    public String getUpdate()
    {
        return String.format("INSERT INTO %s (color, pattern, brand, "
                + "passenger_id, weight, size) VALUES ('%s', '%s', '%s', '%s', "
                + "'%s', '%s')", TABLE, color, pattern, brand, passengerId,
                weight, size);
    }

    @Override
    public int getFieldLength()
    {
        return 6;
    }

    @Override
    public DatabaseTables getTable()
    {
        return TABLE;
    }
}
